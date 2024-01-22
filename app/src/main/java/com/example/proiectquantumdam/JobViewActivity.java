package com.example.proiectquantumdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.proiectquantumdam.service.QuantumServiceInterface;

public class JobViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);

        Intent intent = getIntent();
        String jobId = intent.getStringExtra("jobId");

        TextView textView = findViewById(R.id.v_job_id);
        textView.setText(jobId);

        QuantumServiceInterface quantumInstance = QuantumServiceInterface.GetInstance();
        if(quantumInstance != null){
            quantumInstance.getJobResult(jobId);
        }
    }
}