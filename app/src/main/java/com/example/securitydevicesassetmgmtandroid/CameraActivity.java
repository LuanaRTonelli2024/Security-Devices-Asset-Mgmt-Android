package com.example.securitydevicesassetmgmtandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securitydevicesassetmgmtandroid.Adapters.CameraAdapter;
import com.example.securitydevicesassetmgmtandroid.Models.Camera;
import com.example.securitydevicesassetmgmtandroid.Models.CameraResponse;
import com.example.securitydevicesassetmgmtandroid.Services.ApiService;
import com.example.securitydevicesassetmgmtandroid.Services.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {

    private String token, companyId;
    private Button btnAddCamera, btnShowAll;
    private RecyclerView rvCameras;
    private ImageButton btnSearchQRCode;
    private SearchView svSearchCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        token = getIntent().getStringExtra("TOKEN");
        companyId = getIntent().getStringExtra("COMPANY_ID");

        Log.d("CameraActivity", "Token: " + token + " | CompanyId: " + companyId);


        rvCameras = findViewById(R.id.rv_cameras);
        rvCameras.setLayoutManager(new LinearLayoutManager(this));

        btnAddCamera = findViewById(R.id.btn_add_camera);
        btnSearchQRCode = findViewById(R.id.btn_search);
        btnShowAll = findViewById(R.id.btn_show_all);
        svSearchCamera = findViewById(R.id.sv_search_camera);

        btnAddCamera.setOnClickListener(v -> {
            Intent intent = new Intent(CameraActivity.this, AddCameraActivity.class);
            intent.putExtra("TOKEN", token);
            intent.putExtra("COMPANY_ID", companyId);
            startActivity(intent);
        });

        btnSearchQRCode.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Scan a camera QR code");
            options.setCameraId(0);
            options.setBeepEnabled(true);
            options.setBarcodeImageEnabled(true);
            qrCodeLauncher.launch(options);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTokenAndLoadCameras();
    }

    private void refreshTokenAndLoadCameras() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                            Log.d("CameraActivity", "Fresh Token: " + token + " | CompanyId: " + companyId);
                            loadCameras();
                        } else {
                            Toast.makeText(CameraActivity.this, "Error refreshing authentication token!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String scannedCameraId = result.getContents();
                    Log.d("CameraActivity", "QR Code scanned: " + scannedCameraId);

                    RetrofitClient.getInstance()
                            .create(ApiService.class)
                            .getCameraById("Bearer " + token, scannedCameraId)
                            .enqueue(new Callback<Camera>() {
                                @Override
                                public void onResponse(Call<Camera> call, Response<Camera> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        List<Camera> resultList = new ArrayList<>();
                                        resultList.add(response.body());
                                        rvCameras.setAdapter(new CameraAdapter(resultList, token, CameraActivity.this));
                                        Toast.makeText(CameraActivity.this, "Camera found successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CameraActivity.this, "Camera not found!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Camera> call, Throwable t) {
                                    Toast.makeText(CameraActivity.this, "Connection error while fetching camera!", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(CameraActivity.this, "No QR code detected!", Toast.LENGTH_SHORT).show();
                }
            });

    public void loadCameras() {
        RetrofitClient.getInstance()
                .create(ApiService.class)
                .getCamerasByCompany("Bearer " + token, companyId)
                .enqueue(new Callback<CameraResponse>() {
                    @Override
                    public void onResponse(Call<CameraResponse> call, Response<CameraResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Camera> allCameras = response.body().getCameras();
                            rvCameras.setAdapter(new CameraAdapter(allCameras, token, CameraActivity.this));
                        } else {
                            Toast.makeText(CameraActivity.this, "Error on fetching cameras!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CameraResponse> call, Throwable t) {
                        Toast.makeText(CameraActivity.this, "Error on fetching cameras!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}