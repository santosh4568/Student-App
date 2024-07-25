package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalDetails extends Fragment {
    SharedPreferences preferences;
    RequestQueue rq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal_details,container,false);
        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String regno = preferences.getString("regno","");
        TextView dob = (TextView)v.findViewById(R.id.dob);
        TextView phno=(TextView)v.findViewById(R.id.phno);
        TextView gender=(TextView)v.findViewById(R.id.gender);
        TextView section=(TextView)v.findViewById(R.id.section);
        TextView semester=(TextView)v.findViewById(R.id.semester);
        TextView year=(TextView)v.findViewById(R.id.year);
        TextView branch=(TextView)v.findViewById(R.id.branch);
        TextView course=(TextView)v.findViewById(R.id.course);
        TextView name=(TextView)v.findViewById(R.id.name);
        TextView gmail=(TextView)v.findViewById(R.id.gmail);
        TextView sscyearpassing=(TextView)v.findViewById(R.id.sscyearpassing);
        TextView ssctype=(TextView)v.findViewById(R.id.ssctype);
        TextView schoolname=(TextView)v.findViewById(R.id.schoolname);
        TextView schoollocation=(TextView)v.findViewById(R.id.schoollocation);
        TextView intertype=(TextView)v.findViewById(R.id.intertype);
        TextView intername=(TextView)v.findViewById(R.id.intername);
        TextView interlocation=(TextView)v.findViewById(R.id.interlocation);
        TextView entrancetestno=(TextView)v.findViewById(R.id.entrancetestno);
        TextView entrancetestrank=(TextView)v.findViewById(R.id.entrancetestrank);
        TextView entrancetestmarks=(TextView)v.findViewById(R.id.entrancetestmarks);
        TextView seatcategory=(TextView)v.findViewById(R.id.seatcategory);
        TextView fname = (TextView)v.findViewById(R.id.fname);
        TextView foccu = (TextView)v.findViewById(R.id.foccupation);
        TextView mname = (TextView)v.findViewById(R.id.mname);
        TextView moccu = (TextView)v.findViewById(R.id.moccupation);
        TextView caste = (TextView)v.findViewById(R.id.caste);
        TextView fedu = (TextView)v.findViewById(R.id.fedustatus);
        TextView medu = (TextView)v.findViewById(R.id.medustatus);
        TextView familystatus = (TextView)v.findViewById(R.id.familystatus);
        TextView vigemp = (TextView)v.findViewById(R.id.vignanemployeeknown);
        TextView address = (TextView)v.findViewById(R.id.address);
        TextView batchno = (TextView)v.findViewById(R.id.batchno);
        TextView religion = (TextView)v.findViewById(R.id.religion);
        TextView mothertongue = (TextView)v.findViewById(R.id.mothertongue);
        TextView annualincome = (TextView)v.findViewById(R.id.annualincome);
        TextView employeename = (TextView)v.findViewById(R.id.employeename);



        rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/personal_details.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        dob.setText(hit.getString("dob"));
                        phno.setText(hit.getString("studentmobile"));
                        gender.setText(hit.getString("gender"));
                        section.setText(hit.getString("section"));
                        semester.setText(hit.getString("semester"));
                        year.setText(hit.getString("cyear"));
                        branch.setText(hit.getString("branch"));
                        course.setText(hit.getString("course"));
                        name.setText(hit.getString("name"));
                        gmail.setText(hit.getString("studentemailid").toLowerCase());
                        sscyearpassing.setText(hit.getString("sscpassyear"));
                        ssctype.setText(hit.getString("ssctype"));
                        schoolname.setText(hit.getString("sscschool"));
                        schoollocation.setText(hit.getString("ssclocation"));
                        intertype.setText(hit.getString("intertype"));
                        intername.setText(hit.getString("intercollege"));
                        interlocation.setText(hit.getString("interlocation"));
                        entrancetestno.setText(hit.getString("entrancetest"));
                        entrancetestrank.setText(hit.getString("entrancetestrank"));
                        entrancetestmarks.setText(hit.getString("entrancemarks"));
                        seatcategory.setText(hit.getString("seatcategory"));
                        fname.setText(hit.getString("fathername"));
                        foccu.setText(hit.getString("fatheroccupation"));
                        mname.setText(hit.getString("mothername"));
                        moccu.setText(hit.getString("motheroccupation"));
                        caste.setText(hit.getString("caste"));
                        fedu.setText(hit.getString("fathereducation"));
                        medu.setText(hit.getString("mothereducation"));
                        familystatus.setText(hit.getString("familystatus"));
                        vigemp.setText(hit.getString("vignanemployknown"));
                        address.setText(hit.getString("houseno") + "," + hit.getString("street") + "," + hit.getString("town") + "," + hit.getString("mandal") + "," + hit.getString("district") + "," + hit.getString("state") + "\n" + hit.getString("pincode"));
                        batchno.setText(hit.getString("batchno"));
                        religion.setText(hit.getString("Religion"));
                        mothertongue.setText(hit.getString("mothertongue"));
                        annualincome.setText(hit.getString("annualincome"));
                        employeename.setText(hit.getString("employename"));



                    }
                } catch (JSONException e) {
                    Log.d("error_in",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(request);
        return v;
    }
}