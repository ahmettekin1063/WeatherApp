package com.ahmettekin.WeatherApp.listener;

import com.ahmettekin.WeatherApp.adapter.RecyclerViewAdapter;
import com.ahmettekin.WeatherApp.model.WeatherModel;

import java.util.ArrayList;

public interface RecyclerViewOnClickListener {
    void recyclerViewDeleteClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList, RecyclerViewAdapter.RowHolder holder);
    void recyclerViewItemViewClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList);
}
