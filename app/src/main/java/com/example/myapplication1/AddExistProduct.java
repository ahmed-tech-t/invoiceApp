package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.databinding.ActivityAddExistProductBinding;

public class AddExistProduct extends AppCompatActivity {
    String qrcode;
    DbHandler dbHandler;
    ActivityAddExistProductBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exist_product);
        binding = ActivityAddExistProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
        setSupportActionBar(binding.toolBarAddExistPro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbHandler =new DbHandler(this);
        Intent intent = getIntent();
        qrcode = intent.getStringExtra("qrCode");
        binding.proName.setText(dbHandler.getProductName(qrcode));
    }

    public void buSavePro(View view) {
        if(binding.proQuantity.getText().toString().length()==0){
            binding.proQuantity.setError("please enter Quantity");
        }else if(binding.proQuantity.getError()==null){
            int quantity = dbHandler.getProductQuantity(qrcode) + Integer.parseInt(binding.proQuantity.getText().toString());
                dbHandler.setProductQuantity(qrcode,quantity);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}