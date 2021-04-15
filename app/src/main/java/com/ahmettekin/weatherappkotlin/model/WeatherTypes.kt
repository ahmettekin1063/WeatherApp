package com.ahmettekin.weatherappkotlin.model

import com.ahmettekin.weatherappkotlin.R

enum class WeatherTypes(val weatherType: String? = null, val weatherImage: Int) {
    MIST("Mist", R.drawable.mist),
    SNOW("Snow", R.drawable.snow),
    RAIN("Rain", R.drawable.rain),
    CLOUDS("Clouds", R.drawable.clouds),
    CLEAR("Clear", R.drawable.clear),
    THUNDERSTORM("Thunderstorm", R.drawable.thunderstorm),
    DRIZZLE("Drizzle", R.drawable.drizzle),
    UNKNOWN(weatherImage=R.drawable.unknown)
}