package com.example.securitydevicesassetmgmtandroid.Models;

public class CreateCompanyResponse {

    private String id;

    public CreateCompanyResponse(String id) {
        this.id = id;
    }
    public CreateCompanyResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
