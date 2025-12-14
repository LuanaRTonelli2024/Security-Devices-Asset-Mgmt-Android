package com.example.securitydevicesassetmgmtandroid.Models;

public class UpdateCameraRequest {
    private String name;
    private String location;
    private String ipAddress;
    private String subnetMask;
    private String defaultGateway;
    private String userName;
    private String password;

    public UpdateCameraRequest(String name, String location, String ipAddress,
                               String subnetMask, String defaultGateway,
                               String userName, String password) {
        this.name = name;
        this.location = location;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
        this.defaultGateway = defaultGateway;
        this.userName = userName;
        this.password = password;
    }

}
