package com.example.proiectquantumdam.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private Context context;
    private Properties properties;

    public PropertyReader(String fileName, Context context){
        this.context=context;
        try {
            this.properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            this.properties.load(inputStream);
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String GetProperty(String key){
        return this.properties.getProperty(key);
    }
}