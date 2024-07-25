package com.example.myapplication;

import static android.view.View.GONE;

import static com.example.myapplication.Academics.m;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class FinalMarks extends Fragment {

    ImageButton button,button2,button3,button4,button5,button6,button7,button8;
    CardView cardView,cardView2,cardView3,cardView4,cardView5,cardView6,cardView7,cardView8;
    RelativeLayout expandableview,expandableview2,expandableview3,expandableview4,expandableview5,expandableview6,expandableview7,expandableview8;
    SharedPreferences preferences;
    RequestQueue rq;
    String year="",sem="";
    String regno;
    View v;

    TextView y11,y12,y21,y22,y31,y32,y41,y42,passed,credits,backlogs,aggregate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_final_marks, container, false);




        MainActivity2 mainActivity2 = (MainActivity2) getActivity();
        mainActivity2.findViewById(R.id.att_sp).setVisibility(View.GONE);
        cardView = v.findViewById(R.id.base_cardview);
        cardView2 = v.findViewById(R.id.base_cardview2);
        cardView3 = v.findViewById(R.id.base_cardview3);
        cardView4 = v.findViewById(R.id.base_cardview4);
        cardView5 = v.findViewById(R.id.base_cardview5);
        cardView6 = v.findViewById(R.id.base_cardview6);
        cardView7 = v.findViewById(R.id.base_cardview7);
        cardView8 = v.findViewById(R.id.base_cardview8);


        expandableview = v.findViewById(R.id.hidden_view);
        expandableview2 = v.findViewById(R.id.hidden_view2);
        expandableview3 = v.findViewById(R.id.hidden_view3);
        expandableview4 = v.findViewById(R.id.hidden_view4);
        expandableview5 = v.findViewById(R.id.hidden_view5);
        expandableview6 = v.findViewById(R.id.hidden_view6);
        expandableview7 = v.findViewById(R.id.hidden_view7);
        expandableview8 = v.findViewById(R.id.hidden_view8);


        button = v.findViewById(R.id.button);
        button2 = v.findViewById(R.id.button2);
        button3 = v.findViewById(R.id.button3);
        button4 = v.findViewById(R.id.button4);
        button5 = v.findViewById(R.id.button5);
        button6 = v.findViewById(R.id.button6);
        button7 = v.findViewById(R.id.button7);
        button8 = v.findViewById(R.id.button8);

        y11 = v.findViewById(R.id.y11);
        y12 = v.findViewById(R.id.y12);
        y21 = v.findViewById(R.id.y21);
        y22 = v.findViewById(R.id.y22);
        y31 = v.findViewById(R.id.y31);
        y32 = v.findViewById(R.id.y32);
        y41 = v.findViewById(R.id.y41);
        y42 = v.findViewById(R.id.y42);

        passed =v.findViewById(R.id.passed);
        backlogs =v.findViewById(R.id.backlogs);
        credits =v.findViewById(R.id.credits);
        aggregate =v.findViewById(R.id.percentage);

        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        regno = preferences.getString("regno","");

        rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/aggregate_api.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    String backlogs_=response.getString("Backlogs");
                    backlogs.setText(response.getString("Backlogs"));
                    passed.setText(response.getString("totPassedSub"));
                    credits.setText(response.getString("Credits"));
                    backlogs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(),backlogs_,Toast.LENGTH_SHORT).show();
                        }
                    });
                    if((response.getString("Aggregate")).equals("?"))
                    {
                        aggregate.setText("0.00");
                    }
                    else
                        aggregate.setText(response.getString("Aggregate"));

                    JSONArray jsonArray = response.getJSONArray("Percentage");
                    for (int i = 0; i < 1; i++) {
                        Log.d("Len",Integer.toString(jsonArray.length()));
                        JSONObject hit = jsonArray.getJSONObject(i);
                        y11.setText(hit.getString("y11"));
                        y12.setText(hit.getString("y12"));
                        y21.setText(hit.getString("y21"));
                        y22.setText(hit.getString("y22"));
                        y31.setText(hit.getString("y31"));
                        y32.setText(hit.getString("y32"));
                        y41.setText(hit.getString("y41"));
                        y42.setText(hit.getString("y42"));
                        Log.d("OTPT1",response.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("OTPT",e.toString());
                    Log.d("OTPT1",response.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        rq.add(request);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview.getVisibility()== GONE)
                {
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView, transition);
                    expandableview.setVisibility(View.VISIBLE);
                    button.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="1",sem="1",tbn="table1";
                    int id = R.id.table;
                    init(year,sem,id);
                }
                else
                {
                    TableLayout stk=(TableLayout) v.findViewById(R.id.table);
                    stk.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(250L);
                    TransitionManager.beginDelayedTransition(cardView, transition);
                    expandableview.setVisibility(GONE);
                    button.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview2.getVisibility()== GONE){
//                    TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView2, transition);
                    expandableview2.setVisibility(View.VISIBLE);
                    button2.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="1",sem="2";
                    int id = R.id.table2;
                    init(year,sem,id);
                }
                else{
                    TableLayout stk2=(TableLayout) v.findViewById(R.id.table2);
                    stk2.removeAllViews();
                    //TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView2, transition);
                    expandableview2.setVisibility(GONE);
                    button2.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }

            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview3.getVisibility()== GONE){final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView3, transition);
                    expandableview3.setVisibility(View.VISIBLE);
                    button3.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="2",sem="1"; int id=R.id.table3;
                    init(year,sem,id);

                }
                else{
                    TableLayout stk3=(TableLayout) v.findViewById(R.id.table3);
                    stk3.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView3, transition);
                    expandableview3.setVisibility(GONE);
                    button3.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);

                }

            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview4.getVisibility()== GONE){final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView4, transition);
                    expandableview4.setVisibility(View.VISIBLE);
                    button4.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="2",sem="2";int id=R.id.table4;
                    init(year,sem,id);

                }
                else{
                    TableLayout stk4=(TableLayout) v.findViewById(R.id.table4);
                    stk4.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView4, transition);
                    expandableview4.setVisibility(GONE);
                    button4.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }

            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview5.getVisibility()== GONE){final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView5, transition);
                    expandableview5.setVisibility(View.VISIBLE);
                    button5.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="3",sem="1";int id=R.id.table5;
                    init(year,sem,id);

                }
                else{
                    TableLayout stk5=(TableLayout) v.findViewById(R.id.table5);
                    stk5.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView5, transition);
                    expandableview5.setVisibility(GONE);
                    button5.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }

            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview6.getVisibility()== GONE){final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView6, transition);
                    expandableview6.setVisibility(View.VISIBLE);
                    button6.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="3",sem="2";int id=R.id.table6;
                    init(year,sem,id);

                }
                else{
                    TableLayout stk6=(TableLayout) v.findViewById(R.id.table6);
                    stk6.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView6, transition);
                    expandableview6.setVisibility(GONE);
                    button6.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }

            }
        });

        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview7.getVisibility()== GONE){final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView7, transition);
                    expandableview7.setVisibility(View.VISIBLE);
                    button7.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="4",sem="1";int id=R.id.table7;
                    init(year,sem,id);

                }
                else{
                    TableLayout stk7=(TableLayout) v.findViewById(R.id.table7);
                    stk7.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView7, transition);
                    expandableview7.setVisibility(GONE);
                    button7.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }

            }
        });

        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview8.getVisibility()== GONE){final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView8, transition);
                    expandableview8.setVisibility(View.VISIBLE);
                    button8.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    String year="4",sem="2";int id=R.id.table8;
                    init(year,sem,id);

                }
                else{
                    TableLayout stk8=(TableLayout) v.findViewById(R.id.table8);
                    stk8.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(350L);
                    TransitionManager.beginDelayedTransition(cardView8, transition);
                    expandableview8.setVisibility(GONE);
                    button8.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });
        return v;
    }
    public void init(String year,String sem,int id)
    {

        preferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        regno = preferences.getString("regno","");
        //String name = getResources().getResourceEntryName(tbn);

        TableLayout stk=(TableLayout) v.findViewById(id);
        TableRow tbrow= new TableRow(getContext());
        TextView tvo=new TextView(getContext());
        tvo.setText("No.");
        tvo.setPadding(10,10,10,10);
        tvo.setTextSize(15);
        tvo.setTypeface(Typeface.DEFAULT_BOLD);
        tvo.setTextColor(Color.BLACK);
        tvo.setGravity(Gravity.CENTER);

        tbrow.addView(tvo);

        TextView tv1=new TextView(getContext());
        tv1.setText("SUBJECT CODE");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(10,10,10,10);
        tv1.setTextSize(15);
        tv1.setTypeface(Typeface.DEFAULT_BOLD);
        tv1.setGravity(Gravity.CENTER);
        tbrow.addView(tv1);

        TextView tv2=new TextView(getContext());
        tv2.setText("SUBJECT NAME");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(10,10,10,10);
        tv2.setTextSize(15);
        tv2.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv2);

        TextView tv3=new TextView(getContext());
        tv3.setText("INT");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(10,10,10,10);
        tv3.setTextSize(15);
        tv3.setTypeface(Typeface.DEFAULT_BOLD);

        tbrow.addView(tv3);

        TextView tv4=new TextView(getContext());
        tv4.setText("EXT");
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor(Color.BLACK);
        tv4.setPadding(10,10,10,10);
        tv4.setTextSize(15);
        tv4.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv4);
        TextView tv9=new TextView(getContext());
        tv9.setText("TOT");
        tv9.setGravity(Gravity.CENTER);
        tv9.setTextColor(Color.BLACK);
        tv9.setPadding(10,10,10,10);
        tv9.setTextSize(15);
        tv9.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv9);


        TextView tv5=new TextView(getContext());
        tv5.setText("GRADE");
        tv5.setTextColor(Color.BLACK);
        tv5.setGravity(Gravity.CENTER);
        tv5.setPadding(10,10,10,10);
        tv5.setTextSize(15);
        tv5.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv5);

        TextView tv6=new TextView(getContext());
        tv6.setText("GRADE PTS");
        tv6.setGravity(Gravity.CENTER);
        tv6.setTextColor(Color.BLACK);
        tv6.setPadding(10,10,10,10);
        tv6.setTextSize(15);
        tv6.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv6);

        TextView tv7=new TextView(getContext());
        tv7.setText("RESULT");
        tv7.setGravity(Gravity.CENTER);
        tv7.setTextColor(Color.BLACK);
        tv7.setPadding(10,10,10,10);
        tv7.setTextSize(15);
        tv7.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv7);

        TextView tv8=new TextView(getContext());
        tv8.setText("MONTH-YEAR");
        tv8.setGravity(Gravity.CENTER);
        tv8.setTextColor(Color.BLACK);
        tv8.setPadding(10,10,10,10);
        tv8.setTextSize(15);
        tv8.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv8);

        stk.addView(tbrow);



        rq = Volley.newRequestQueue(getContext());
        String url = "http://14.139.85.169/jspapi/finalmarks_api.jsp?regno="+regno+"&year="+year+"&sem="+sem;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = response.getJSONArray(year+"year"+sem+"semester");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        //Toast.makeText(getContext(), Integer.toString(jsonArray.length()), Toast.LENGTH_SHORT).show();

                        TableRow tbrow1 = new TableRow(getContext());
                        TextView t1 = new TextView(getContext());
                        t1.setText(hit.getString("Sno"));
                        t1.setTextColor(Color.BLACK);
                        t1.setGravity(Gravity.CENTER);
                        t1.setPadding(10,10,10,10);
                        t1.setTextSize(15);
                        tbrow1.addView(t1);
                        TextView t2 = new TextView(getContext());
                        t2.setText(hit.getString("subjectcode"));
                        t2.setTextColor(Color.BLACK);
                        t2.setGravity(Gravity.CENTER);
                        t2.setPadding(10,10,10,10);
                        t2.setTextSize(15);
                        tbrow1.addView(t2);
                        TextView t3 = new TextView(getContext());
                        t3.setText(hit.getString("subjectname"));
                        t3.setTextColor(Color.BLACK);
                        t3.setPadding(10,10,10,10);
                        t3.setTextSize(15);
                        tbrow1.addView(t3);
                        TextView t4 = new TextView(getContext());
                        t4.setText(hit.getString("int"));
                        t4.setTextColor(Color.BLACK);
                        t4.setGravity(Gravity.CENTER);
                        t4.setPadding(10,10,10,10);
                        t4.setTextSize(15);
                        tbrow1.addView(t4);
                        TextView t5 = new TextView(getContext());
                        t5.setText(hit.getString("ext"));
                        t5.setTextColor(Color.BLACK);
                        t5.setGravity(Gravity.CENTER);
                        t5.setPadding(10,10,10,10);
                        t5.setTextSize(15);
                        tbrow1.addView(t5);
                        TextView t10 = new TextView(getContext());
                        t10.setText(hit.getString("tot"));
                        t10.setTextColor(Color.BLACK);
                        t10.setGravity(Gravity.CENTER);
                        t10.setPadding(10,10,10,10);
                        t10.setTextSize(15);
                        tbrow1.addView(t10);
                        TextView t6 = new TextView(getContext());
                        t6.setText(hit.getString("grade"));
                        t6.setTextColor(Color.BLACK);
                        t6.setGravity(Gravity.CENTER);
                        t6.setPadding(10,10,10,10);
                        t6.setTextSize(15);

                        tbrow1.addView(t6);
                        TextView t7 = new TextView(getContext());
                        t7.setText(hit.getString("gradepoints"));
                        t7.setTextColor(Color.BLACK);
                        t7.setGravity(Gravity.CENTER);
                        t7.setPadding(10,10,10,10);
                        tvo.setTextSize(15);
                        tbrow1.addView(t7);
                        TextView t8 = new TextView(getContext());
                        t8.setText(hit.getString("status"));
                        t8.setTextColor(Color.BLACK);
                        t8.setGravity(Gravity.CENTER);
                        t8.setPadding(10,10,10,10);
                        tvo.setTextSize(15);
                        tbrow1.addView(t8);
                        TextView t9 = new TextView(getContext());
                        t9.setText(hit.getString("monthyear"));
                        t9.setTextColor(Color.BLACK);
                        t9.setGravity(Gravity.CENTER);
                        t9.setPadding(10,10,10,10);
                        t9.setTextSize(15);
                        tbrow1.addView(t9);


                        stk.addView(tbrow1);

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

    }
}