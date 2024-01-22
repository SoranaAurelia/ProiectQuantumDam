package com.example.proiectquantumdam;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.example.proiectquantumdam.adapter.JobsAdapter;
import com.example.proiectquantumdam.model.QuantumJob;
import com.example.proiectquantumdam.service.OnJobsListReceivedCallback;
import com.example.proiectquantumdam.service.QuantumServiceInterface;
import com.example.proiectquantumdam.utils.PropertyReader;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

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

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerViewJobs;
    private JobsAdapter jobsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    quantumServiceInstance.GetJobs(new OnJobsListReceivedCallback() {
                        @Override
                        public void onJobsListReceivedCallback(List<QuantumJob> jobs) {
                            generateDataList(jobs);
                            createToast();
                        }
                    });
                }
            }
        });
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
}