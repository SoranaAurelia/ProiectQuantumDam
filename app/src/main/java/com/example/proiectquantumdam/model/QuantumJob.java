package com.example.proiectquantumdam.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class QuantumJob {

    public class Bss{
        public int seconds;
    }

    public class Program{
        public String id;
    }

    public class State{
        public String status;
    }

    public class Usage{
        public int quantum_seconds;
        public int seconds;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("backend")
    private String backend;
    @SerializedName("state")
    private State state;
    @SerializedName("program")
    private Program program;
    @SerializedName("created")
    private Date created;
    @SerializedName("cost")
    private int cost;
    @SerializedName("bss")
    private Bss bss;
    @SerializedName("usage")
    private Usage usage;
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public String getBackend() {
        return backend;
    }

    public State getState() {
        return state;
    }

    public Program getProgram() {
        return program;
    }

    public Date getCreated() {
        return created;
    }

    public int getCost() {
        return cost;
    }

    public Bss getBss() {
        return bss;
    }

    public Usage getUsage() {
        return usage;
    }

    public String getStatus() {
        return status;
    }
}
