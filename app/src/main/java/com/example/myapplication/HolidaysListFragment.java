package com.example.myapplication;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class HolidaysListFragment extends Fragment {
    LinearLayout expandableview;
    ImageButton button;
    CardView cardView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_holidays_list, container, false);

        cardView = v.findViewById(R.id.base_cardview);
        expandableview = v.findViewById(R.id.hidden_view);
        button = v.findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableview.getVisibility()==View.GONE){
//                    TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(400L);
                    TransitionManager.beginDelayedTransition(cardView, transition);
                    expandableview.setVisibility(View.VISIBLE);
                    button.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);

                }
                else{
                    //TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
                    final ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(400L);
                    TransitionManager.beginDelayedTransition(cardView, transition);
                    expandableview.setVisibility(View.GONE);
                    button.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });
        return v;
    }
}