package com.example.securitydevicesassetmgmtandroid;

import android.content.Intent;
import android.os.Bundle;
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
        loadCompanies();
    }

    public void loadCompanies() {
        RetrofitClient.getInstance()
                .create(ApiService.class)
                .getCompanies(token)
                .enqueue(new Callback<CompanyResponse>() {
                    @Override
                    public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Company> allCompanies = response.body().getCompanies();
                            rvCompanies.setAdapter(new CompanyAdapter(allCompanies, token));
                        } else {
                            Toast.makeText(CompanyActivity.this, "Error on fetching companies!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyResponse> call, Throwable t) {
                        Toast.makeText(CompanyActivity.this, "Error on fetching companies!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}