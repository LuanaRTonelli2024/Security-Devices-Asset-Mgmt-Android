package com.example.securitydevicesassetmgmtandroid;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securitydevicesassetmgmtandroid.Models.CreateCompanyRequest;
import com.example.securitydevicesassetmgmtandroid.Models.CreateCompanyResponse;
import com.example.securitydevicesassetmgmtandroid.Services.ApiService;
import com.example.securitydevicesassetmgmtandroid.Services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCompanyActivity extends AppCompatActivity {

    private Button btnCreateCompany;
    private EditText etCompanyName;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_company);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCreateCompany = findViewById(R.id.btn_add_new_company);
        etCompanyName = findViewById(R.id.et_add_company_name);

        token = getIntent().getStringExtra("TOKEN");

        btnCreateCompany.setOnClickListener(v -> {
            String name = etCompanyName.getText().toString();

            CreateCompanyRequest company = new CreateCompanyRequest(name);

            RetrofitClient.getInstance().create(ApiService.class)
                    .addCompany("Bearer " + token, company)
                    .enqueue(new Callback<CreateCompanyResponse>() {
                        @Override
                        public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(AddCompanyActivity.this, "Company created with success!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                Toast.makeText(AddCompanyActivity.this, "Error on creating company!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                            Toast.makeText(AddCompanyActivity.this, "Error on Creating company!", Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}