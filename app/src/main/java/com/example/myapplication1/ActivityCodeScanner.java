package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.databinding.ActivityCodeScannerBinding;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityCodeScanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    int id;
    boolean flag = false;
    DbHandler dbHandler;
   ActivityCodeScannerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCodeScannerBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        this.setContentView(v);
        dbHandler = new DbHandler(this);
        scannerView = findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(this,scannerView);
        codeScanner();

    }
    private void codeScanner(){
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i =0; i<dbHandler.getAllQrCode().size();i++){
                            if(dbHandler.getAllQrCode().get(i).getCode().equals(result.getText())){
                               Intent intent = new Intent(ActivityCodeScanner.this, AddExistProduct.class);
                                intent.putExtra("qrCode",result.getText());
                                startActivity(intent);
                                }
                        }
                        if(!flag){
                            binding.scannerMassage.setText("UnKnown Product");
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}