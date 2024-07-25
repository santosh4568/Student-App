package com.example.myapplication;
import static android.view.View.GONE;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.graphics.Typeface;
import android.net.NetworkInfo;
import android.os.Bundle;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

        import android.text.SpannableString;
        import android.text.style.UnderlineSpan;
        import android.transition.ChangeBounds;
        import android.transition.TransitionManager;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.time.LocalDate;
        import java.util.HashMap;
        import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class highereducationdetails extends AppCompatActivity {
    SharedPreferences preferences;
    Button Submit;
    String type0[]={"Select Exam Type","GRE","IELTS","GATE","CAT","GMAT","TOEFL","DUOLINGO"};
    Spinner spinner,spinner1,spinner2;
    String type_,type1,type2;
    TextView roll,Names,companyname,pack;

    RequestQueue rq;
    ImageButton button;
    CardView cardView;
    RelativeLayout expandableview;
    String regno;
    private Disposable networkDisposable;
    ConstraintLayout highereducation;
    private Disposable internetDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highereducationdetails);

        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String name= preferences.getString("name","");
        regno=preferences.getString("regno","");

        Names=findViewById(R.id.name);
        roll=findViewById(R.id.roll);
        companyname=findViewById(R.id.nameofcompany);
        pack=findViewById(R.id.pack);
        Submit=findViewById(R.id.submit);
        Names.setText(name);
        highereducation=findViewById(R.id.highereducation);

        button=findViewById(R.id.button);
        cardView=findViewById(R.id.base_cardview);
        expandableview=findViewById(R.id.hidden_view);

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

                    init();
                }
                else
                {
                    TableLayout stk=findViewById(R.id.table);
                    stk.removeAllViews();
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(250L);
                    TransitionManager.beginDelayedTransition(cardView, transition);
                    expandableview.setVisibility(GONE);
                    button.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        roll.setText(regno);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, type0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.sp1);
        spinner.setSelection(2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    type_ = spinner.getSelectedItem().toString();
                    //Toast.makeText(getContext(), type_, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        String url = "http://14.139.85.169/jspapi/highereducation.jsp";
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Names_ = name.toUpperCase();
                String Nameofcompany_ = companyname.getText().toString();
                String Pack_ = pack.getText().toString();
                if (Pack_.length() != 0 && Nameofcompany_.length() != 0 && Names_.length() != 0) {

                    LocalDate myObj = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        myObj = LocalDate.now();
                    }


                    if (type_.equals("Select Exam Type")) {
                        spinner.setFocusable(true);
                        Toast.makeText(getApplicationContext(), "Please Select Exam Type", Toast.LENGTH_SHORT).show();
                    } else {
                        LocalDate finalMyObj = myObj;
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {


                                String flag = response.trim();
                                Log.d("res122", "" + response.trim());
                                if (flag.equals("Success")) {
                                    Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
                                    spinner.setSelection(0);
                                    companyname.setText("");
                                    pack.setText("");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v("msg122", error.toString());
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<String, String>();

                                param.put("regno", regno);
                                param.put("type", type_);
                                param.put("companyname", Nameofcompany_);
                                param.put("pack", Pack_);
                                param.put("date", String.valueOf(finalMyObj));


                                return param;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter All Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init() {

        preferences = getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        regno = preferences.getString("regno","");
        //String name = getResources().getResourceEntryName(tbn);

        TableLayout stk=findViewById(R.id.table);
        TableRow tbrow= new TableRow(getApplicationContext());

        TextView tvo=new TextView(getApplicationContext());
        tvo.setText("  S.No  ");
        tvo.setPadding(10,10,10,10);
        tvo.setTextSize(15);
        tvo.setTypeface(Typeface.DEFAULT_BOLD);
        tvo.setTextColor(Color.BLACK);
        tvo.setGravity(Gravity.CENTER);
        tbrow.addView(tvo);

        TextView tv1=new TextView(getApplicationContext());
        tv1.setText("  Regno  ");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(10,10,10,10);
        tv1.setTextSize(15);
        tv1.setTypeface(Typeface.DEFAULT_BOLD);
        tv1.setGravity(Gravity.CENTER);
        tbrow.addView(tv1);

        TextView tv2=new TextView(getApplicationContext());
        tv2.setText("  Exam Type  ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(10,10,10,10);
        tv2.setTextSize(15);
        tv2.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv2);

        TextView tv3=new TextView(getApplicationContext());
        tv3.setText("  Rank  ");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(10,10,10,10);
        tv3.setTextSize(15);
        tv3.setTypeface(Typeface.DEFAULT_BOLD);

        tbrow.addView(tv3);

        TextView tv4=new TextView(getApplicationContext());
        tv4.setText("  Marks  ");
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor(Color.BLACK);
        tv4.setPadding(10,10,10,10);
        tv4.setTextSize(15);
        tv4.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv4);

        TextView tv6=new TextView(getApplicationContext());
        tv6.setText("Entry Date  ");
        tv6.setGravity(Gravity.CENTER);
        tv6.setTextColor(Color.BLACK);
        tv6.setPadding(10,10,10,10);
        tv6.setTextSize(15);
        tv6.setTypeface(Typeface.DEFAULT_BOLD);
        tbrow.addView(tv6);


        stk.addView(tbrow);



        rq = Volley.newRequestQueue(getApplicationContext());
        String url1 = "http://14.139.85.169/jspapi/highereducationretrival.jsp?regno="+regno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = response.getJSONArray("highereducation");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        //Toast.makeText(getContext(), Integer.toString(jsonArray.length()), Toast.LENGTH_SHORT).show();

                        TableRow tbrow1 = new TableRow(getApplicationContext());
                        TextView t1 = new TextView(getApplicationContext());
                        t1.setText(hit.getString("Sno"));
                        t1.setTextColor(Color.BLACK);
                        t1.setGravity(Gravity.CENTER);
                        t1.setPadding(10,10,10,10);
                        t1.setTextSize(15);
                        tbrow1.addView(t1);
                        TextView t2 = new TextView(getApplicationContext());
                        t2.setText(hit.getString("regno"));
                        t2.setTextColor(Color.BLACK);
                        t2.setGravity(Gravity.CENTER);
                        t2.setPadding(10,10,10,10);
                        t2.setTextSize(15);
                        tbrow1.addView(t2);
                        TextView t3 = new TextView(getApplicationContext());
                        t3.setText(hit.getString("examname"));
                        t3.setGravity(Gravity.CENTER);
                        t3.setTextColor(Color.BLACK);
                        t3.setPadding(10,10,10,10);
                        t3.setTextSize(15);
                        tbrow1.addView(t3);
                        TextView t4 = new TextView(getApplicationContext());
                        t4.setText(hit.getString("rank"));
                        t4.setTextColor(Color.BLACK);
                        t4.setGravity(Gravity.CENTER);
                        t4.setPadding(10,10,10,10);
                        t4.setTextSize(15);
                        tbrow1.addView(t4);
                        TextView t5 = new TextView(getApplicationContext());
                        t5.setText(hit.getString("marks"));
                        t5.setTextColor(Color.BLACK);
                        t5.setGravity(Gravity.CENTER);
                        t5.setPadding(10,10,10,10);
                        t5.setTextSize(15);
                        tbrow1.addView(t5);
                        TextView t7 = new TextView(getApplicationContext());
                        t7.setText(hit.getString("entyrdate"));
                        t7.setTextColor(Color.BLACK);
                        t7.setGravity(Gravity.CENTER);
                        t7.setPadding(10,10,10,10);
                        t7.setTextSize(15);
                        tbrow1.addView(t7);


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
    int status=1;
    @Override protected void onResume() {
        super.onResume();

        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    //Log.d(TAG, connectivity.toString());
                    final NetworkInfo.State state = connectivity.state();
                    final String name = connectivity.typeName();
                    //tvConnectivityStatus.setText(String.format("state: %s, typeName: %s", state, name));
                });

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> in(isConnected.toString())
                );
    }

    @Override protected void onPause() {
        super.onPause();
        safelyDispose(networkDisposable, internetDisposable);
    }

    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
    public void in(String internet)
    {
        if(internet.equals("false"))
        {       status=0;
            Snackbar.make(highereducation,"Please connect to the internet",Snackbar.LENGTH_INDEFINITE).show();
        }
        else{
            if(status==0) {
                status=1;
                Snackbar.make(highereducation, "Connected to the internet", Snackbar.LENGTH_SHORT).show();
            }


        }
    }





}