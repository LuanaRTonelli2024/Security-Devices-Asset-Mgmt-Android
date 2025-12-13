package com.example.securitydevicesassetmgmtandroid.Models;

public class CreateCompanyRequest {
    private String name;

    public CreateCompanyRequest(String name) {
        this.name = name;
    }

    public CreateCompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
