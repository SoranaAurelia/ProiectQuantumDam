package com.example.proiectquantumdam.model;

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

    public String id;
    public String backend;
    public State state;
    public Program program;
    public Date created;
    public int cost;
    public Bss bss;
    public Usage usage;
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
