package com.ahmettekin.WeatherApp.service;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.ahmettekin.WeatherApp.database.LocalDataClass;
import com.ahmettekin.WeatherApp.model.CityModel;
import com.ahmettekin.WeatherApp.view.AddCityFragmentDirections;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityService {
    private ArrayList<CityModel> cityModels;
    private Retrofit retrofit;
    private static final String BASE_URL = "https://raw.githubusercontent.com/";
    private static CityService cityService = null;
    private static final String cityNotFoundText = "Aradığınız Şehir Bulunamadı";

    private CityService() {
    }

    public static CityService getInstance() {
        if (cityService == null) {
            cityService = new CityService();
        }
        return cityService;
    }

    public void writeDataLocalDatabase(String cityName, View view) {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CityAPI cityAPI = retrofit.create(CityAPI.class);
        Call<List<CityModel>> call = cityAPI.getData();
        call.enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.isSuccessful()) {
                    List<CityModel> responseList = response.body();
                    cityModels = new ArrayList<>(responseList);
                    boolean sehirBulundu = false;
                    String enteredCityName = upperCaseWords(cityName);

                    for (CityModel cityModel : cityModels) {
                        if (cityModel.name.matches(enteredCityName)) {
                            LocalDataClass.getInstance().veriYaz(cityModel.name, cityModel.id, view.getContext());
                            sehirBulundu = true;
                            break;
                        }
                    }

                    if (!sehirBulundu) {
                        Toast.makeText(view.getContext(), cityNotFoundText, Toast.LENGTH_SHORT).show();
                    }
                    NavDirections navDirections = AddCityFragmentDirections.actionAddCityFragmentToListFragment();
                    Navigation.findNavController(view).navigate(navDirections);
                }
            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String upperCaseWords(String line) {
        line = line.trim().toLowerCase();
        String[] data = line.split("\\s");
        StringBuilder lineBuilder = new StringBuilder();

        for (String datum : data) {
            if (datum.length() > 1) {
                lineBuilder.append(datum.substring(0, 1).toUpperCase()).append(datum.substring(1)).append(" ");
            } else {
                lineBuilder.append(datum.toUpperCase());
            }
        }

        line = lineBuilder.toString();
        return line.trim();
    }
}