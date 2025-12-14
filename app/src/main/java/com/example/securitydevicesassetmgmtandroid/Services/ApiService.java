package com.example.securitydevicesassetmgmtandroid.Services;

import com.example.securitydevicesassetmgmtandroid.Models.Camera;
import com.example.securitydevicesassetmgmtandroid.Models.CameraResponse;
import com.example.securitydevicesassetmgmtandroid.Models.Company;
import com.example.securitydevicesassetmgmtandroid.Models.CompanyResponse;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCameraRequest;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCameraResponse;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCompanyResponse;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCompanyRequest;
import com.example.securitydevicesassetmgmtandroid.Models.UpdateCameraRequest;

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
    Call<CompanyResponse> getCompanies(
            @Header("Authorization") String token
    );

    @GET("cameras/company/{company_id}")
    Call<CameraResponse> getCamerasByCompany(
            @Header("Authorization") String token,
            @Path("company_id") String companyId
    );

    @GET("cameras/")
    Call<CameraResponse> getCameras(
            @Header("Authorization") String token
    );


    @POST("companies/")
    Call<CreateCompanyResponse> addCompany(
            @Header("Authorization") String token,
            @Body CreateCompanyRequest company
    );

    @POST("cameras/")
    Call<CreateCameraResponse> addCamera(
            @Header("Authorization") String token,
            @Body CreateCameraRequest camera
    );

    @GET("companies/{id}")
    Call<Company> getCompanyById(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @GET("cameras/{id}")
    Call<Camera> getCameraById(
            @Header("Authorization") String token,
            @Path("id") String id
    );


    @PATCH("companies/{id}")
    Call<Void> updateCompany(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Body CreateCompanyRequest company
    );

    @PATCH("cameras/{id}")
    Call<Camera> updateCamera(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Body UpdateCameraRequest camera
    );

    @DELETE("companies/{id}")
    Call<Void> deleteCompany(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @DELETE("cameras/{id}")
    Call<Void> deleteCamera(
            @Header("Authorization") String token,
            @Path("id") String id
    );
}
