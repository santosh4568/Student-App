package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ActivitiesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_activities, container, false);

        PhotoView photoView = (PhotoView) v.findViewById(R.id.photo_view);
       // photoView.setImageResource(R.drawable.yfo);
        Glide.with(getContext())
                .load("http://14.139.85.169/jspapi/sp/191FA04188.jpg")
                .into(photoView);

    return v;
    }
}