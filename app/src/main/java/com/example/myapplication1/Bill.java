package com.example.myapplication1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.Models.BillModel;
import com.example.myapplication1.Models.CustomerModel;
import com.example.myapplication1.Models.ProductModel;
import com.example.myapplication1.databinding.ActivityBillBinding;

public class Bill extends AppCompatActivity implements View.OnFocusChangeListener , View.OnClickListener {

    //Main Array

    CustomerModel customerBill;
    ArrayList<ProductModel> productsArray;
    DbHandler dbHandler;

    ActivityBillBinding binding;

    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetBill();
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
        setSupportActionBar(binding.ToolBarBill);
        dbHandler =new DbHandler(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getDataToFromAddCustomerActivity();
        addProView();

        numberOfInstallmentsIsRight(binding.etNumberOfInstallments);
        binding.billLinearList.setOnClickListener(this);
        binding.BilMainConstrain.setOnClickListener(this);
        binding.BillTitle.setOnClickListener(this);
    }

    //import product from Customer Activity
    private void addProView() {
        int quantity=0;
       try {
           for (int i =0 ;i<productsArray.size(); i++){
               final View billView = getLayoutInflater().inflate(R.layout.products_bill, null, false);
               binding.billProductsLinear.addView(billView);

               TextView tvName =billView.findViewById(R.id.billProductName);
               TextView tvPrice =billView.findViewById(R.id.billProductPrice);
               final EditText etQuantity=billView.findViewById(R.id.billProductQuantity);
               etQuantity.setOnFocusChangeListener(this);
               tvName.setText(productsArray.get(i).getName());
               tvPrice.setText(String.valueOf(productsArray.get(i).getPrice()));
               quantityIsRight(etQuantity);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void getDataToFromAddCustomerActivity(){
        Bundle bundle = getIntent().getExtras();
        customerBill = (CustomerModel) getIntent().getSerializableExtra("customer");
        //add customer name to bill
        binding.tvAddBillCusName.setText(customerBill.getName());

        productsArray = (ArrayList<ProductModel>) bundle.getSerializable("productsArray");
    }
    private String getCurrentTime(){
        SimpleDateFormat time  = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss", Locale.getDefault());
        String currentDate = time.format(new Date());
        return currentDate;
    }

    //check fields
    private void quantityIsRight(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    editText.setError("required field");
                } else if (s.length() < 1) {
                    editText.setError("invalid value");
                } else if (s.length() > 10) {
                    editText.setError("invalid value");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void numberOfInstallmentsIsRight(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    editText.setError("required field");
                } else if (s.length() < 1) {
                    editText.setError("invalid value");
                } else if (s.length() > 4) {
                    editText.setError("invalid value");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private boolean AllQuantityIsRight(){
        ArrayList<Boolean>qua = new ArrayList<>();
        for (int i =0;i<binding.billProductsLinear.getChildCount();i++){
            binding.billProductsLinear.getChildAt(i).clearFocus();
            View v=binding.billProductsLinear.getChildAt(i);
            EditText etQuantity =v.findViewById(R.id.billProductQuantity);
            if(etQuantity.getText().length()==0){
                etQuantity.setError("Enter the quantity");
            }
            if(etQuantity.getError()==null) {
                qua.add(true);
            }else{
                qua.add(false);
            }
        }
        return NewBill.AreAllTrue(qua);
    }

    //######################

    public void calcTotalThenSaveAllData(View view) {
       try {
           if(flag){
               if(AllQuantityIsRight()){
                   ArrayList<Integer> quantityArray =new ArrayList<>();
                   ArrayList<Double> totalArray =new ArrayList<>();
                   BillModel.setTotalPrice(0);
                   for (int i =0;i<binding.billProductsLinear.getChildCount();i++){
                       binding.billProductsLinear.getChildAt(i).clearFocus();
                       View v=binding.billProductsLinear.getChildAt(i);
                       binding.billLinearList.getChildAt(i).clearFocus();

                       //initialize variable
                       EditText etQuantity =v.findViewById(R.id.billProductQuantity);
                       TextView etTotal =v.findViewById(R.id.totalProPrice);
                       int quantity =Integer.parseInt(etQuantity.getText().toString());
                       double total = quantity * productsArray.get(i).getPrice();

                       quantityArray.add(quantity);
                       totalArray.add(total);

                       //set total every product
                       etTotal.setText(String.valueOf(total));

                   }
                   BillModel.setCustomerName(customerBill.getName());
                   BillModel.setCustomerName(customerBill.getName());

                   BillModel.setProducts(productsArray);
                   BillModel.setTotal(totalArray);
                   BillModel.setQuantity(quantityArray);
                   BillModel.setPayedType(BillModel.getPayedType());
                   BillModel.setPayedTime(getCurrentTime());

                   binding.tvAddBillTotal.setText(String.valueOf(BillModel.getTotalPrice()));
                   binding.tvAddBillDate.setText(BillModel.getPayedTime());
                   BillModel.print();
                   flag =false;
                   binding.saveAllCus.setText("Save");
               }else{
                   Log.w("quantity","false");

               }

           }else {
               Log.w("saved","true");
               //TODO ADD TO DATA BASE

               Intent intent = new Intent(this,MainActivity.class);
               startActivity(intent);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void resetBill(){
        BillModel.setCustomerName("");
        BillModel.setProducts(null);
        BillModel.setTotal(null);

        BillModel.setQuantity(null);
        BillModel.setTotalPrice(0);
        BillModel.setGiven(0);

        BillModel.setPayedTime("");
        BillModel.setPayedType("");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            binding.saveAllCus.setText("Total");
            flag=true;

        }
    }
    public void hideKeyBoard(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.BilMainConstrain.getId()||v.getId()==binding.billLinearList.getId()||v.getId()==binding.BillTitle.getId()){
            hideKeyBoard(v);
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

