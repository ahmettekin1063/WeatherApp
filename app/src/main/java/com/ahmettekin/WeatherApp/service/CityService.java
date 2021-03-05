package com.ahmettekin.WeatherApp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityService {
    
    public static final String BASE_URL = "https://raw.githubusercontent.com/";
    private static CityService cityService = null;
    public static final String cityNotFoundText = "Aradığınız Şehir Bulunamadı";

    private CityService() {
    }

    public static CityService getInstance() {
        if (cityService == null) {
            cityService = new CityService();
        }
        return cityService;
    }

    public Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}