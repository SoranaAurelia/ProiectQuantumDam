package com.example.proiectquantumdam.service;

import com.example.proiectquantumdam.model.QuantumJob;

import java.util.List;

public interface OnJobsListReceivedCallback {
    public void onJobsListReceivedCallback(List<QuantumJob> jobs);
}
