package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity2 extends AppCompatActivity {
    String name, regno_ = "";
    RequestQueue rq;
    private HomeFragment homeFragment;
    public Spinner att_sp;
    String semester[];

    public static String in_year1 = "", in_sem = "", from_ = "", to_ = "";
    SharedPreferences preferences;
    ImageView more, refresh;
    private Disposable networkDisposable;
    private Disposable internetDisposable;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rq = Volley.newRequestQueue(this);
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String no = preferences.getString("regno", "");
        drawerLayout = findViewById(R.id.drawerLayout);



        findViewById(R.id.imgmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.hostFrag);
        NavigationUI.setupWithNavController(navigationView, navController);


        att_sp = (Spinner) findViewById(R.id.att_sp);
        refresh = findViewById(R.id.refresh);
        more = findViewById(R.id.more);
        TextView textTitle = findViewById(R.id.textTitle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
                RequestQueue rq1;
                rq1 = Volley.newRequestQueue(getApplicationContext());
                String no = preferences.getString("regno", "");
                String url1 = "http://14.139.85.169/jspapi/personal_details.jsp?regno=" + no;
                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String sem = (hit.getString("semester"));
                                String year = (hit.getString("cyear"));
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("yr", year);
                                editor.putString("sm", sem);
                                editor.apply();
                            }
                        } catch (JSONException e) {
                            Log.d("error_in", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                rq1.add(request1);


                String dropdown[] = selectsem();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner, dropdown);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                if (destination.getLabel().equals("Academics")) {
                    att_sp.setVisibility(View.VISIBLE);
                    more.setVisibility(View.GONE);
                    refresh.setVisibility(View.GONE);
                    att_sp.setAdapter(adapter);
                    att_sp.setSelection(adapter.getCount() - 1, true);
                    String[] parts1 = dropdown[adapter.getCount() - 1].split(Pattern.quote("-"));
                    String part1 = parts1[0]; // 004
                    String part2 = parts1[1]; // 034556
                    in_year1 = part1;
                    in_sem = part2;

                    WeeklyFragment weeklyFragment = new WeeklyFragment();
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.flFragment, weeklyFragment).commit();

                    att_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);

                            if (item != null && !(att_sp.getSelectedItem().toString().equals("Select sem"))) {
                                String in_year = att_sp.getSelectedItem().toString();
                                String[] parts1 = in_year.split(Pattern.quote("-"));
                                String part1 = parts1[0]; // 004
                                String part2 = parts1[1]; // 034556
                                in_year1 = part1;
                                in_sem = part2;

                                WeeklyFragment weeklyFragment = new WeeklyFragment();
                                FragmentManager fragmentManager1 = getSupportFragmentManager();
                                fragmentManager1.beginTransaction().replace(R.id.flFragment, weeklyFragment).commit();

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else if (destination.getLabel().equals("Attendance")) {
                    att_sp.setVisibility(View.GONE);
                    more.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.VISIBLE);
                    refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getApplicationContext(),"Current Attendance",Toast.LENGTH_SHORT).show();
                            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.flFragment2, new Attendance(), "NewFragmentTag");
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    });
                    more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Dialog dialog = new Dialog(MainActivity2.this);
                            dialog.setContentView(R.layout.alertdialog);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.setCancelable(true);
                            // dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                            dialog.show();
                            EditText from = dialog.findViewById(R.id.from);
                            from.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final java.util.Calendar calendar = java.util.Calendar.getInstance();
                                    final int year = calendar.get(java.util.Calendar.YEAR);
                                    final int month = calendar.get(java.util.Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    DatePickerDialog dialog1 = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                            month = month + 1;
                                            String date = dayOfMonth + "-" + month + "-" + year;
                                            from.setText(date);

                                        }
                                    }, year, month, day);
                                    dialog1.show();
                                }
                            });
                            EditText to = dialog.findViewById(R.id.to);
                            to.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final java.util.Calendar calendar = java.util.Calendar.getInstance();
                                    final int year = calendar.get(java.util.Calendar.YEAR);
                                    final int month = calendar.get(java.util.Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    DatePickerDialog dialog2 = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                            month = month + 1;
                                            String date = dayOfMonth + "-" + month + "-" + year;
                                            to.setText(date);

                                        }
                                    }, year, month, day);
                                    dialog2.show();

                                }
                            });
                            Button ok = dialog.findViewById(R.id.ok);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    from_ = from.getText().toString();
                                    to_ = to.getText().toString();
                                    if (from_.length() == 0 || to_.length() == 0) {
                                        Toast.makeText(getApplicationContext(), "Please select both dates to continue", Toast.LENGTH_SHORT).show();
                                    } else {
                                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.flFragment2, new Attendance2(), "NewFragmentTag");
                                        ft.addToBackStack(null);
                                        ft.commit();
                                        dialog.dismiss();
                                    }
                                }
                            });


                        }
                    });

                } else {
                    att_sp.setVisibility(View.GONE);
                    more.setVisibility(View.GONE);
                    refresh.setVisibility(View.GONE);
                }


            }
        });


        String url = "http://14.139.85.169/jspapi/personal_details.jsp?regno=" + no;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < 1; i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        View headerView = navigationView.getHeaderView(0);
                        TextView name = (TextView) headerView.findViewById(R.id.name);
                        name.setText(hit.getString("name"));
                        TextView regno = (TextView) headerView.findViewById(R.id.regno);
                        regno.setText(no);
                        ImageView imageView = (ImageView) headerView.findViewById(R.id.profile);
                        Glide.with(getApplicationContext())
                                .load("http://14.139.85.169/jspapi/sp/" + no + ".jpg")
                                .into(imageView);
                    }
                } catch (JSONException e) {
                    Log.d("msge", e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg", error.toString());
            }
        });


        rq.add(request);


    }

    public String[] selectsem() {
        String users[] = {"Select user", "Student", "Parent"};
        int y = 0, s = 0;
        y = Integer.parseInt(preferences.getString("yr", "0"));
        s = Integer.parseInt(preferences.getString("sm", "0"));

        if (y == 1 && s == 1)
            return new String[]{"Select sem", "1-1"};
        else if (y == 1 && s == 2)
            return new String[]{"Select sem", "1-1", "1-2"};
        else if (y == 2 && s == 1)
            return new String[]{"Select sem", "1-1", "1-2", "2-1"};
        else if (y == 2 && s == 2)
            return new String[]{"Select sem", "1-1", "1-2", "2-1", "2-2"};
        else if (y == 3 && s == 1)
            return new String[]{"Select sem", "1-1", "1-2", "2-1", "2-2", "3-1"};
        else if (y == 3 && s == 2)
            return new String[]{"Select sem", "1-1", "1-2", "2-1", "2-2", "3-1", "3-2"};
        else if (y == 4 && s == 1)
            return new String[]{"Select sem", "1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1"};
        else if (y == 4 && s == 2)
            return new String[]{"Select sem", "1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2"};
        return users;
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
            Snackbar.make(drawerLayout,"Please connect to the internet",Snackbar.LENGTH_LONG).show();
        }
//        else{
//            if(status==0) {
//                status=1;
//                Snackbar.make(drawerLayout, "Connected to the internet", Snackbar.LENGTH_SHORT).show();
//            }


        //}
    }

}