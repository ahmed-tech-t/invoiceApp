package com.example.myapplication1.Models;

public class QrCodeModel {
    String ProductName;
    String code;

    public QrCodeModel(String code,String productName ) {
        ProductName = productName;
        this.code = code;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
