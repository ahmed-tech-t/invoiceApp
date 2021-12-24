package com.example.myapplication1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import com.example.myapplication1.databinding.ActivityNewBillBinding;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
