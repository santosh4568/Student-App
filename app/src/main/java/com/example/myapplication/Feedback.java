package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends Fragment {
    SharedPreferences preferences;
    View v;
    String type0[]={"Choose a Feedback type","Bug","New feature","General feedback","Other"};
    Spinner spinner;
    Button submit;
    String type_;
    EditText title,message;
    RatingBar ratingbar;
    private static String userRate = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_feedback, container, false);
        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String regno = preferences.getString("regno","");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, type0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) v.findViewById(R.id.sp1);
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

        ratingbar = v.findViewById(R.id.ratingbar);
        submit = v.findViewById(R.id.submit);
        message = v.findViewById(R.id.msg);

        final RatingBar ratingbar = v.findViewById(R.id.ratingbar);
        final ImageView ratingimage = v.findViewById(R.id.ratingimage);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if(rating < 1){
                    ratingimage.setImageResource(R.drawable.question);
                }
                else if(rating <= 1){
                    ratingimage.setImageResource(R.drawable.emoji1);
                }
                else if(rating <=2){
                    ratingimage.setImageResource(R.drawable.emoji2);
                }
                else if(rating <=3){
                    ratingimage.setImageResource(R.drawable.emoji3);
                }
                else if(rating <=4){
                    ratingimage.setImageResource(R.drawable.emoji4);
                }
                else if(rating <=5){
                    ratingimage.setImageResource(R.drawable.emoji5);
                }
                animateImage(ratingimage);
                userRate = String.valueOf(rating);

            }
        });


        String url = "http://14.139.85.169/jspapi/feedback_api.jsp";
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String regno_ = regno.toUpperCase();
                String message_ = message.getText().toString();

                if (type_.equals("Feedback type")) {
                    spinner.setFocusable(true);
                    Toast.makeText(getContext(), "Please select feedback type", Toast.LENGTH_SHORT).show();
                }
                else if (ratingbar.getRating() == 0)
                {
                    Toast.makeText(getContext(), "Please select rating", Toast.LENGTH_SHORT).show();
                }
                else if(message_.length()==0)
                {
                    Toast.makeText(getContext(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            //Toast.makeText(getContext(),userRate+"",Toast.LENGTH_SHORT).show();

                            String flag = response.trim();
                            Log.d("res122", "" + response.trim());
                            if (flag.equals("Success")) {
                                Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("msg122", error.toString());
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<String, String>();
                            param.put("regno", regno_);
                             param.put("type", type_);
                            param.put("rating", userRate);
                            param.put("message", message_);
                            return param;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(request);
                }
            }
        });


        return v;
    }

    private void animateImage(ImageView ratingimage){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingimage.startAnimation(scaleAnimation);
    }

}