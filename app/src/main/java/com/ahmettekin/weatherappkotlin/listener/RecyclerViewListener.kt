package com.ahmettekin.weatherappkotlin.listener

import android.view.View
import com.ahmettekin.weatherappkotlin.model.WeatherModel

interface RecyclerViewListener {
    fun recyclerViewItemClick(weatherItem: WeatherModel.WeatherItem?, deleteView: View?)
}