package com.example.proiectquantumdam.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobResultDto {

    public class CircuitMetadata{
    }

    public class Metadata{
        public int shots;
        public CircuitMetadata circuit_metadata;
    }


    @SerializedName("quasi_dists")
    private ArrayList<Map<String, Double>> quasiDists;

    @SerializedName("metadata")
    private ArrayList<Metadata> metadata;

    public ArrayList<Map<String, Double>> getQuasiDists() {
        return quasiDists;
    }

    public ArrayList<Metadata> getMetadata() {
        return metadata;
    }
}
