package com.example.securitydevicesassetmgmtandroid.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;

    private static final String PORT = "5000";

    private static final String EMULATOR_BASE_URL = "http://10.0.2.2:" + PORT + "/";

    private static final String DEVICE_BASE_URL = "http://10.0.0.97:" + PORT + "/";

    private static final String BASE_URL = "https://special-meme-x5g5p995xjrf6vv-5000.app.github.dev/";


    //private static final String BASE_URL = DEVICE_BASE_URL;

    private RetrofitClient() {}

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

}
