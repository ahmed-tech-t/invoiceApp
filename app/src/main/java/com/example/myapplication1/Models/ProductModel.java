package com.example.myapplication1.Models;

import android.util.Log;

import java.io.Serializable;

public class ProductModel implements Serializable {


    String name;
    double price;
    int code;
    String countryOfOrigin;
    String ProductionDate;
    String ExpiryDate;
    String StoreName;
    double Weight;
    String Section;

    public ProductModel(int code,double price){
        this.code = code;
        this.price = price;
    }
    public ProductModel(int code,String name){
        this.code =code;
    }
    public ProductModel(int code,String name, double price, String countryOfOrigin, String productionDate, String expiryDate,double weight,String section,String storeName) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.countryOfOrigin = countryOfOrigin;
        ProductionDate = productionDate;
        ExpiryDate = expiryDate;
        StoreName = storeName;
        Weight = weight;
        Section = section;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getProductionDate() {
        return ProductionDate;
    }

    public void setProductionDate(String productionDate) {
        ProductionDate = productionDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }


    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public ProductModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void print(){
        Log.w("name",name);
        Log.w("price",String.valueOf(price));
    }

}
