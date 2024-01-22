package com.example.proiectquantumdam.dto;

import com.example.proiectquantumdam.model.QuantumJob;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JobsResponseDto {
    @SerializedName("jobs")
    private ArrayList<QuantumJob> jobs;
    @SerializedName("count")
    private int count;

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    public ArrayList<QuantumJob> getJobs() {
        return jobs;
    }
}
