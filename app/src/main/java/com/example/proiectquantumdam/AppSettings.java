package com.example.proiectquantumdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proiectquantumdam.service.QuantumServiceInterface;
import com.example.proiectquantumdam.utils.PropertyReader;

public class AppSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);


        Button save = findViewById(R.id.buttonSave);
        EditText etServiceCrn = findViewById(R.id.etServiceCrn);
        EditText etApiKey = findViewById(R.id.etApiKey);
        PropertyReader propertyReader = new PropertyReader("config.properties", getApplicationContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceCrn = String.valueOf(etServiceCrn.getText());
                String apiKey = String.valueOf(etApiKey.getText());

                QuantumServiceInterface.ConfigureQuantumService(serviceCrn, apiKey, propertyReader.GetProperty("apiUrl"));

                finish();
            }
        });
    }
}