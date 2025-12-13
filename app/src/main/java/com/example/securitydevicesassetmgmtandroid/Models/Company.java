package com.example.securitydevicesassetmgmtandroid.Models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Company {
    @SerializedName("_id")
    private String id;
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public Company(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}