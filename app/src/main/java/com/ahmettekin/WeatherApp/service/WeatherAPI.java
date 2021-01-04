package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.WeatherModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WeatherAPI {

    //@GET("box/city?bbox=40,38,41,42,10&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric")
    @GET("group?id=2013159,524901,2643743,745042,1850144,6167865,5106292,2988507,4140963&lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric")
    //@GET("find?lat=39.92077&lon=32.85411&cnt=10&lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric")
    Observable<WeatherModel> getWeatherData();

}
