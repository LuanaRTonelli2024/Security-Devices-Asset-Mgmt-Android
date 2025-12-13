package com.example.securitydevicesassetmgmtandroid.Models;

import java.util.List;

public class CompanyResponse {
    private List<Company> companies;

    public CompanyResponse(List<Company> companies) { this.companies = companies; }

    public CompanyResponse() { }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

}
