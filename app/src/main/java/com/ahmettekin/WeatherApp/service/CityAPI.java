package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.CityModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CityAPI {
    @GET("ahmettekin1063/CityListJson/master/city.list.tr.json")
    Observable<List<CityModel>> getData();
}
