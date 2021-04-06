package com.ahmettekin.weatherappkotlin.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiClient {

    val BASE_URL="http://api.openweathermap.org/data/2.5/"
    private var retrofit: Retrofit? = null
    var gson = GsonBuilder().setLenient().create()
    val client: Retrofit?
        get() {
            if(retrofit ==null){
                retrofit =Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit
        }
}