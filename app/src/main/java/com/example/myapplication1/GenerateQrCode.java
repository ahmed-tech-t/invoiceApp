package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.Models.QrCodeModel;
import com.example.myapplication1.databinding.ActivityGenerateQrCodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class GenerateQrCode extends AppCompatActivity {

    ArrayList<Bitmap> qrCodes;
    ActivityGenerateQrCodeBinding binding;
    DbHandler dbHandler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenerateQrCodeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
        setSupportActionBar(binding.toolBar);
        dbHandler =new DbHandler(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        qrCodes = new ArrayList<>();

        //TODO productCode = getAllProductCode();

        for (int i =0;i<dbHandler.getAllQrCode().size();i++){
            final View qrcodeView = getLayoutInflater().inflate(R.layout.qrcodelayout, null, false);
            binding.qrCodeList.addView(qrcodeView);
            TextView tvName =qrcodeView.findViewById(R.id.productName);
            ImageView imQrCode=qrcodeView.findViewById(R.id.qrCodeImage);
            tvName.setText(dbHandler.getAllQrCode().get(i).getProductName());
            imQrCode.setImageBitmap(generateQrCode(dbHandler.getAllQrCode().get(i).getCode()));
        }
    }

    private Bitmap generateQrCode(String text){
        MultiFormatWriter writer =new MultiFormatWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(text , BarcodeFormat.QR_CODE,350,350);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BarcodeEncoder encoder =new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            //InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE );
        return bitmap;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}