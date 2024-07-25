package com.example.myapplication;

import static java.lang.Math.round;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.AdapterViewHolder> {
    private Context ctx;
    private ArrayList<Variables2> arrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public Adapter2(Context ctx, ArrayList<Variables2> arrayList) {
        this.ctx = ctx;
        this.arrayList = arrayList;
    }


    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.print_layout2,parent,false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        Variables2 variables2 = arrayList.get(position);

        final String subjectname_ = variables2.getSubjectname();
        final String wt1_ = variables2.getWt1();
        final String wt2_ = variables2.getWt2();
        final String wt3_ = variables2.getWt3();
        final String wt4_ = variables2.getWt4();
        final String wt5_ = variables2.getWt5();
        final String wt6_ = variables2.getWt6();
        final String wt7_ = variables2.getWt7();


        holder.subjectname.setText(subjectname_);
        holder.wt1.setText(wt1_);
        holder.wt2.setText(wt2_);
        holder.wt3.setText(wt3_);
        holder.wt4.setText(wt4_);
        holder.wt5.setText(wt5_);
        holder.wt6.setText(wt6_);
        holder.wt7.setText(wt7_);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView subjectname,wt1,wt2,wt3,wt4,wt5,wt6,wt7;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectname =(TextView) itemView.findViewById(R.id.subjectname);
            wt1 =(TextView) itemView.findViewById(R.id.wt1);
            wt2 =(TextView) itemView.findViewById(R.id.wt2);
            wt3 =(TextView) itemView.findViewById(R.id.wt3);
            wt4 =(TextView) itemView.findViewById(R.id.wt4);
            wt5 =(TextView) itemView.findViewById(R.id.wt5);
            wt6 =(TextView) itemView.findViewById(R.id.wt6);
            wt7 =(TextView) itemView.findViewById(R.id.wt7);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(mListener !=null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION) {
//                            mListener.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }
    }
}