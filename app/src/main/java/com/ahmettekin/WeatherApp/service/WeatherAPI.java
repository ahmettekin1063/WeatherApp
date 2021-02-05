package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.WeatherModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("group?lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric")
    Observable<WeatherModel> getWeatherData(@Query("id") String id);
}


