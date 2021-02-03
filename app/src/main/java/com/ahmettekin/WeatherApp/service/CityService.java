package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.CityModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityService {
    private ArrayList<CityModel> cityModels;
    private Retrofit retrofit;
    private String BASE_URL = " https://raw.githubusercontent.com/";
    private int istenenId = 0;

    private static CityService cityService = null;

    private CityService() {
    }

    public static CityService getInstance() {
        if (cityService == null) {
            cityService = new CityService();
            return cityService;
        } else {
            return cityService;
        }
    }

    public int loadData(String cityName) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        CityAPI cityAPI = retrofit.create(CityAPI.class);

        Call<List<CityModel>> call = cityAPI.getData();
        call.enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {

                if (response.isSuccessful()) {

                    List<CityModel> responseList = response.body();
                    cityModels = new ArrayList<>(responseList);

                    for (CityModel cityModel : cityModels) {
                        if (cityModel.name.matches(cityName)) {
                            istenenId = cityModel.id;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return istenenId;
    }
}
