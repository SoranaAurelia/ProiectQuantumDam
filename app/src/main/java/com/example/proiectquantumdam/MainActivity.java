package com.example.proiectquantumdam;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.example.proiectquantumdam.adapter.JobsAdapter;
import com.example.proiectquantumdam.model.QuantumJob;
import com.example.proiectquantumdam.service.OnJobsListReceivedCallback;
import com.example.proiectquantumdam.service.QuantumServiceInterface;
import com.example.proiectquantumdam.utils.NotificationBuilderHelper;
import com.example.proiectquantumdam.utils.PropertyReader;
import com.example.proiectquantumdam.websocket.JobStreamWebSocketListener;
import com.example.proiectquantumdam.websocket.OnMessageReceivedCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectquantumdam.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerViewJobs;
    private JobsAdapter jobsAdapter;
    private NotificationManager notificationManager;

    void requestNotificationPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestNotificationPermission();
        ConfigureNotificationChannel();

        PropertyReader propertyReader = new PropertyReader("config.properties", getApplicationContext());
        QuantumServiceInterface.ConfigureQuantumService(propertyReader.GetProperty("serviceCrn"), propertyReader.GetProperty("apiKey"), propertyReader.GetProperty("apiUrl"));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                QuantumServiceInterface quantumServiceInstance = QuantumServiceInterface.GetInstance();
                if(quantumServiceInstance != null) {
                    quantumServiceInstance.getJobs(new OnJobsListReceivedCallback() {
                        @Override
                        public void onJobsListReceivedCallback(List<QuantumJob> jobs) {
                            generateDataList(jobs);
                            createToast();

                        }
                    });
                }
                StartJobStreamListener(notificationManager, getApplicationContext());
            }
        });
    }

    public static void StartJobStreamListener(NotificationManager notificationManager, Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url("https://us-east.quantum-computing.cloud.ibm.com/stream/jobs")
                .addHeader("Authorization", "apikey IpnsqmFbhYBIYDTY5XHJ-Cu0C5CBkMXuOEbQTkhxeu4s")
                .addHeader("Service-Crn", "crn:v1:bluemix:public:quantum-computing:us-east:a/650ef3eebf2a4dcb991c7c605d5a4c0f:f4710a0e-80e6-4efa-9ae3-41f2cf78164e::")
                .addHeader("accept", "text/event-stream")
                .build();
        JobStreamWebSocketListener listener = new JobStreamWebSocketListener(new OnMessageReceivedCallback() {
            @Override
            public void onJobsListReceivedCallback() {
                notificationManager.notify(0, NotificationBuilderHelper.createNotificationCompatBuilder(context, "NotificationChannelId"));
            }
        });

        WebSocket ws = client.newWebSocket(request, listener);

    }

    private void createToast(){
        Toast.makeText(getApplicationContext(), "Jobs updated", Toast.LENGTH_SHORT).show();
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<QuantumJob> jobList) {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        jobsAdapter = new JobsAdapter(this, jobList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewJobs.setLayoutManager(layoutManager);
        recyclerViewJobs.setAdapter(jobsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void ConfigureNotificationChannel(){

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = notificationManager.getNotificationChannel("NotificationChannelId");
            if(channel == null) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                channel = new NotificationChannel("NotificationChannelId", "Some Description", importance);
                channel.setLightColor(Color.GREEN);
                channel.enableVibration(true);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this.
                notificationManager.createNotificationChannel(channel);
            }
        }

//        notificationManager.notify(0, builder.build());
    }
}