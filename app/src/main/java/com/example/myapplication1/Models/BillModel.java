package com.example.myapplication1.Models;

import android.util.Log;

import java.util.ArrayList;

public class BillModel {
    public static String customerName;
    public static ArrayList<ProductModel> products;
    public static ArrayList<Double> total;
    public static ArrayList<Integer> quantity;
    public static int number_of_installments;
    public static String payedType;

    public static double totalPrice;
    public static String payedTime;
    public static double given;




    public static String getCustomerName() {
        return customerName;
    }
    public static void setTotalPrice(double totalPrice) {
        BillModel.totalPrice = totalPrice;
    }

    public static void setCustomerName(String customerName) {
        BillModel.customerName = customerName;
    }

    public static ArrayList<ProductModel> getProducts() {
        return products;
    }

    public static void setProducts(ArrayList<ProductModel> products) {
        BillModel.products = products;
    }

    public static ArrayList<Double> getTotal() {
        return total;
    }

    public static void setTotal(ArrayList<Double> total) {
        BillModel.total = total;
    }

    public static ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public static void setQuantity(ArrayList<Integer> quantity) {
        BillModel.quantity = quantity;
    }

    public static int getNumber_of_installments() {
        return number_of_installments;
    }

    public static void setNumber_of_installments(int number_of_installments) {
        BillModel.number_of_installments = number_of_installments;
    }

    public static String getPayedType() {
        return payedType;
    }

    public static void setPayedType(String payedType) {
        BillModel.payedType = payedType;
    }

    public static String getPayedTime() {
        return payedTime;
    }

    public static void setPayedTime(String payedTime) {
        BillModel.payedTime = payedTime;
    }

    public static double getGiven() {
        return given;
    }

    public static void setGiven(double given) {
        BillModel.given = given;
    }

    public static double getTotalPrice() {
        for (double i :total)
            totalPrice+=i;

        return totalPrice;
    }

    public static void print (){
        Log.w("cus name",customerName);
        Log.w("payed Type",payedType);
        Log.w("payedTime",payedTime);
        for (int i =0; i <products.size();i++){
            Log.w("pro name",products.get(i).getName());
            Log.w("pro price", String.valueOf(products.get(i).getPrice()));
            Log.w("pro quantity", String.valueOf(quantity.get(i)));
            Log.w("pro total", String.valueOf(total.get(i)));
        }
        Log.w("total Price", String.valueOf(totalPrice));
        Log.w("given", String.valueOf(given));
        Log.w("Number_of_installments", String.valueOf(number_of_installments));
    }

}