package com.example.securitydevicesassetmgmtandroid.Services;

import com.example.securitydevicesassetmgmtandroid.Models.Company;
import com.example.securitydevicesassetmgmtandroid.Models.CompanyResponse;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCompanyResponse;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCompanyRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("companies/")
    Call<CompanyResponse> getCompanies(@Header("Authorization") String token);

    @POST("companies/")
    Call<CreateCompanyResponse> addCompany(
            @Header("Authorization") String token,
            @Body CreateCompanyRequest company
    );

    @GET("companies/{id}")
    Call<Company> getCompanyById(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @PATCH("companies/{id}")
    Call<Void> updateCompany(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Body CreateCompanyRequest company
    );

    @DELETE("companies/{id}")
    Call<Void> deleteCompany(
            @Header("Authorization") String token,
            @Path("id") String id
    );

}
