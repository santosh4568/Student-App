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

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    private Context ctx;
    private ArrayList<Variables> arrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public Adapter(Context ctx, ArrayList<Variables> arrayList) {
        this.ctx = ctx;
        this.arrayList = arrayList;
    }


    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.print_layout,parent,false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        Variables variables = arrayList.get(position);

        final String subjectname_ = variables.getSubjectname();
        final String absent_ = variables.getAbsent();
        final String total_ = variables.getTotal();
        final String percentage_ = variables.getPercentage();
        final ProgressBar p1_ = variables.getP1();

        holder.p1.setProgress((int) round(Double.parseDouble(percentage_)));
        holder.subjectname.setText(subjectname_);
        holder.absent.setText(absent_);
        holder.total.setText(total_);
        holder.percentage.setText(percentage_);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView subjectname,absent,total,percentage;
        ProgressBar p1;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectname =(TextView) itemView.findViewById(R.id.subjectname);
            absent =(TextView) itemView.findViewById(R.id.absent);
            total = (TextView) itemView.findViewById(R.id.total);
            percentage = (TextView) itemView.findViewById(R.id.percentage);
            p1 = (ProgressBar)itemView.findViewById(R.id.p1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
