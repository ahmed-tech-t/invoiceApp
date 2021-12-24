package com.example.myapplication1.Models;

import android.util.Log;

import java.io.Serializable;

public class CustomerModel implements Serializable {

    String name;
    String age;
    String gender;
    String jop;
    String idCard;
    String PhoneNumber;
    String address;

    public CustomerModel(){
        this.name = "";
        this.age = "";
        this.gender = "";
        this.PhoneNumber = "";
        this.jop = "";
        this.address = "";
        this.idCard = "";
    }
    public CustomerModel(String name, String age, String gender, String phoneNumber, String jop, String address, String idCard) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.PhoneNumber = phoneNumber;
        this.jop = jop;
        this.address = address;
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getJop() {
        return jop;
    }

    public void setJop(String jop) {
        this.jop = jop;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void print(){
        Log.w("name",name);
        Log.w("age",age);
        Log.w("gender",gender);
        Log.w("phone",PhoneNumber);
        Log.w("jop",jop);
        Log.w("id card",idCard);
        Log.w("address",address);
    }

}
