package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.databinding.ActivityAddCustomerBinding;
import com.example.myapplication1.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    DbHandler dbHandler;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        this.setContentView(v);
        dbHandler =new DbHandler(this);

        binding.buMainAddExistProduct.setOnClickListener(this);
        binding.buMainAddNewBIll.setOnClickListener(this);
        binding.buMainAddNewCustomer.setOnClickListener(this);
        binding.buMainAddNewProduct.setOnClickListener(this);
        binding.buMainManageProducts.setOnClickListener(this);
        binding.buMainGetAllQrCode.setOnClickListener(this);
        toolbar =findViewById(R.id.ToolBarMainActivity);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(this, NewBill.class);
            startActivity(intent);
        }
        return;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id) {
            case R.id.buMainAddNewBIll:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

                } else {
                    intent = new Intent(this, NewBill.class);
                    startActivity(intent);
                }
                break;
            case R.id.buMainAddNewProduct:
                intent = new Intent(this, AddProduct.class);
                startActivity(intent);
                break;
            case R.id.buMainAddNewCustomer:
                intent = new Intent(this, AddCustomer.class);
                startActivity(intent);
                break;
            case R.id.buMainManageProducts:
                intent = new Intent(this, ManageProducts.class);
                startActivity(intent);
                break;
            case R.id.buMainAddExistProduct:
                intent = new Intent(this, ScanExistProduct.class);
                startActivity(intent);
                break;
            case R.id.buMainGetAllQrCode:
                intent = new Intent(this, GenerateQrCode.class);
                startActivity(intent);
        }
    }
}
