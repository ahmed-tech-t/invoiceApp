package com.example.myapplication1.Models;

import android.util.Log;

import java.util.ArrayList;

public class Bill_Model {

    public String customerName;
    public String productName;
    public String productPrice;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Bill_Model(String customerName, String productName, String productPrice) {
        this.customerName = customerName;
        this.productName = productName;
        this.productPrice = productPrice;
    }




}