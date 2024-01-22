package com.example.proiectquantumdam.service;

import com.example.proiectquantumdam.dto.JobResultDto;
import com.example.proiectquantumdam.model.QuantumJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OnJobResultReceivedCallback {
    public void onJobResultReceivedCallback(ArrayList<Map<String, Double>> distributions);

}
