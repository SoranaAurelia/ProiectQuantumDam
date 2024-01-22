package com.example.proiectquantumdam.controller;

import com.example.proiectquantumdam.dto.JobsResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface JobsController {
    @Headers({
            "Service-CRN: crn:v1:bluemix:public:quantum-computing:us-east:a/650ef3eebf2a4dcb991c7c605d5a4c0f:f4710a0e-80e6-4efa-9ae3-41f2cf78164e::",
            "Authorization: apikey IpnsqmFbhYBIYDTY5XHJ-Cu0C5CBkMXuOEbQTkhxeu4s",
    })
    @GET("/jobs")
    Call<JobsResponseDto> getJobs();


    @Headers({
            "Service-CRN: crn:v1:bluemix:public:quantum-computing:us-east:a/650ef3eebf2a4dcb991c7c605d5a4c0f:f4710a0e-80e6-4efa-9ae3-41f2cf78164e::",
            "Authorization: apikey IpnsqmFbhYBIYDTY5XHJ-Cu0C5CBkMXuOEbQTkhxeu4s",
    })
    @GET("jobs")
    Call<Object> getObjectJobs();
}