package com.example.myapplication;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    EditText e1,e30;
    ImageButton login;
    Button forgotPass;
    TextInputLayout et1;
    SharedPreferences preferences;
    Spinner spinner;
    String users[]={"Select user","Student","Parent"};
    String usertype="";
    ConstraintLayout loginscreen;
    private Disposable networkDisposable;
    private Disposable internetDisposable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginscreen = findViewById(R.id.loginscreen);
        preferences = getSharedPreferences("pref",MODE_PRIVATE);

        e1=findViewById(R.id.e1);
        e30=findViewById(R.id.e30);
        login = findViewById(R.id.login);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.sp1);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    String usertype = spinner.getSelectedItem().toString();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("usertype",usertype);
                    editor.apply();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //et1 = findViewById(R.id.textInputLayout);
                String s1,s2;
                SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
                usertype = preferences.getString("usertype","");
                s1=e1.getText().toString().trim();
                s2=e30.getText().toString().trim();
                if(s1.length() != 0 && s2.length() != 0 && !(usertype.equals("Select user")) ) {
                    loginCheck();
                }
                else if(s1.length() == 0 && s2.length() == 0)
                {
                    e1.setError("Enter ID");
                    e30.setError("Enter password");
                }
                else if(s1.length() == 0){
                    e1.requestFocus();
                    e1.setError("Enter ID");
                }
                else if(s2.length() == 0) {
                    e30.requestFocus();
                    e30.setError("Enter password");
                }
                else if((usertype.equals("Select user")))
                {
                    Toast.makeText(getApplicationContext(),"Please select user type",Toast.LENGTH_SHORT).show();
                }
            }
        });


        forgotPass = findViewById(R.id.forgotPass);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),forgotPassword.class);
                startActivity(i);
            }
        });

        e1.addTextChangedListener(sub);
        e30.addTextChangedListener(sub);

    }
    private TextWatcher sub=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // et1 = findViewById(R.id.textInputLayout);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //et1 = findViewById(R.id.textInputLayout);


        }

        @Override
        public void afterTextChanged(Editable editable) {
            //et1 = findViewById(R.id.textInputLayout);
            String s1=e30.getText().toString();
        }
    };


    public void loginCheck(){
        String url = "http://14.139.85.169/jspapi/loginapi.jsp";
        String regno_ = e1.getText().toString().toUpperCase();
        String pass_ = e30.getText().toString();


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                String flag=response.trim();
                Log.d("Response",""+response.trim());
                if(flag.equals("Success")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLogin",true);
                    editor.putString("regno",regno_);
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect RegisterId or Password", Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("msg",error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("regno",regno_);
                param.put("password",pass_);
                param.put("usertype",usertype);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    public void onBackPressed() { }
    int status=1;
    @Override protected void onResume() {
        super.onResume();

        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    //Log.d(TAG, connectivity.toString());
                    final NetworkInfo.State state = connectivity.state();
                    final String name = connectivity.typeName();
                    //tvConnectivityStatus.setText(String.format("state: %s, typeName: %s", state, name));
                });

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> in(isConnected.toString())
                );
    }

    @Override protected void onPause() {
        super.onPause();
        safelyDispose(networkDisposable, internetDisposable);
    }

    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
    public void in(String internet)
    {
        if(internet.equals("false"))
        {       status=0;
            Snackbar.make(loginscreen,"Please connect to the internet",Snackbar.LENGTH_INDEFINITE).show();
        }
        else{
            if(status==0) {
                status=1;
                Snackbar.make(loginscreen, "Connected to the internet", Snackbar.LENGTH_SHORT).show();
            }


        }
    }



}