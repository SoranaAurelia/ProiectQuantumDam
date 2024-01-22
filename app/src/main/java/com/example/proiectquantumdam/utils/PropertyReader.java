package com.example.proiectquantumdam.utils;

import android.content.Context;

import java.util.Properties;

public class PropertyReader {

    private Context context;
    private Properties properties;

    public PropertyReader(Context context){
        this.context=context;
        properties = new Properties();
    }

    public Properties GetProperties(String file){
        try{
//            AssetManager assetManager = context.getAssets();
//            InputStream inputStream = assetManager.open(file);
////            InputStream inputStream = new FileInputStream("src/main/assets/config.properties");;
//            properties.load(inputStream);

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

        return properties;
    }
}