package com.example.proiectquantumdam.service;

import android.util.Log;

import com.example.proiectquantumdam.controller.JobsController;
import com.example.proiectquantumdam.dto.JobsResponseDto;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuantumServiceInterface {

    public static OkHttpClient getHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        //TODO : remove logging interceptors as it is to be used for development purpose
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300,TimeUnit.SECONDS).
                addInterceptor(logging).
                build();

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
                .baseUrl("https://us-east.quantum-computing.cloud.ibm.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient()) //TODO: remove this
                .build();
    }

    public void GetJobs() {
        JobsController jobService = retrofit.create(JobsController.class);
        Call<JobsResponseDto> call = jobService.getJobs();
        call.enqueue(new Callback<JobsResponseDto>() {
            @Override
            public void onResponse(Call<JobsResponseDto> call, Response<JobsResponseDto> response) {
                //String res = response.body().toString();
                Log.i("COUNT", response.body().count+"");
//                Log.i("RES", res);
//                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
            }

            @Override
            public void onFailure(Call<JobsResponseDto> call, Throwable t) {
                t.printStackTrace();
//                Log.e("RES", "ERROR");
//                System.out.println(t.toString());
            }
        });
    }

    public static void ConfigureQuantumService(String serviceCrn, String apiKey, String apiUrl){
//        quantumService = new QuantumService(serviceCrn, apiKey, apiUrl);
        quantumService = new QuantumServiceInterface("crn:v1:bluemix:public:quantum-computing:us-east:a/650ef3eebf2a4dcb991c7c605d5a4c0f:f4710a0e-80e6-4efa-9ae3-41f2cf78164e::",
                "IpnsqmFbhYBIYDTY5XHJ-Cu0C5CBkMXuOEbQTkhxeu4s", "https://us-east.quantum-computing.cloud.ibm.com");

    }

    public static QuantumServiceInterface GetInstance() {
        if(quantumService == null)
            return null;
        return quantumService;
    }
}
