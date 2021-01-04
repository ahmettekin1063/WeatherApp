package com.ahmettekin.WeatherApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ListFragment extends Fragment {

    ArrayList<WeatherModel.WeatherItem> weatherItems;
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        Gson gson = new GsonBuilder().setLenient().create();
        String BASE_URL = "http://api.openweathermap.org/data/2.5/";

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

    }

    public void handleResponse(WeatherModel weatherModel) {

        this.weatherItems = weatherModel.weatherItems;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(this.weatherItems);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        compositeDisposable.clear();
    }
}