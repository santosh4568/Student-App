package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Academics extends Fragment  {


public static int n = 0,m=0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.weekly:
                    m=0;
                    if(n==1)
                    {}
                    else{
                    WeeklyFragment weeklyFragment = new WeeklyFragment();
                    FragmentManager fragmentManager1 = getChildFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.flFragment, weeklyFragment).commit();
                        //Toast.makeText(getContext(),n+"",Toast.LENGTH_SHORT).show();
                    n=1;
                    }
                    return true;

                case R.id.semester:
                    n=0;
                    if(m==1)
                    {}
                    else{FinalMarks finalMarks = new FinalMarks();
                    FragmentManager fragmentManager3 = getChildFragmentManager();
                    fragmentManager3.beginTransaction().replace(R.id.flFragment, finalMarks).commit();
                    //Toast.makeText(getContext(),m+"",Toast.LENGTH_SHORT).show();
                    m=1;}
                    return true;
            }
            return false;
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_academics, container, false);
        BottomNavigationView navegacion = (BottomNavigationView) v.findViewById(R.id.bottomNavigationView);
        navegacion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        n=1;m=0;
        WeeklyFragment weeklyFragment = new WeeklyFragment();
        FragmentManager fragmentManager1 = getChildFragmentManager();
        fragmentManager1.beginTransaction().replace(R.id.flFragment, weeklyFragment).commit();

        return v;


    }
    public void t(String str){
        Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
    }

}