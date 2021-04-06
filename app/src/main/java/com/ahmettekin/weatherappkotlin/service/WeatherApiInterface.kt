package com.ahmettekin.weatherappkotlin.service

import com.ahmettekin.weatherappkotlin.model.WeatherModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiInterface {
    @GET("group?lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric")
    fun getWeatherData(@Query("id") id: String?): Observable<WeatherModel?>?
}