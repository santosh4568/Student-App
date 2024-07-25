package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;


public class Calendar extends Fragment {
    View v;
    GifImageView loading;
    SharedPreferences preferences;
    RequestQueue rq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       v =  inflater.inflate(R.layout.fragment_calendar, container, false);
        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String regno = preferences.getString("regno","");
        loading=v.findViewById(R.id.loading);
        rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/personal_details.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        PhotoView photoView1 = v.findViewById(R.id.photo_view);
                        //PhotoView mIcon1 = v.findViewById(R.id.ivIcon1);
                        loading.setVisibility(View.GONE);
                        Glide.with(getContext())
                                .load("http://14.139.85.169/jspapi/calendar/"+(hit.getString("coursecode"))+(hit.getString("cyear"))+""+(hit.getString("semester"))+".jpg")
                                .into(photoView1);
                    }
                } catch (JSONException e) {
                    Log.d("error_in",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_out",error.toString());
            }
        });
        rq.add(request);
        return v;   }

}