package com.ahmettekin.retrofitjava.service;

import com.ahmettekin.retrofitjava.model.CryptoModel;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
public interface CryptoAPI {

    //https://api.nomics.com/v1/prices?key=d58e4dc4e10e1b73538d6d41f61ba0b1
//annotations


  @GET("prices?key=d58e4dc4e10e1b73538d6d41f61ba0b1")

    Observable<List<CryptoModel>> getData();

    //Call<List<CryptoModel>> getData();

}
