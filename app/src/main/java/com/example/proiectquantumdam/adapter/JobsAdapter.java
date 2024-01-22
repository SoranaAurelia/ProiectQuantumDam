package com.example.proiectquantumdam.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectquantumdam.JobViewActivity;
import com.example.proiectquantumdam.MainActivity;
import com.example.proiectquantumdam.R;
import com.example.proiectquantumdam.model.QuantumJob;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {


    class JobViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView jobId;
        TextView status;
        TextView backend;
        TextView dateCreated;

        JobViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            jobId = mView.findViewById(R.id.job_id);
            status = mView.findViewById(R.id.job_status);
            backend = mView.findViewById(R.id.job_backend);
            dateCreated = mView.findViewById(R.id.date_created);
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
        holder.jobId.setText(jobs.get(position).getId());
        holder.status.setText(jobs.get(position).getStatus());
        holder.backend.setText(jobs.get(position).getBackend());
        holder.dateCreated.setText(jobs.get(position).getCreated().toString());

        if(jobs.get(position).getStatus().equals("Completed")) {
            holder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.job_completed, 0, 0, 0);
            holder.status.setCompoundDrawablePadding(2);
        }
        else{
            holder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.status_canceled, 0, 0, 0);
            holder.status.setCompoundDrawablePadding(2);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Job clicked " + jobs.get(holder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, JobViewActivity.class);
                intent.putExtra("jobId", jobs.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }
}
