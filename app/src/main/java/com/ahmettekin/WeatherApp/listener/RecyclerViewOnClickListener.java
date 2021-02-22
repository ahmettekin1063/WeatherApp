package com.ahmettekin.WeatherApp.listener;

import android.view.View;

import com.ahmettekin.WeatherApp.model.WeatherModel;

import java.util.ArrayList;

public interface RecyclerViewOnClickListener {
    void recyclerViewDeleteClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList, View view);
    void recyclerViewItemViewClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList);
}
