package com.ahmettekin.WeatherApp.listener;

import android.view.View;

import com.ahmettekin.WeatherApp.adapter.RecyclerViewAdapter;
import com.ahmettekin.WeatherApp.model.WeatherModel;

public interface RecyclerViewOnClickListener {
    void recyclerViewItemViewClick(RecyclerViewAdapter.RowHolder holder, WeatherModel.WeatherItem weatherItem, View view);
}
