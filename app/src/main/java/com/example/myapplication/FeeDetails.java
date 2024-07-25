package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeeDetails extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fee_details, container, false);

        Button pay=v.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeePayment feePayment = new FeePayment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, feePayment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        TableLayout stk= v.findViewById(R.id.table);

        TableRow tbrow= new TableRow(getContext());

        TextView tvo=new TextView(getContext());
        tvo.setText("S.No");
        tvo.setPadding(10,10,10,10);
        tvo.setTextSize(15);
        tvo.setTypeface(Typeface.DEFAULT_BOLD);
        tvo.setTextColor(Color.BLACK);
        tvo.setGravity(Gravity.CENTER);
        tbrow.addView(tvo);

        TextView tv5=new TextView(getContext());
        tv5.setText("Date");
        tv5.setTextColor(Color.BLACK);
        tv5.setGravity(Gravity.CENTER);
        tv5.setPadding(10,10,10,10);
        tv5.setTextSize(15);
        tv5.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv5);


        TextView tv3=new TextView(getContext());
        tv3.setText("Receipt No.");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(10,10,10,10);
        tv3.setTextSize(15);
        tv3.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv3);


        TextView tv4=new TextView(getContext());
        tv4.setText("Challan No.");
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor(Color.BLACK);
        tv4.setPadding(10,10,10,10);
        tv4.setTextSize(15);
        tv4.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv4);

        TextView tv2=new TextView(getContext());
        tv2.setText("Pay Mode");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(10,10,10,10);
        tv2.setTextSize(15);
        tv2.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv2);

        TextView tv1=new TextView(getContext());
        tv1.setText("Amount");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(10,10,10,10);
        tv1.setTextSize(15);
        tv1.setTypeface(Typeface.DEFAULT_BOLD);
        tv1.setGravity(Gravity.CENTER);
        tbrow.addView(tv1);







        stk.addView(tbrow);
        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String regno= preferences.getString("regno","");
        RequestQueue rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/stu_fee.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = response.getJSONArray("fee");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        //Toast.makeText(getContext(), Integer.toString(jsonArray.length()), Toast.LENGTH_SHORT).show();

                        TableRow tbrow1 = new TableRow(getContext());
                        TextView tvo = new TextView(getContext());
                        tvo.setText(hit.getString("Sno"));
                        tvo.setTextColor(Color.BLACK);
                        tvo.setGravity(Gravity.CENTER);
                        tvo.setPadding(10,10,10,10);
                        tvo.setTextSize(15);
                        tbrow1.addView(tvo);

                        TextView t3 = new TextView(getContext());
                        t3.setText(hit.getString("date"));
                        t3.setTextColor(Color.BLACK);
                        t3.setPadding(10,10,10,10);
                        t3.setTextSize(15);
                        tbrow1.addView(t3);

                        TextView t1 = new TextView(getContext());
                        t1.setText(hit.getString("recno"));
                        t1.setTextColor(Color.BLACK);
                        t1.setGravity(Gravity.CENTER);
                        t1.setPadding(10,10,10,10);
                        t1.setTextSize(15);
                        tbrow1.addView(t1);

                        TextView t2 = new TextView(getContext());
                        t2.setText(hit.getString("chno"));
                        t2.setTextColor(Color.BLACK);
                        t2.setGravity(Gravity.CENTER);
                        t2.setPadding(10,10,10,10);
                        t2.setTextSize(15);
                        tbrow1.addView(t2);

                        TextView t5 = new TextView(getContext());
                        t5.setText(hit.getString("pmode"));
                        t5.setTextColor(Color.BLACK);
                        t5.setGravity(Gravity.CENTER);
                        t5.setPadding(10,10,10,10);
                        t5.setTextSize(15);
                        tbrow1.addView(t5);



                        TextView t4 = new TextView(getContext());
                        t4.setText(hit.getString("pexceedplusamount"));
                        t4.setTextColor(Color.BLACK);
                        t4.setGravity(Gravity.CENTER);
                        t4.setPadding(10,10,10,10);
                        t4.setTextSize(15);
                        tbrow1.addView(t4);










                        stk.addView(tbrow1);

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



        TableLayout stk2= v.findViewById(R.id.table2);
        TableRow tbrow2= new TableRow(getContext());

        TextView tvo2=new TextView(getContext());
        tvo2.setText("S.No");
        tvo2.setPadding(10,10,10,10);
        tvo2.setTextSize(15);
        tvo2.setTypeface(Typeface.DEFAULT_BOLD);
        tvo2.setTextColor(Color.BLACK);
        tvo2.setGravity(Gravity.CENTER);
        tbrow2.addView(tvo2);

        TextView tv32=new TextView(getContext());
        tv32.setText("Date");
        tv32.setGravity(Gravity.CENTER);
        tv32.setTextColor(Color.BLACK);
        tv32.setPadding(10,10,10,10);
        tv32.setTextSize(15);
        tv32.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow2.addView(tv32);

        TextView tv12=new TextView(getContext());
        tv12.setText("Cheque No.");
        tv12.setTextColor(Color.BLACK);
        tv12.setPadding(10,10,10,10);
        tv12.setTextSize(15);
        tv12.setTypeface(Typeface.DEFAULT_BOLD);
        tv12.setGravity(Gravity.CENTER);
        tbrow2.addView(tv12);

        TextView tv22=new TextView(getContext());
        tv22.setText("Amount");
        tv22.setGravity(Gravity.CENTER);
        tv22.setTextColor(Color.BLACK);
        tv22.setPadding(10,10,10,10);
        tv22.setTextSize(15);
        tv22.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow2.addView(tv22);









        stk2.addView(tbrow2);

        RequestQueue rq2 = Volley.newRequestQueue(getContext());
        String url2 = "http://14.139.85.169/jspapi/stu_fee.jsp?regno="+regno;
        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = response.getJSONArray("concess");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        //Toast.makeText(getContext(), Integer.toString(jsonArray.length()), Toast.LENGTH_SHORT).show();

                        TableRow tbrow12 = new TableRow(getContext());
                        TextView tvo2 = new TextView(getContext());
                        tvo2.setText(hit.getString("Sno"));
                        tvo2.setTextColor(Color.BLACK);
                        tvo2.setGravity(Gravity.CENTER);
                        tvo2.setPadding(10,10,10,10);
                        tvo2.setTextSize(15);
                        tbrow12.addView(tvo2);

                        TextView t32 = new TextView(getContext());
                        t32.setText(hit.getString("date"));
                        t32.setTextColor(Color.BLACK);
                        t32.setPadding(10,10,10,10);
                        t32.setTextSize(15);
                        tbrow12.addView(t32);

                        TextView t12 = new TextView(getContext());
                        t12.setText(hit.getString("cheq"));
                        t12.setTextColor(Color.BLACK);
                        t12.setGravity(Gravity.CENTER);
                        t12.setPadding(10,10,10,10);
                        t12.setTextSize(15);
                        tbrow12.addView(t12);

                        TextView t22 = new TextView(getContext());
                        t22.setText(hit.getString("refamt"));
                        t22.setTextColor(Color.BLACK);
                        t22.setGravity(Gravity.CENTER);
                        t22.setPadding(10,10,10,10);
                        t22.setTextSize(15);
                        tbrow12.addView(t22);



                        stk2.addView(tbrow12);

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
        rq2.add(request2);


        return v;
    }
}