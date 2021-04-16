package com.ahmettekin.WeatherApp.listener;

import android.view.View;

import com.ahmettekin.WeatherApp.model.WeatherModel;

import javax.annotation.Nullable;

public interface RecyclerViewOnClickListener {
    void recyclerViewItemViewClick(WeatherModel.WeatherItem weatherItem, @Nullable View deleteView);
}
