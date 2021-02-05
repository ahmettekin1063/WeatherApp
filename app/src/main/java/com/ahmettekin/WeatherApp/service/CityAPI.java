package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.CityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityAPI {
    @GET("ahmettekin1063/CityListJson/master/city.list.tr.json")
    Call<List<CityModel>> getData();
}
