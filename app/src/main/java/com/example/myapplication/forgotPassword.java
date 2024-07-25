package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class forgotPassword extends AppCompatActivity {
    Button update,generate_otp;
    EditText regno,otptxt,new_pass;
    String otp="";
    Spinner spinner;
    String usertype="";
    SharedPreferences preferences;
    CardView cv_container2,updatepassbtn,pa;
    String users[]={"Select user","Student","Parent"};
    EditText e1,otp_,password;
    TextView seconds,minutes,colon;
    ConstraintLayout forgotpass;
    private Disposable networkDisposable;
    private Disposable internetDisposable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        cv_container2 = findViewById(R.id.cv_container2);
        updatepassbtn = findViewById(R.id.updatepassbtn);
        pa=findViewById(R.id.pa);
        seconds = findViewById(R.id.seconds);
        minutes = findViewById(R.id.minutes);
        colon = findViewById(R.id.colon);
        forgotpass = findViewById(R.id.forgotPass);

        regno = findViewById(R.id.e1);
        generate_otp = findViewById(R.id.generate_otp);
        otptxt = findViewById(R.id.otp);
        new_pass = findViewById(R.id.password);

        e1=findViewById(R.id.e1);
        otp_ = findViewById(R.id.otp);
        password = findViewById(R.id.password);

        preferences = getSharedPreferences("pref",MODE_PRIVATE);
        int min=0,sec=0;


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.sp1);
        spinner.setSelection(2);
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


        generate_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generate_otp.setEnabled(false);
                String e1_,usertype;
                SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
                usertype = preferences.getString("usertype","");
                e1_=e1.getText().toString().trim();
                if(e1_.length() != 0 && !(usertype.equals("Select user")) ) {
                   generate_otp.setEnabled(false);
                    otp();
                }
                else if(e1_.length() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Register Id",Toast.LENGTH_SHORT).show();
                }
                else if((usertype.equals("Select user")))
                {
                    Toast.makeText(getApplicationContext(),"Please select user type",Toast.LENGTH_SHORT).show();
                }
            }
        });
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp,password_;
                otp=otp_.getText().toString().trim();
                password_=password.getText().toString().trim();
                if(otp.length() != 0 && password_.length() != 0 ) {
                    updatePassword();
                }
                else if(otp.length() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter OTP",Toast.LENGTH_SHORT).show();
                }
                else if(password_.length() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void updatePassword(){
        String regno_ = regno.getText().toString();
        otptxt = findViewById(R.id.otp);
        new_pass = findViewById(R.id.password);
        usertype = preferences.getString("usertype","");
        String url = "http://14.139.85.169/jspapi/otp_verifi.jsp";
        String new_otp_ = otptxt.getText().toString();
        String new_pass_= new_pass.getText().toString();
        if(otp.equals(""))
            Toast.makeText(getApplicationContext(),"Generate a OTP",Toast.LENGTH_SHORT).show();
        else {

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //String flag=response.trim();

                    if (response.trim().equals("Success")) {
                        Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), response.trim(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("msg", error.toString());

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("regno", regno_);
                    param.put("otp", otp);
                    param.put("new_otp", new_otp_);
                    param.put("new_pass", new_pass_);
                    param.put("usertype", usertype);
                    return param;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }
    }

    public void otp(){
        String url = "http://14.139.85.169/jspapi/forgot_pass_api.jsp";
        usertype = preferences.getString("usertype","");
        String regno_ = regno.getText().toString().toUpperCase().trim();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String flag=response.trim();
                otp = flag;
                if(flag.equals("Invalid"))
                    Toast.makeText(getApplicationContext(),"Invalid Register ID",Toast.LENGTH_SHORT).show();
                else {
                    minutes.setVisibility(View.VISIBLE);
                    colon.setVisibility(View.VISIBLE);
                    seconds.setVisibility(View.VISIBLE);
                    new CountDownTimer(120000, 1000) {

                        public void onTick(long millisUntilFinished ) {
                            int min=(int)((millisUntilFinished/1000)/60);
                            int sec=(int)((millisUntilFinished/1000)%60);
                            minutes.setText("0"+min+"");
                            if(sec<10)
                                seconds.setText("0"+sec+"");
                            else
                                seconds.setText(sec+"");
                        }

                        public void onFinish() {
                            generate_otp.setEnabled(true);
                            minutes.setVisibility(View.INVISIBLE);
                            colon.setVisibility(View.INVISIBLE);
                            seconds.setVisibility(View.INVISIBLE);
                        }

                    }.start();
                    cv_container2.setVisibility(View.VISIBLE);
                    updatepassbtn.setVisibility(View.VISIBLE);
                    pa.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "OTP SENT", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("otp_error",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("regno",regno_);
                param.put("usertype",usertype);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
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
            Snackbar.make(forgotpass,"Please connect to the internet",Snackbar.LENGTH_INDEFINITE).show();
        }
        else{
            if(status==0) {
                status=1;
                Snackbar.make(forgotpass, "Connected to the internet", Snackbar.LENGTH_SHORT).show();
            }


        }
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

}