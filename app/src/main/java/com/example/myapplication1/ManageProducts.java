package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication1.Adapter.CustomerListAdapter;
import com.example.myapplication1.Adapter.ProductListAdapter;
import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.databinding.ActivityManageProductSBinding;

public class ManageProducts extends AppCompatActivity {
    ActivityManageProductSBinding binding;
    public static DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageProductSBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        this.setContentView(v);
        setSupportActionBar(binding.ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHandler =new DbHandler(this);

        ProductListAdapter adapter =new ProductListAdapter(this);

        adapter.setList(dbHandler.getAllProducts());
        binding.proRecycler.setAdapter(adapter);
        binding.proRecycler.setLayoutManager(new LinearLayoutManager(this));

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