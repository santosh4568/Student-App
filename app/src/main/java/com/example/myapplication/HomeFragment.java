package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress;
import com.kaushikthedeveloper.doublebackpress.helper.DoubleBackPressAction;
import com.kaushikthedeveloper.doublebackpress.helper.FirstBackPressAction;
import com.kaushikthedeveloper.doublebackpress.setup.display.ToastDisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class HomeFragment extends Fragment  {
    View v;
    RequestQueue rq;
    SharedPreferences preferences;
    CardView profile_cardview,att_cardview,agg_cardview,feepayment_cardview,calender_cardview;
    TextView c_name,c_phone,c_email,registerno,sectionn,branch,aggregate,aggdecimal,attendance,attdecimal,yearandsem;
    String branch_="branch",name_="name",regno="",section="section",year="",semester="",branchcode="",coursecode="",doj="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);
        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        regno = preferences.getString("regno","");

        yearandsem = v.findViewById(R.id.yearandsem);
        registerno =v.findViewById(R.id.regno);
        sectionn =v.findViewById(R.id.section);
        branch =v.findViewById(R.id.dept);
        aggregate =v.findViewById(R.id.aggregate);
        aggdecimal =v.findViewById(R.id.aggdecimal);
        attendance =v.findViewById(R.id.attendance);
        attdecimal =v.findViewById(R.id.attdecimal);
        c_name =v.findViewById(R.id.c_name);
        c_phone =v.findViewById(R.id.c_phone);
        c_email =v.findViewById(R.id.c_email);

        ImageView imageView = (ImageView)v.findViewById(R.id.profile);
        Glide.with(getContext())
                .load("http://14.139.85.169/jspapi/sp/"+regno+".jpg")
                .into(imageView);

        rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/personal_details.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        name_ = hit.getString("name");
                        branch_=hit.getString("branchsname").toUpperCase();
                        section = hit.getString("section");
                        year = hit.getString("cyear");
                        semester = hit.getString("semester");
                        branchcode = hit.getString("branchcode");
                        coursecode = hit.getString("coursecode");
                        doj = hit.getString("dojj");

                        attfunc(section,year,regno,semester,branchcode,coursecode,doj);


                        sectionn.setText("Section :  "+section);
                        branch.setText("Branch  :  "+branch_);
                        registerno.setText("Reg.No  :  "+regno);
                        yearandsem.setText("Year & Sem  :  "+year+" & "+semester);


                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name",name_);
                        editor.putString("section",section);
                        editor.putString("year",year);
                        editor.putString("semester",semester);
                        editor.putString("branchcode",branchcode);
                        editor.putString("coursecode",coursecode);
                        editor.putString("doj",doj);

                        Log.d("sppppp",regno+section+year+semester+branchcode+coursecode+doj);
                        editor.apply();
                    }
                } catch (JSONException e) {
                    Log.d("home_error_in",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("home_error_out",error.toString());
            }
        });
        rq.add(request);

        RequestQueue rq2 = Volley.newRequestQueue(getContext());
        String url2 = "http://14.139.85.169/jspapi/aggregate_api.jsp?regno="+regno;
        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    String aggregate__ = response.getString("Aggregate");
                    if(aggregate__.equals("?"))
                    {
                        String part1 = "0"; // 004
                        String part2 = "00";
                        aggregate.setText(part1);
                        aggdecimal.setText("."+part2);
                    }
                    else {
                        String[] parts = aggregate__.split(Pattern.quote("."));
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        Log.v("aggregate", part1 + "");
                        aggregate.setText(part1);
                        aggdecimal.setText("." + part2);
                    }
                } catch (JSONException e) {
                    Log.e("agg_error_in",e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("agg_error_out",error.toString());
            }
        });
        rq2.add(request2);

        RequestQueue rq3 = Volley.newRequestQueue(getContext());
        String url3 = "http://14.139.85.169/jspapi/counsellor_info_api.jsp?regno="+regno;
        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++)
                    {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        c_name.setText(hit.getString("c_name"));
                        c_phone.setText(hit.getString("c_phoneno"));
                        c_email.setText(hit.getString("c_email"));

                    }
                } catch (JSONException e) {
                    Log.e("c_error_in",e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("c_error_out",error.toString());
            }
        });
        rq3.add(request3);


        profile_cardview = v.findViewById(R.id.profile_cardview);
        att_cardview = v.findViewById(R.id.att_cardview);
        agg_cardview = v.findViewById(R.id.agg_cardview);
        feepayment_cardview = v.findViewById(R.id.feepayment_cardview);
        calender_cardview = v.findViewById(R.id.calender_cardview);

        



        profile_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_to_Personal_Details);
            }
        });
        att_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_to_attendance);
            }
        });
        agg_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_home_to_academics);
            }
        });
        feepayment_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_to_feeDetails);
            }
        });
        calender_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_to_calender);
            }
        });

        return v;
    }
    public void attfunc(String section,String year,String regno,String semester,String branchcode,String coursecode,String doj)
    {
        RequestQueue rq3 = Volley.newRequestQueue(getContext());
        String url3 = "http://14.139.85.169/jspapi/main_attendance.jsp?regno="+regno;
        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    if(response.getString("AttendancePercentage").equals("?"))
                    {attendance.setText("00");attdecimal.setText(".00");}
                    else {
                        String attendance__ = (response.getString("AttendancePercentage"));
                        String[] parts1 = attendance__.split(Pattern.quote("."));
                        String part1 = parts1[0]; // 004
                        String part2 = parts1[1]; // 034556
                        Log.v("attendace",part1+"");
                        attendance.setText(part1);
                        attdecimal.setText("."+part2);
                    }

                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("att_err_out",error.toString());

            }
        });
        rq3.add(request3);
    }
//    public void onBackPressed()
//    {
//
//    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getContext(), "back pressed", Toast.LENGTH_SHORT).show();
                getActivity().finishAffinity();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

}









