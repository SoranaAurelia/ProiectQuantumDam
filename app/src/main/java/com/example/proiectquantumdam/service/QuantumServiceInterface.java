package com.example.proiectquantumdam.service;

import androidx.annotation.NonNull;

import com.example.proiectquantumdam.controller.JobsController;
import com.example.proiectquantumdam.dto.JobResultDto;
import com.example.proiectquantumdam.dto.JobsResponseDto;
import com.example.proiectquantumdam.model.QuantumJob;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuantumServiceInterface {

    public static OkHttpClient getHttpClient(String apiKey, String serviceCrn) {

        String apiKeyUpdated = "apikey " + apiKey;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300,TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("Service-Crn", serviceCrn)
                                .addHeader("Authorization", apiKeyUpdated)
                                .build();
                        return chain.proceed(request);
                    }
                )
                .build();

        return client;
    }
    private static QuantumServiceInterface quantumService;

    private Retrofit retrofit;
    private String serviceCrn;
    private String apiKey;
    private String apiUrl;

    public String getServiceCrn() {
        return serviceCrn;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public QuantumServiceInterface(String serviceCrn, String apiKey, String apiUrl) {
        this.serviceCrn = serviceCrn;
        this.apiKey = apiKey;
        this.apiUrl =  apiUrl;

        this.retrofit =  new Retrofit.Builder()
                .baseUrl(this.apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient(this.apiKey, this.serviceCrn))
                .build();
    }

    public void getJobs(OnJobsListReceivedCallback callback) {
        JobsController jobService = retrofit.create(JobsController.class);
        Call<JobsResponseDto> call = jobService.getJobs();
        call.enqueue(new Callback<JobsResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<JobsResponseDto> call, @NonNull Response<JobsResponseDto> response) {
                if(response.isSuccessful()){
                    if(response.body() != null)
                        callback.onJobsListReceivedCallback(response.body().getJobs());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JobsResponseDto> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getJobResult(String jobId, OnJobResultReceivedCallback callback){
        JobsController jobService = retrofit.create(JobsController.class);
        Call<JobResultDto> call = jobService.getJobResult(jobId);
        call.enqueue(new Callback<JobResultDto>() {
            @Override
            public void onResponse(@NonNull Call<JobResultDto> call, @NonNull Response<JobResultDto> response) {
                if(response.isSuccessful()){
                    if(response.body() != null)
                        callback.onJobResultReceivedCallback(response.body().getQuasiDists());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JobResultDto> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getJobInfo(String jobId, OnJobInfoReceivedCallback callback){
        JobsController jobService = retrofit.create(JobsController.class);
        Call<QuantumJob> call = jobService.getJobInfo(jobId);
        call.enqueue(new Callback<QuantumJob>() {
            @Override
            public void onResponse(@NonNull Call<QuantumJob> call, @NonNull Response<QuantumJob> response) {
                if(response.isSuccessful()){
                    callback.onJobInfoReceivedCallback(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuantumJob> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void ConfigureQuantumService(String serviceCrn, String apiKey, String apiUrl){
        quantumService = new QuantumServiceInterface(serviceCrn, apiKey, apiUrl);
    }

    public static QuantumServiceInterface GetInstance() {
        if(quantumService == null)
            return null;
        return quantumService;
    }
}
