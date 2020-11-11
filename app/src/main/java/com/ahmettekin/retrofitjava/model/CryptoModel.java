package com.ahmettekin.retrofitjava.model;

import androidx.annotation.AttrRes;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("currency")
    public String currency;
//interface kullanımını anla!!!
    @SerializedName("price")
    public String price;

}
