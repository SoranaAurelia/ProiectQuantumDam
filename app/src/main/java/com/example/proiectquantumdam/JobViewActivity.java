package com.example.proiectquantumdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.proiectquantumdam.dto.JobResultDto;
import com.example.proiectquantumdam.model.QuantumJob;
import com.example.proiectquantumdam.service.OnJobInfoReceivedCallback;
import com.example.proiectquantumdam.service.OnJobResultReceivedCallback;
import com.example.proiectquantumdam.service.QuantumServiceInterface;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Map;

public class JobViewActivity extends AppCompatActivity {

    BarChart outputChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> dataLabels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);

        Intent intent = getIntent();
        String jobId = intent.getStringExtra("jobId");

        TextView tvJobId = findViewById(R.id.v_job_id);
        TextView tvStatus = findViewById(R.id.v_job_status);
        TextView tvBackend = findViewById(R.id.v_job_backend);
        TextView tvDate = findViewById(R.id.v_job_date);
        tvJobId.setText(jobId);

        QuantumServiceInterface quantumInstance = QuantumServiceInterface.GetInstance();
        if(quantumInstance != null){
            quantumInstance.getJobResult(jobId, new OnJobResultReceivedCallback() {
                @Override
                public void onJobResultReceivedCallback(ArrayList<Map<String, Double>> distributions) {
                    consumeDistributions(distributions);
                }
            });

            quantumInstance.getJobInfo(jobId, new OnJobInfoReceivedCallback() {
                @Override
                public void onJobInfoReceivedCallback(QuantumJob quantumJob) {
                    tvStatus.setText(quantumJob.getStatus());
                    tvBackend.setText(quantumJob.getBackend());
                    tvDate.setText(quantumJob.getCreated().toString());

                    if(quantumJob.getStatus().equals("Completed")) {
                        tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.job_completed, 0, 0, 0);
                        tvStatus.setCompoundDrawablePadding(2);
                    }
                    else{
                        tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.status_canceled, 0, 0, 0);
                        tvStatus.setCompoundDrawablePadding(2);
                    }
                }
            });
        }
        barEntriesArrayList = new ArrayList<>();
        dataLabels = new ArrayList<>();


        outputChart = findViewById(R.id.idBarChartResults);

    }

    public void consumeDistributions(ArrayList<Map<String, Double>> distributions){
        int len = 0;
        for(Map.Entry<String, Double> entry: distributions.get(0).entrySet())
        {
            String key=entry.getKey(); // "00" -> 9
            len = key.length();
            int decimal = Integer.parseInt(key, 2);
            Double result = entry.getValue();

            barEntriesArrayList.add(new BarEntry(decimal, result.floatValue()));
        }

        double num_labels = Math.pow(2, len);
        for(int i=0; i<num_labels; i++){
            StringBuilder label = new StringBuilder(Integer.toBinaryString(i));
            int tmp_len = label.length();
            while (tmp_len < len){
                label.insert(0, "0");
                tmp_len++;
            }
            dataLabels.add(label.toString());

            if(!distributions.get(0).containsKey(label.toString()))
                barEntriesArrayList.add(new BarEntry(i, 0));
        }

        updateChart();
    }
    private void updateChart(){
        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Sampler Output");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        outputChart.setData(barData);
        outputChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dataLabels));
        outputChart.getXAxis().setGranularity(1f);
        outputChart.getXAxis().setGranularityEnabled(true);
        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        outputChart.getDescription().setEnabled(false);
        outputChart.invalidate();
    }

}