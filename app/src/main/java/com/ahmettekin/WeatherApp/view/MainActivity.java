package com.ahmettekin.WeatherApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.adapter.RecyclerViewAdapter;
import com.ahmettekin.WeatherApp.model.WeatherModel;
import com.ahmettekin.WeatherApp.service.WeatherAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<WeatherModel.WeatherItem> weatherItems;
    private String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //https://api.nomics.com/v1/prices?key=2187154b76945f2373394aa34f7dc98a


        recyclerView = findViewById(R.id.recyclerView);

        //Retrofit & JSON

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        loadData();

    }



    private void loadData() {

        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);


        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(weatherAPI.getWeatherData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponse)
        );
/*
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    for (CryptoModel cryptoModel : cryptoModels) {
                        System.out.println(cryptoModel.currency);
                        System.out.println(cryptoModel.price);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

*/
    }
    public void handleResponse (WeatherModel weatherModel){

        this.weatherItems = weatherModel.weatherItems;

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(this.weatherItems);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        compositeDisposable.clear();
    }
}
