package com.example.securitydevicesassetmgmtandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securitydevicesassetmgmtandroid.Models.CreateCameraRequest;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCameraResponse;
import com.example.securitydevicesassetmgmtandroid.Services.ApiService;
import com.example.securitydevicesassetmgmtandroid.Services.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCameraActivity extends AppCompatActivity {
    private String token, companyId;
    private EditText etCameraName, etCameraLocation, etCameraIpAddress, etCameraSubnetMask, etCameraDefaultGateway, etCameraUserName, etCameraPassword;
    private Button btnAddNewCamera;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        token = getIntent().getStringExtra("TOKEN");
        companyId = getIntent().getStringExtra("COMPANY_ID");

        etCameraName = findViewById(R.id.et_add_camera_name);
        etCameraLocation = findViewById(R.id.et_add_camera_location);
        etCameraIpAddress = findViewById(R.id.et_add_camera_ipAdress);
        etCameraSubnetMask = findViewById(R.id.et_add_camera_subnetMask);
        etCameraDefaultGateway = findViewById(R.id.et_add_camera_defaultGateway);
        etCameraUserName = findViewById(R.id.et_add_camera_userName);
        etCameraPassword = findViewById(R.id.et_add_camera_password);
        btnAddNewCamera = findViewById(R.id.btn_add_new_camera);

        btnAddNewCamera.setOnClickListener(v -> {
            refreshTokenAndAddCamera();
        });

    }

    private void refreshTokenAndAddCamera() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken(); // token atualizado
                            Log.d("AddCameraActivity", "Fresh Token: " + token + " | CompanyId: " + companyId);
                            addCamera(); // só chama a API depois de renovar o token
                        } else {
                            Toast.makeText(AddCameraActivity.this, "Erro ao renovar token!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void addCamera() {
        String cameraName = etCameraName.getText().toString();
        String cameraLocation = etCameraLocation.getText().toString();
        String cameraIpAddress = etCameraIpAddress.getText().toString();
        String cameraSubnetMask = etCameraSubnetMask.getText().toString();
        String cameraDefaultGateway = etCameraDefaultGateway.getText().toString();
        String cameraUserName = etCameraUserName.getText().toString();
        String cameraPassword = etCameraPassword.getText().toString();

        CreateCameraRequest camera = new CreateCameraRequest(
                cameraName,
                cameraLocation,
                cameraIpAddress,
                cameraSubnetMask,
                cameraDefaultGateway,
                cameraUserName,
                cameraPassword,
                companyId
        );

        RetrofitClient.getInstance().create(ApiService.class)
                .addCamera("Bearer " + token, camera)
                .enqueue(new Callback<CreateCameraResponse>() {
                    @Override
                    public void onResponse(Call<CreateCameraResponse> call, Response<CreateCameraResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddCameraActivity.this, "Camera created with success!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AddCameraActivity.this, "Error on creating camera!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateCameraResponse> call, Throwable t) {
                        Toast.makeText(AddCameraActivity.this, "Error on Creating camera!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}