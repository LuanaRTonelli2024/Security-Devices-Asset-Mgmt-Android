package com.example.securitydevicesassetmgmtandroid.Models;

import java.util.List;

public class CameraResponse {
    private List<Camera> cameras;

    public CameraResponse(List<Camera> cameras) { this.cameras = cameras; }

    public CameraResponse() { }

    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }

}
