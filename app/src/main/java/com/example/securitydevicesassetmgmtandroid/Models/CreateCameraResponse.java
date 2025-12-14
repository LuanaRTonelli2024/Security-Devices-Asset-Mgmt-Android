package com.example.securitydevicesassetmgmtandroid.Models;

public class CreateCameraResponse {
    private String name;
    private String location;
    private String ipAddress;
    private String subnetMask;
    private String defaultGateway;
    private String userName;
    private String password;
    private String companyId;

    public CreateCameraResponse(String name, String location, String ipAddress, String subnetMask, String defaultGateway, String userName, String password, String companyId) {
        this.name = name;
        this.location = location;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
        this.defaultGateway = defaultGateway;
        this.userName = userName;
        this.password = password;
        this.companyId = companyId;
    }

    public CreateCameraResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getDefaultGateway() {
        return defaultGateway;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
