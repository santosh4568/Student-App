package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class Attendance extends Fragment {
    int value;
    ProgressBar p1;
    ImageView notFound;
    String regno,section,semester,year,branchcode,coursecode,doj;
    SharedPreferences preferences;
    RequestQueue rq;
    ImageView loading;
    TextView total,present,absent,percentage;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Variables> arrayList;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_attendance, container, false);
        total = v.findViewById(R.id.total);
        notFound = v.findViewById(R.id.notFound);
        present = v.findViewById(R.id.present);
        absent = v.findViewById(R.id.absent);
        percentage = v.findViewById(R.id.percentage);
        loading=v.findViewById(R.id.load);

        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        regno = preferences.getString("regno","");
        section = preferences.getString("section","");
        year = preferences.getString("year","");
        semester = preferences.getString("semester","");
        branchcode = preferences.getString("branchcode","");
        coursecode = preferences.getString("coursecode","");
        doj = preferences.getString("doj","");

       // Log.d("sp",regno+section+year+semester+branchcode+coursecode);

        rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/main_attendance.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    total.setText(response.getString("TotalHours"));
                    present.setText(response.getString("TotalAttendedHours"));
                    absent.setText(response.getString("TotalAbsentHrs"));
                    if(response.getString("AttendancePercentage").equals("?"))
                        percentage.setText("0.00");
                    else
                        percentage.setText(response.getString("AttendancePercentage"));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ATTEE",e.toString());
                    Log.d("ATTRR",response.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("att_err_out",error.toString());

            }
        });
        rq.add(request);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());


        String URL = "http://14.139.85.169/jspapi/main_attendance.jsp?regno="+regno;

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("rcycler1",response.toString());
                loading.setVisibility(View.GONE);
                try {
                    NumberFormat nf = new DecimalFormat("0.0");
                    JSONArray jsonArray = response.getJSONArray("Subjects");
                    JSONArray jsonArray1 = response.getJSONArray("SubjectHoursAttended");
                    JSONArray jsonArray2 = response.getJSONArray("SubjectHoursTotal");
                    JSONArray jsonArray3 = response.getJSONArray("SubjectCount");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject hit = jsonArray.getJSONObject(i);
                        JSONObject hit1 = jsonArray1.getJSONObject(i);
                        JSONObject hit2 = jsonArray2.getJSONObject(i);
                        JSONObject hit3 = jsonArray3.getJSONObject(i);

                        for(int j=0;j<hit3.getInt("Count");j++) {

                            String subjectname_ = hit.getString("Sub " + j);
                            String present = hit1.getString("Sub " + j);
                            String total_ = hit2.getString("Sub " + j);
                            String percentage_ = String.valueOf(nf.format((Double.parseDouble(present) * 100) / Double.parseDouble(total_)));

                            Log.v("rcycler", total_ + "  " + present);
                            if (percentage_.equals("?") || percentage_.equals("-?") || percentage_.equals("NaN"))
                                percentage_ = "0.00";

                            arrayList.add(new Variables(subjectname_, total_, present, percentage_, p1));
                        }
                    }
                    adapter = new Adapter(getContext(), arrayList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    loading.setVisibility(View.GONE);
                    notFound.setVisibility(View.VISIBLE);
                    Log.v("att_error_in2",e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                notFound.setVisibility(View.VISIBLE);
                error.printStackTrace();
                Log.v("att_error_out2",error.toString());
            }
        });
        requestQueue.add(request1);
        return v;
    }
}