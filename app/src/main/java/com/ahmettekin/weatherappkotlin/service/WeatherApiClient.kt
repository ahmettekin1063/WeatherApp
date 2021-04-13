package com.ahmettekin.weatherappkotlin.service

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiClient {

    val BASE_URL="https://api.openweathermap.org/data/2.5/"
    private var retrofit: Retrofit? = null
    var gson = GsonBuilder().setLenient().create()
    val clientBuilder = OkHttpClient.Builder()
    val loggingInterceptor= HttpLoggingInterceptor()

    val client: Retrofit?
        get() {
            if(retrofit ==null){
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                clientBuilder.addInterceptor(loggingInterceptor)
                retrofit =Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
}