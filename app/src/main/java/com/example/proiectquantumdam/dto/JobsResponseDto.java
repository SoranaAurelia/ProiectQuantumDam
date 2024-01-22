package com.example.proiectquantumdam.dto;

import com.example.proiectquantumdam.model.QuantumJob;

import java.util.ArrayList;

public class JobsResponseDto {
    public ArrayList<QuantumJob> jobs;
    public int count;
    public int limit;
    public int offset;
}
