package com.example.securitydevicesassetmgmtandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securitydevicesassetmgmtandroid.CameraDetailActivity;
import com.example.securitydevicesassetmgmtandroid.Models.Camera;
import com.example.securitydevicesassetmgmtandroid.R;

import java.util.List;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.CameraViewHolder> {
    private List<Camera> cameras;
    private String token;
    private Context context;

    public CameraAdapter(List<Camera> cameras, String token, Context context){
        this.cameras = cameras;
        this.token = token;
        this.context = context;
    }

    @NonNull
    @Override
    public CameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false);
        return new CameraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CameraViewHolder holder, int position) {
        Camera camera = cameras.get(position);
        holder.tvCameraName.setText(camera.getName());
        holder.tvCameraLocation.setText(camera.getLocation());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CameraDetailActivity.class);
            intent.putExtra("CAMERA_ID", camera.getId());
            intent.putExtra("TOKEN", token);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cameras.size();
    }

    static class CameraViewHolder extends RecyclerView.ViewHolder{
        TextView tvCameraName, tvCameraLocation;

        public CameraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCameraName = itemView.findViewById(R.id.tv_camera_name);
            tvCameraLocation = itemView.findViewById(R.id.tv_camera_location);
        }
    }
}
