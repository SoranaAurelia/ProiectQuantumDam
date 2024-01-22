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
    public String id;

    @SerializedName("backend")
    public String backend;
    @SerializedName("state")
    public State state;
    @SerializedName("program")
    public Program program;
    @SerializedName("created")
    public Date created;
    @SerializedName("cost")
    public int cost;
    @SerializedName("bss")
    public Bss bss;
    @SerializedName("usage")
    public Usage usage;
    @SerializedName("status")
    public String status;

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
