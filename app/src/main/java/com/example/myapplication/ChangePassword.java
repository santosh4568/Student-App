package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends Fragment {
    String regno_;
    EditText oldpass,newpass;
        Button update;
        SharedPreferences preferences;
    View v;
        TextInputLayout oop,onp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_change_password, container, false);
        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        regno_ = preferences.getString("regno","");


        oldpass =v.findViewById(R.id.oldpass);
        newpass = v.findViewById(R.id.newpass);
        update = v.findViewById(R.id.button9);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oop = v.findViewById(R.id.oop);
                onp = v.findViewById(R.id.onp);
                String s1,s2;
                s1=oldpass.getText().toString().trim();
                s2=newpass.getText().toString().trim();
                if(s1.length() != 0 && s2.length() != 0) {
                    update();
                }
                else if(s1.length() == 0 && s2.length() == 0)
                {
                    oldpass.setError("Enter OLD PASSWORD");
                    newpass.setError("Enter NEW PASSWORD");
                }
                else if(s1.length() == 0){
                    oldpass.requestFocus();
                    oldpass.setError("Enter OLD PASSWORD");
                }
                else if(s2.length() == 0) {
                    newpass.requestFocus();
                    newpass.setError("Enter password");
                }
            }
        });
        oldpass.addTextChangedListener(sub1);
        newpass.addTextChangedListener(sub2);
        return v;
    }
    private TextWatcher sub1=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //oop = findViewById(R.id.oop);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            oop = v.findViewById(R.id.oop);
            oop.setPasswordVisibilityToggleEnabled(true);


        }

        @Override
        public void afterTextChanged(Editable editable) {
            oop = v.findViewById(R.id.oop);
            String s1=oldpass.getText().toString();
            if(s1.length()==0)
                oop.setPasswordVisibilityToggleEnabled(false);
        }
    };
    private TextWatcher sub2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //oop = findViewById(R.id.oop);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            onp = v.findViewById(R.id.onp);
            onp.setPasswordVisibilityToggleEnabled(true);


        }

        @Override
        public void afterTextChanged(Editable editable) {
            onp = v.findViewById(R.id.onp);
            String s1=newpass.getText().toString();
            if(s1.length()==0)
                onp.setPasswordVisibilityToggleEnabled(false);
        }
    };

    public void update(){

        String old_pass_ = oldpass.getText().toString();
        String new_pass_ = newpass.getText().toString();
        String url = "http://14.139.85.169/jspapi/change_pass.jsp";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String flag=response.trim();
                if(flag.equals("Success")){
                    Toast.makeText(getContext(),"Password updated successfully",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Incorrect old password",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("msg",error.toString());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("regno",regno_);
                param.put("old_pass",old_pass_);
                param.put("new_pass",new_pass_);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }


}