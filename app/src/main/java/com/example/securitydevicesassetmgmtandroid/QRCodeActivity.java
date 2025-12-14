package com.example.securitydevicesassetmgmtandroid;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeActivity extends AppCompatActivity {
    private ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrcode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        qrCodeImage = findViewById(R.id.iv_qrCode);

        String cameraId = getIntent().getStringExtra("CAMERA_ID");

        Bitmap qrBitmap = generateQRCode(cameraId);
        qrCodeImage.setImageBitmap(qrBitmap);
    }

    private Bitmap generateQRCode(String data) {
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            return encoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 600, 600);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}