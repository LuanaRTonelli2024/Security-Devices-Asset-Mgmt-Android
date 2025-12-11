package com.example.securitydevicesassetmgmtandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securitydevicesassetmgmtandroid.Services.AuthFirebase;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserId, etEmail, etPassword;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUserId = findViewById(R.id.et_user_id);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Please fill all fields!", Toast.LENGTH_LONG).show();
                return;
            }

            AuthFirebase fAuth = new AuthFirebase();
            fAuth.signInUser(email, password, LoginActivity.this, result -> {
                if(result){
                    String userId = fAuth.getCurrentUser().getUid();
                    Intent intent = new Intent(LoginActivity.this, CompanyActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                }
            });
        });

        btnSignUp.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Please enter email and password.", Toast.LENGTH_LONG).show();
                return;
            }

            AuthFirebase fAuth = new AuthFirebase();
            fAuth.signUpUser(email, password, LoginActivity.this);
        });
    }
}