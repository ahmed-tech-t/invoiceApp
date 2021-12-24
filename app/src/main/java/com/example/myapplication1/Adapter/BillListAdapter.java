package com.example.myapplication1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.MainActivity;
import com.example.myapplication1.Models.BillModel;
import com.example.myapplication1.Models.Bill_Model;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillViewHolder> {

    private ArrayList<Bill_Model> billList = new ArrayList<>();

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        holder.cusName.setText("client :  "+billList.get(position).getCustomerName());
        holder.proName.setText("product :  "+billList.get(position).getProductName());
        holder.proPrice.setText(billList.get(position).getProductPrice());

    }
    public void setList(ArrayList<Bill_Model> billList, MainActivity mainActivity){
        this.billList = billList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        TextView cusName;
        TextView proName;
        TextView proPrice;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            cusName =itemView.findViewById(R.id.billCustomerName);
            proName =itemView.findViewById(R.id.billProductName);
            proPrice =itemView.findViewById(R.id.billProductPrice);
        }
    }
}
