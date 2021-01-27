package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.WeatherModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WeatherAPI {

    @GET("group?id=2013159,524901,2643743,745042,1850144,6167865,5106292,2988507,4140963&lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric")
    Observable<WeatherModel> getWeatherData();


}
