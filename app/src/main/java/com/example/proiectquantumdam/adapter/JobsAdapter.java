package com.example.proiectquantumdam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectquantumdam.R;
import com.example.proiectquantumdam.model.QuantumJob;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {


    class JobViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView jobId;
        TextView status;

        JobViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            jobId = mView.findViewById(R.id.job_id);
            status = mView.findViewById(R.id.job_status);


        }
    }

    private List<QuantumJob> jobs;
    private Context context;

    public JobsAdapter(Context context,List<QuantumJob> dataList){
        this.context = context;
        this.jobs = dataList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.job_cardview, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.jobId.setText(jobs.get(position).id);
        holder.status.setText(jobs.get(position).status);

        if(jobs.get(position).status.equals("Completed")) {
            holder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.job_completed, 0, 0, 0);
            holder.status.setCompoundDrawablePadding(2);
        }
        else{
            holder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.status_canceled, 0, 0, 0);
            holder.status.setCompoundDrawablePadding(2);
        }
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }
}
