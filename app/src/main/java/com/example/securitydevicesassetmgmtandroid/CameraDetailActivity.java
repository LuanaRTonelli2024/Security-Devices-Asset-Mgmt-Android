package com.example.securitydevicesassetmgmtandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securitydevicesassetmgmtandroid.Models.Camera;
import com.example.securitydevicesassetmgmtandroid.Models.UpdateCameraRequest;
import com.example.securitydevicesassetmgmtandroid.Services.ApiService;
import com.example.securitydevicesassetmgmtandroid.Services.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraDetailActivity extends AppCompatActivity {
    private EditText etCameraName, etCameraLocation, etCameraIPAddress, etCameraSubnetMask, etCameraDefaultGateway, etCameraUserName, etCameraPassword;
    private TextView tvCameraName, tvCameraLocation, tvCameraIPAddress, tvCameraSubnetMask, tvCameraDefaultGateway, tvCameraUserName, tvCameraPassword;
    private Button btnDelete, btnQRCode, btnEdit, btnSave;
    private String token, cameraId;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        token = getIntent().getStringExtra("TOKEN");
        cameraId = getIntent().getStringExtra("CAMERA_ID");

        etCameraName = findViewById(R.id.et_edit_camera_name);
        etCameraLocation = findViewById(R.id.et_edit_camera_location);
        etCameraIPAddress = findViewById(R.id.et_edit_camera_ipaddress);
        etCameraSubnetMask = findViewById(R.id.et_edit_camera_subnetmask);
        etCameraDefaultGateway = findViewById(R.id.et_edit_camera_defaultgateway);
        etCameraUserName = findViewById(R.id.et_edit_camera_username);
        etCameraPassword = findViewById(R.id.et_edit_camera_password);

        tvCameraName = findViewById(R.id.tv_vis_camera_name);
        tvCameraLocation = findViewById(R.id.tv_vis_camera_location);
        tvCameraIPAddress = findViewById(R.id.tv_vis_camera_ipaddress);
        tvCameraSubnetMask = findViewById(R.id.tv_vis_camera_subnetmask);
        tvCameraDefaultGateway = findViewById(R.id.tv_vis_camera_defaultgateway);
        tvCameraUserName = findViewById(R.id.tv_vis_camera_username);
        tvCameraPassword = findViewById(R.id.tv_vis_camera_password);

        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        btnQRCode = findViewById(R.id.btn_qrCode);
        btnEdit = findViewById(R.id.btn_edit);

        loadCameraDetails();

        btnEdit.setOnClickListener(v -> switchToEditMode());

        btnSave.setOnClickListener(v -> refreshTokenAndUpdateCamera());

        btnDelete.setOnClickListener(v -> refreshTokenAndDeleteCamera());

        btnQRCode.setOnClickListener(v -> {
            Intent intent = new Intent(CameraDetailActivity.this, QRCodeActivity.class);
            intent.putExtra("CAMERA_ID", cameraId);
            startActivity(intent);
        });
    }

    private void loadCameraDetails() {
        RetrofitClient.getInstance()
                .create(ApiService.class)
                .getCameraById("Bearer " + token, cameraId)
                .enqueue(new Callback<Camera>() {
                    @Override
                    public void onResponse(Call<Camera> call, Response<Camera> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            camera = response.body();
                            tvCameraName.setText(camera.getName());
                            tvCameraLocation.setText(camera.getLocation());
                            tvCameraIPAddress.setText(camera.getIpAddress());
                            tvCameraSubnetMask.setText(camera.getSubnetMask());
                            tvCameraDefaultGateway.setText(camera.getDefaultGateway());
                            tvCameraUserName.setText(camera.getUserName());
                            tvCameraPassword.setText(camera.getPassword());
                        } else {
                            Toast.makeText(CameraDetailActivity.this, "Error loading camera details!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Camera> call, Throwable t) {
                        Toast.makeText(CameraDetailActivity.this, "Connection error!", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void switchToEditMode() {
        tvCameraName.setVisibility(View.GONE);
        tvCameraLocation.setVisibility(View.GONE);
        tvCameraIPAddress.setVisibility(View.GONE);
        tvCameraSubnetMask.setVisibility(View.GONE);
        tvCameraDefaultGateway.setVisibility(View.GONE);
        tvCameraUserName.setVisibility(View.GONE);
        tvCameraPassword.setVisibility(View.GONE);

        etCameraName.setVisibility(View.VISIBLE);
        etCameraLocation.setVisibility(View.VISIBLE);
        etCameraIPAddress.setVisibility(View.VISIBLE);
        etCameraSubnetMask.setVisibility(View.VISIBLE);
        etCameraDefaultGateway.setVisibility(View.VISIBLE);
        etCameraUserName.setVisibility(View.VISIBLE);
        etCameraPassword.setVisibility(View.VISIBLE);

        etCameraName.setText(camera.getName());
        etCameraLocation.setText(camera.getLocation());
        etCameraIPAddress.setText(camera.getIpAddress());
        etCameraSubnetMask.setText(camera.getSubnetMask());
        etCameraDefaultGateway.setText(camera.getDefaultGateway());
        etCameraUserName.setText(camera.getUserName());
        etCameraPassword.setText(camera.getPassword());


        btnSave.setVisibility(View.VISIBLE);
    }

    private void refreshTokenAndUpdateCamera() {
        FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        token = task.getResult().getToken();
                        updateCamera();
                    } else {
                        Toast.makeText(CameraDetailActivity.this, "Error refreshing authentication token!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateCamera() {
        UpdateCameraRequest request = new UpdateCameraRequest(
                etCameraName.getText().toString(),
                etCameraLocation.getText().toString(),
                etCameraIPAddress.getText().toString(),
                etCameraSubnetMask.getText().toString(),
                etCameraDefaultGateway.getText().toString(),
                etCameraUserName.getText().toString(),
                etCameraPassword.getText().toString()

        );

        RetrofitClient.getInstance()
                .create(ApiService.class)
                .updateCamera("Bearer " + token, cameraId, request)
                .enqueue(new Callback<Camera>() {
                    @Override
                    public void onResponse(Call<Camera> call, Response<Camera> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            camera = response.body();

                            tvCameraName.setText(camera.getName());
                            tvCameraLocation.setText(camera.getLocation());
                            tvCameraIPAddress.setText(camera.getIpAddress());
                            tvCameraSubnetMask.setText(camera.getSubnetMask());
                            tvCameraDefaultGateway.setText(camera.getDefaultGateway());
                            tvCameraUserName.setText(camera.getUserName());
                            tvCameraPassword.setText(camera.getPassword());


                            Toast.makeText(CameraDetailActivity.this, "Camera updated successfully!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CameraDetailActivity.this, "Error updating camera!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Camera> call, Throwable t) {
                        Toast.makeText(CameraDetailActivity.this, "Connection error!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void refreshTokenAndDeleteCamera() {
        FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        token = task.getResult().getToken();
                        deleteCamera();
                    } else {
                        Toast.makeText(CameraDetailActivity.this, "Error refreshing authentication token!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void deleteCamera() {
        RetrofitClient.getInstance()
                .create(ApiService.class)
                .deleteCamera("Bearer " + token, cameraId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CameraDetailActivity.this, "Camera deleted successfully!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CameraDetailActivity.this, "Error deleting camera!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CameraDetailActivity.this, "Connection error!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}