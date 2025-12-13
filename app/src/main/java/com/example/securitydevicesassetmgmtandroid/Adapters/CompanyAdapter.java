package com.example.securitydevicesassetmgmtandroid.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securitydevicesassetmgmtandroid.CameraActivity;
import com.example.securitydevicesassetmgmtandroid.Models.Company;
import com.example.securitydevicesassetmgmtandroid.R;
import com.example.securitydevicesassetmgmtandroid.Services.ApiService;
import com.example.securitydevicesassetmgmtandroid.Services.RetrofitClient;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>{

    private List<Company> companies;
    private String token;

    public CompanyAdapter(List<Company> companies, String token){
        this.companies = companies;
        this.token = token;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.name.setText(company.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CameraActivity.class);
            intent.putExtra("TOKEN", token);
            intent.putExtra("COMPANY_ID", company.getId());
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return companies.size();
    }

    static class CompanyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        //Button deleteButton;
        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_company_name);
            //deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }

}
