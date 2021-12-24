package com.example.myapplication1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Models.CustomerModel;
import com.example.myapplication1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder>{
private ArrayList<CustomerModel> customerList =new ArrayList<>();
private OnCusListener monCusListener;

    @NonNull
    @Override
    public CustomerListAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout,parent,false),monCusListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.CustomerViewHolder holder, int position) {
            holder.customerName.setText(customerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void setList(ArrayList<CustomerModel> customerList,OnCusListener onCusListener){
        this.customerList=customerList;
        this.monCusListener =onCusListener;
        notifyDataSetChanged();
    }
    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView customerName;
        OnCusListener onCusListener;
        public CustomerViewHolder(@NonNull View v,OnCusListener onCusListener) {
            super(v);
            this.onCusListener =onCusListener;
            customerName = v.findViewById(R.id.customerName);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCusListener.onClick(getAdapterPosition());
        }
    }
    public interface OnCusListener{
        void onClick(int pos);
    }
}
