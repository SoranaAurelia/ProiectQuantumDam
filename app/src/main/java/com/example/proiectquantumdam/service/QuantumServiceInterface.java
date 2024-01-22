package com.example.proiectquantumdam.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.example.proiectquantumdam.controller.JobsController;
import com.example.proiectquantumdam.dto.JobsResponseDto;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
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
            public void onResponse( Call<JobsResponseDto> call, Response<JobsResponseDto> response) {
                if(response.isSuccessful()){
                    callback.onJobsListReceivedCallback(response.body().jobs);
                }
            }

            @Override
            public void onFailure(Call<JobsResponseDto> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getJobUpdateStream(){
        JobsController jobService = retrofit.create(JobsController.class);
        Call<ResponseBody> call = jobService.getJobUpdates();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d("I", "connected to server");

                    new AsyncTask<Void, Void, Void>() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        protected Void doInBackground(Void... voids) {
                            Log.d("I", "file download was a success? " + response.body());
                            return null;
                        }
                    }.execute();

                }
                else{
                    Log.e("E", "Error");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getJobResult(String jobId){
        JobsController jobService = retrofit.create(JobsController.class);
        Call<Object> call = jobService.getJobResult(jobId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse( Call call, Response response) {
                if(response.isSuccessful()){
                    Log.e("RES", "Result retrieved");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void subscribeToJobUpdates(){

    }

    public void GetObjectJobs(OnJobsListReceivedCallback callback) {
        JobsController jobService = retrofit.create(JobsController.class);
        Call call = jobService.getObjectJobs();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                String res = response.body().toString();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
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
