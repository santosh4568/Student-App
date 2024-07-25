package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class OthersFragment extends Fragment {
    Button softskills,scholarshipdetails,recognitiondetails,cocurriculardetails,intern,project,placement,internaltraining,committeescoordination,codingprofile,activitiescoordination,publicationdetails,highereducation;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_others, container, false);



        intern=v.findViewById(R.id.intern);
        project=v.findViewById(R.id.project);
        placement=v.findViewById(R.id.placement);
        internaltraining=v.findViewById(R.id.internaltraining);
        codingprofile=v.findViewById(R.id.codingprofile);
        committeescoordination=v.findViewById(R.id.committeescoordination);
        activitiescoordination=v.findViewById(R.id.activitiescoordination);
        publicationdetails=v.findViewById(R.id.publicationdetails);
        highereducation=v.findViewById(R.id.highereducation);
        softskills=v.findViewById(R.id.softskills);
        scholarshipdetails=v.findViewById(R.id.scholarshipdetails);
        recognitiondetails=v.findViewById(R.id.recognitiondetails);
        cocurriculardetails=v.findViewById(R.id.cocurriculardetails);


        intern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),InternActivity.class);
                startActivity(i);
            }
        });
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getContext(),project.class);
                startActivity(j);
            }
        });
        placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),PlacementActivity.class);
                startActivity(z);
            }
        });
        internaltraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),InternaltrainingActivity.class);
                startActivity(z);
            }
        });
        codingprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),CodingprofileActivity.class);
                startActivity(z);
            }
        });
        committeescoordination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),CommitteeActivity.class);
                startActivity(z);

            }
        });
        activitiescoordination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),ActivitiescoordinationActivity.class);
                startActivity(z);

            }
        });
        publicationdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),publicationdetails.class);
                startActivity(z);
            }
        });
        highereducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),highereducationdetails.class);
                startActivity(z);
            }
        });
        softskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),softskillsdetails.class);
                startActivity(z);
            }
        });
        scholarshipdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),scholarshipdetais.class);
                startActivity(z);
            }
        });
        recognitiondetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),recognitiondetails.class);
                startActivity(z);
            }
        });
        cocurriculardetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getContext(),cocurriculardetails.class);
                startActivity(z);
            }
        });
        return  v;
    }
}