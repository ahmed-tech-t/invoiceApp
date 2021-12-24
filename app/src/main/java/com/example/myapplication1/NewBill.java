package com.example.myapplication1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.Models.CustomerModel;
import com.example.myapplication1.Models.ProductModel;
import com.example.myapplication1.databinding.ActivityNewBillBinding;

import java.util.ArrayList;
import java.util.Locale;

public class NewBill extends AppCompatActivity implements View.OnClickListener ,ScannerFragment.SendDataInterFace,SelectCustomer.SendCusNameInterFace {
    ActivityNewBillBinding binding;
    ArrayList<ProductModel> productsArray;
    CustomerModel customerModel;
    int chiledId=0;

    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerModel =new CustomerModel();
        binding = ActivityNewBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
        setSupportActionBar(binding.ToolBarAddBill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("New BIll");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //edit language
        String languageToLoad = "english"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        dbHandler = new DbHandler(this);

        setContentView(view);
        binding.scrollView.setOnClickListener(this);
        binding.linearListPro.setOnClickListener(this);
        binding.biGLinear.setOnClickListener(this);
        binding.middleLinear.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

            FragmentManager fragmentManager =getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentById(R.id.scannerContainer);
            if(fragment!=null){
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
                binding.scrollView.setVisibility(View.VISIBLE);

            }else{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    //add and control new Product
    public void addProductPressed(View view) {
        try {
            addProView(view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addProView(View v) {

        int lastChild= binding.linearListPro.getChildCount();
        if(binding.linearListPro.getChildCount()-1>=0){
            View proView = binding.linearListPro.getChildAt(lastChild-1);
            final ImageView drop1 = proView.findViewById(R.id.hidePro);
            hideProView(proView, drop1);
        }

        final View productView = getLayoutInflater().inflate(R.layout.add_new_product_layout, null, false);
        binding.linearListPro.addView(productView);


        final ImageView drop = productView.findViewById(R.id.hidePro);
        //set focus

        ImageView  imageClosePro = productView.findViewById(R.id.clearPro);
        setConditionsToProduct(productView);

        Button scan = productView.findViewById(R.id.buScan);

        try {
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chiledId = scan.hashCode();
                    binding.scrollView.setVisibility(View.GONE);
                    Fragment mFragment = new ScannerFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.scannerContainer, mFragment).commit();
                }
            });

            imageClosePro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeProView(productView);
                    binding.scrollView.requestFocus();
                }
            });
            drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    proSpinner(productView, drop);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void removeProView(View v) {
        binding.linearListPro.removeView(v);
        hideKeyBoard(v);
    }
    private void proSpinner(View v, ImageView i) {
        hideKeyBoard(v);
        LinearLayout innerLayoutListPro = v.findViewById(R.id.innerLinerListPro);
        if (innerLayoutListPro.getHeight() == 0) {
            i.setImageResource(R.drawable.ic_drop_down);
            innerLayoutListPro.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        } else {
            i.setImageResource(R.drawable.ic_drop_up);
            innerLayoutListPro.getLayoutParams().height = 0;
        }
        innerLayoutListPro.requestLayout();
    }
    private void hideProView(View v, ImageView i) {
        hideKeyBoard(v);
        LinearLayout innerLayoutListPro = v.findViewById(R.id.innerLinerListPro);
        i.setImageResource(R.drawable.ic_drop_up);
        innerLayoutListPro.getLayoutParams().height = 0;
        innerLayoutListPro.requestLayout();
    }
//******************************************************

    //set alarm if fields are empty
    private void ifPressedAndProductFieldsAreEmpty(EditText etPrice) {
        if (etPrice.getText().length() == 0) {
            etPrice.setError("Enter the price");
        }
    }

    //put condition for every edit text

    private void PriceConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("required field");
                }
            }
        });

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
    // put conditions to cus & gua & pro
    private void setConditionsToProduct(View ProView) {
        EditText etPrice = ProView.findViewById(R.id.etAddProPrice);
        TextView etTitle = ProView.findViewById(R.id.addProTitle);
        PriceConditions(etPrice);
    }


    //read data from fields
    private void readDataFromView() {
        //بيانات المنتج
        productsArray = new ArrayList<>();
        for (int i = 0; i < binding.linearListPro.getChildCount(); i++) {
            ProductModel product = new ProductModel();
            View ProView = binding.linearListPro.getChildAt(i);
            //find id
            EditText etPrice = ProView.findViewById(R.id.etAddProPrice);
            TextView etName = ProView.findViewById(R.id.addProTitle);
            TextView etCode=ProView.findViewById(R.id.tvAddProCode);

            //write
            product.setPrice(Double.parseDouble(etPrice.getText().toString()));
            product.setName(etName.getText().toString());
            product.setCode(Integer.parseInt(etCode.getText().toString()));
            productsArray.add(product);
        }
    }

    private ArrayList<Boolean>checkProduct(){
        ArrayList<Boolean>pro = new ArrayList<>();
        for (int i = 0; i < binding.linearListPro.getChildCount(); i++) {
            View ProView = binding.linearListPro.getChildAt(i);
            EditText etPrice = ProView.findViewById(R.id.etAddProPrice);
            TextView tvCode =ProView.findViewById(R.id.tvAddProCode);
            ifPressedAndProductFieldsAreEmpty(etPrice);
            if ( etPrice.getError() == null&&!tvCode.getText().toString().equals("")) {
                pro.add(true);
            }else {
                pro.add(false);
                break;
            }
        }
        return pro;
    }


    private Boolean productIsRight(){
        return AreAllTrue(checkProduct());
    }

    //check if cus and gua and pro is full

    public static boolean AreAllTrue(ArrayList<Boolean> array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }

    //Button save data
    public void MoveToBill(View view) {
        try {
            if(customerModel.getName().equals("")){
                new AlertDialog.Builder(this)
                        .setTitle("Wrong")
                        .setMessage("please select a client first")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else{
                if (productIsRight()){
                    if(binding.linearListPro.getChildCount()<1){
                        new AlertDialog.Builder(this)
                                .setTitle("wrong")
                                .setMessage("please add product before moving to bill")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else{
                        readDataFromView();
                        Intent intent = new Intent(NewBill.this, Bill.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("productsArray",productsArray);
                        intent.putExtra("customer",customerModel);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }if(!productIsRight()){
                    Log.w("Product",String.valueOf(productIsRight()));
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
            if(binding.middleLinear.getId()==v.getId()||binding.biGLinear.getId()==v.getId()||binding.scrollView.getId()==v.getId()||binding.linearListPro.getId()==v.getId()){
                hideKeyBoard(v);
            }
    }
    private void hideKeyBoard(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    private void showKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }


    @Override
    public void sendData(String qrCode) {

        for (int i = 0; i < binding.linearListPro.getChildCount(); i++) {
            View proView = binding.linearListPro.getChildAt(i);
            TextView tv =  proView.findViewById(R.id.addProTitle);
            TextView tv1 =proView.findViewById(R.id.tvAddProCode);

            Button bu = proView.findViewById(R.id.buScan);
            if (bu.hashCode() == chiledId){
                tv1.setText(qrCode);
                tv.setText(dbHandler.getProductName(qrCode));
                break;
            }
        }
        closeFragment();
        binding.scrollView.setVisibility(View.VISIBLE);

    }
    public void closeFragment(){
        FragmentManager fragmentManager ;
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.scannerContainer);
        if(fragment!=null){
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();}
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.scannerContainer);
        if(fragment!=null){
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            binding.scrollView.setVisibility(View.VISIBLE);

        }else{
            super.onBackPressed();
        }
    }

    public void buAddCus(View view) {
        binding.scrollView.setVisibility(View.GONE);
        Fragment mFragment = new SelectCustomer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.scannerContainer, mFragment).commit();
    }

    @Override
    public void getCusName(String name) {
        binding.addCustomer.setText("client :  " + name);
        customerModel.setName(name);
        closeFragment();
        binding.scrollView.setVisibility(View.VISIBLE);

    }
}