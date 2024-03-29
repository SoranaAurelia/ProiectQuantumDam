package com.example.proiectquantumdam.controller;

import android.database.Observable;

import com.example.proiectquantumdam.dto.JobResultDto;
import com.example.proiectquantumdam.dto.JobsResponseDto;
import com.example.proiectquantumdam.model.QuantumJob;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface JobsController {
    @GET("jobs")
    Call<JobsResponseDto> getJobs();


    @GET("jobs/{id}/results")
    Call<JobResultDto> getJobResult(@Path("id") String jobId);


    @GET("jobs/{id}")
    Call<QuantumJob> getJobInfo(@Path("id") String jobId);

    @Headers({
            "Service-CRN: crn:v1:bluemix:public:quantum-computing:us-east:a/650ef3eebf2a4dcb991c7c605d5a4c0f:f4710a0e-80e6-4efa-9ae3-41f2cf78164e::",
            "Authorization: apikey IpnsqmFbhYBIYDTY5XHJ-Cu0C5CBkMXuOEbQTkhxeu4s",
    })
    @GET("jobs")
    Call<Object> getObjectJobs();
}