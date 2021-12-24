package com.example.myapplication1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication1.Adapter.CustomerListAdapter;
import com.example.myapplication1.DataBase.DbHandler;

import java.util.concurrent.locks.ReadWriteLock;

public class SelectCustomer extends Fragment implements CustomerListAdapter.OnCusListener {
    DbHandler dbHandler;
    SendCusNameInterFace sendCusNameInterFace;
    public interface SendCusNameInterFace{
        public void getCusName(String name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler =new DbHandler(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_select_customer, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler);
        CustomerListAdapter adapter =new CustomerListAdapter();
        adapter.setList(dbHandler.getAllCustomers(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return v;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            sendCusNameInterFace = (SelectCustomer.SendCusNameInterFace) activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }    @Override
    public void onClick(int pos) {
        String name="";
        //TODO GET NAME FROM ARRAY
        name = dbHandler.getAllCustomers().get(pos).getName();
        sendCusNameInterFace.getCusName(name);
    }
}