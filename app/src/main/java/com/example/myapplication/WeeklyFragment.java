package com.example.myapplication;

import static com.example.myapplication.Academics.n;
import static com.example.myapplication.MainActivity2.in_sem;
import static com.example.myapplication.MainActivity2.in_year1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderSubTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeeklyFragment extends Fragment {
    WeeklyFragment context;
    RequestQueue rq;
    SharedPreferences preferences;
    View v;
    ImageView loading;
    private FixedHeaderTableLayout fixedHeaderTableLayout;

    FixedHeaderSubTableLayout mainTable;
    FixedHeaderSubTableLayout columnHeaderTable;
    FixedHeaderSubTableLayout rowHeaderTable;
    FixedHeaderSubTableLayout cornerTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_weekly, container, false);
        loading=v.findViewById(R.id.loading);
        rq = Volley.newRequestQueue(getContext());
        MainActivity2 mainActivity2 = (MainActivity2) getActivity();
        mainActivity2.findViewById(R.id.att_sp).setVisibility(View.VISIBLE);
        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String regno = preferences.getString("regno","");
        context = this;
        display(regno);

        return v;
    }
    public void display(String regno){
        if (checkContext(getContext())) {
            String url = "http://14.139.85.169/jspapi/weekly_mid_api_2.jsp?regno=" + regno + "&year=" + in_year1 + "&sem=" + in_sem;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("OTPT1", response.toString());
                    try {
                        loading.setVisibility(View.GONE);
                        fixedHeaderTableLayout = v.findViewById(R.id.FixedHeaderTableLayout);
                        if (checkContext(getContext()))
                            cornerTable = new FixedHeaderSubTableLayout(getContext());
                        for (int i = 1; i <= 1; i++) {
                            if (checkContext(getContext())) {
                                FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(getContext());
                                // Add some data
                                for (int j = 1; j <= 1; j++) {
                                    // Add a Textview

                                    TextView textView = new TextView(getContext());
                                    textView.setText("Exam");
                                    textView.setPadding(30, 20, 20, 20);
                                    textView.setTextSize(15);
                                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setBackgroundColor(Color.parseColor("#ed2d28"));
                                    textView.setGravity(Gravity.CENTER_HORIZONTAL);

                                    //textView.setTextColor(getResources().getColor(R.color.colorText));
                                    tableRowData.addView(textView);
                                }

                                cornerTable.addView(tableRowData);
                            }
                        }
                        int l1 = 0, l2 = 0;
                        JSONArray jsonArray00 = response.getJSONArray("nSubjects");
                        for (int i = 0; i < jsonArray00.length(); i++) {
                            JSONObject hit = jsonArray00.getJSONObject(i);
                            l1 = hit.getInt("nSubjects");
                        }
                        Log.d("subs", l1 + "");
                        JSONArray jsonArray01 = response.getJSONArray("nExams");
                        for (int i = 0; i < jsonArray01.length(); i++) {
                            JSONObject hit = jsonArray01.getJSONObject(i);
                            l2 = hit.getInt("nExams");
                        }
                        Log.d("subs", l2 + "");
                        JSONArray jsonArray = response.getJSONArray("Subjects");
                        if (checkContext(getContext()))
                            columnHeaderTable = new FixedHeaderSubTableLayout(getContext());
                        // 2 x 5 in size
                        for (int i = 0; i < 1; i++) {
                            if (checkContext(getContext())) {
                                FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(getContext());
                                JSONObject hit = jsonArray.getJSONObject(i);
                                // Add some data
                                for (int j = 0; j < l1; j++) {
                                    // Add a Textview
                                    if (checkContext(getContext())) {
                                        TextView textView = new TextView(getContext());
                                        textView.setGravity(Gravity.CENTER);
                                        textView.setPadding(30, 20, 20, 20);
                                        textView.setTextSize(15);
                                        textView.setTextColor(Color.DKGRAY);
                                        textView.setGravity(Gravity.LEFT);
                                        textView.setText(hit.getString("Subject" + j));
                                        //tvo2.setTypeface(Typeface.DEFAULT_BOLD);
                                        tableRowData.addView(textView);


                                        if (i == 0) {
                                            textView.setBackgroundColor(Color.parseColor("#ed2d28"));
                                            textView.setTextColor(Color.WHITE);
                                            textView.setPadding(30, 20, 20, 20);
                                        }
                                    }
                                }
                                columnHeaderTable.addView(tableRowData);
                            }
                        }

                        if (checkContext(getContext())) {
                            mainTable = new FixedHeaderSubTableLayout(getContext());
                            rowHeaderTable = new FixedHeaderSubTableLayout(getContext());
                        }
                        JSONArray jsonArray2 = response.getJSONArray("Marks");
                        // 25 x 5 in size
                        float textSize = 20.0f;
                        for (int x = 1; x <= l2; x++) {
                            if (checkContext(getContext())) {
                                FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(getContext());
                                FixedHeaderTableRow tableRowData1 = new FixedHeaderTableRow(getContext());
                                JSONObject hit2 = jsonArray2.getJSONObject(0);
                                for (int y = 0; y <= l1; y++) {
                                    if (checkContext(getContext())) {
                                        TextView tvo1 = new TextView(getContext());

                                        tvo1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                TableRow.LayoutParams.WRAP_CONTENT));
                                        tvo1.setMaxWidth(350);
                                        tvo1.setPadding(30, 20, 20, 20);
                                        tvo1.setTextSize(15);
                                        tvo1.setText(hit2.getString(x + "_" + y));
                                        tvo1.setTextColor(Color.BLACK);
                                        if (!hit2.getString(x + "_" + y).equals("-") && y != 0) {
                                            int marks = Integer.parseInt(hit2.getString(x + "_" + y));
                                        }
                                        if (!hit2.getString(x + "_" + y).equals("-") && y != 0 && (x > 7 && y < 11)) {
                                            int marks1 = Integer.parseInt(hit2.getString(x + "_" + y));
                                        }

                                        if (y != 0) {
                                            tableRowData.addView(tvo1);
                                        }
                                        else {
                                            tvo1.setTypeface(null,Typeface.BOLD);
                                            tableRowData1.addView(tvo1);
                                        }
                                    }
                                }
                                mainTable.addView(tableRowData);
                                mainTable.setBackgroundResource(R.drawable.bottom_border);
                                rowHeaderTable.addView(tableRowData1);
                            }
                        }
                        if (checkContext(getContext())) {
                            //rowHeaderTable.setBackgroundResource(R.drawable.right_border);

                            fixedHeaderTableLayout.setMinScale(0.1f);
                            fixedHeaderTableLayout.addViews(mainTable, columnHeaderTable, rowHeaderTable, cornerTable);
                        }
                        //TableRow tbrow= new TableRow(getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("int_error_in", e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("int_error_out", error.toString());
                }
            });
            rq.add(request);
        }
    }
    private boolean checkContext(Context context) {
        if(context != null)
            return true;
        else
            return false;
    }
}