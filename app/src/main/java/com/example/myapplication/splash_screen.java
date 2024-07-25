package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash_screen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=1250;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferences = getSharedPreferences("pref",MODE_PRIVATE);
        Boolean islogin = preferences.getBoolean("isLogin",false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(islogin!=false)
                {
                    Intent i = new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
                //the current activity will get finished.
            }
        }, 500);
    }
}