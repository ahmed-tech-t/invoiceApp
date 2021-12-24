package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.AddProduct;
import com.example.myapplication1.Bill;
import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.ManageProducts;
import com.example.myapplication1.Models.ProductModel;
import com.example.myapplication1.NewBill;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    Context context;
    public ProductListAdapter(Context context){
        this.context=context;
    }
    private ArrayList<ProductModel> productsList =new ArrayList<>();
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.proName.setText(productsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void setList(ArrayList<ProductModel> productsList){
        this.productsList = productsList;
        notifyDataSetChanged();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView proName;
        ImageButton imageButton;
        public ProductViewHolder(@NonNull View v) {
            super(v);
            proName =v.findViewById(R.id.LProName);
            imageButton=v.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            showPopupMenu(view);
        }
        public void showPopupMenu(View v){
            PopupMenu popupMenu =new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.editMenu:
                    Intent intent = new Intent(context, AddProduct.class);
                    intent.putExtra("product",productsList.get(getAdapterPosition()));
                    context.startActivity(intent);
                    return true;
                case R.id.deleteMenu:
                    String code =String.valueOf(ManageProducts.dbHandler.getAllProducts().get(getAdapterPosition()).getCode());
                    ManageProducts.dbHandler.deleteProduct(code);
                    productsList.remove(getAdapterPosition());
                    notifyItemRemoved(this.getLayoutPosition());
                    return true;
                default:
                    return false;
            }

        }
    }
}
