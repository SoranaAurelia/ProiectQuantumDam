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
    public ArrayList<Map<String, Double>> quasiDists;

    @SerializedName("metadata")
    public ArrayList<Metadata> metadata;

}
