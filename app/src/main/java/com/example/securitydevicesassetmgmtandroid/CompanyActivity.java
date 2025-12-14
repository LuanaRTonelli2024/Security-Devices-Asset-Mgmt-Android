package com.example.securitydevicesassetmgmtandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securitydevicesassetmgmtandroid.Adapters.CompanyAdapter;
import com.example.securitydevicesassetmgmtandroid.Models.Company;
import com.example.securitydevicesassetmgmtandroid.Models.CompanyResponse;
import com.example.securitydevicesassetmgmtandroid.Services.ApiService;
import com.example.securitydevicesassetmgmtandroid.Services.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActivity extends AppCompatActivity {
    private String token;
    private RecyclerView rvCompanies;
    private Button btnAddCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        token = getIntent().getStringExtra("TOKEN");

        Log.d("CompanyActivity", "Received token: " + token);

        btnAddCompany = findViewById(R.id.btn_add_company);
        btnAddCompany.setOnClickListener(v -> {
            Intent intent = new Intent(CompanyActivity.this, AddCompanyActivity.class);
            intent.putExtra("TOKEN", token);
            startActivity(intent);
        });

        rvCompanies = findViewById(R.id.rv_companies);
        rvCompanies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                            Log.d("CompanyActivity", "Fresh token: " + token);
                            loadCompanies();
                        } else {
                            Toast.makeText(this, "Error refreshing token!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    public void loadCompanies() {
        Log.d("CompanyActivity", "Calling getCompanies with token: Bearer " + token);

        RetrofitClient.getInstance()
                .create(ApiService.class)
                .getCompanies("Bearer " + token)
                .enqueue(new Callback<CompanyResponse>() {
                    @Override
                    public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                        Log.d("CompanyActivity", "Response code: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            List<Company> allCompanies = response.body().getCompanies();
                            rvCompanies.setAdapter(new CompanyAdapter(allCompanies, token));
                            Toast.makeText(CompanyActivity.this, "Companies loaded successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CompanyActivity.this, "Error on fetching companies!", Toast.LENGTH_LONG).show();

                            // 👉 Evite usar .string() aqui (pode travar a UI)
                            if (response.errorBody() != null) {
                                Log.e("CompanyActivity", "Error body: " + response.errorBody().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyResponse> call, Throwable t) {
                        Toast.makeText(CompanyActivity.this, "Connection error while fetching companies!", Toast.LENGTH_LONG).show();
                        Log.e("CompanyActivity", "Failure: " + t.getMessage(), t);
                    }
                });
    }



}