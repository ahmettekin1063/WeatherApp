package com.ahmettekin.WeatherApp.model;

import com.ahmettekin.WeatherApp.R;

public enum WeatherTypes {
    mist("Mist",R.drawable.mist), snow("Snow", R.drawable.snow), rain("Rain",R.drawable.rain),
    clouds("Clouds",R.drawable.clouds), clear("Clear",R.drawable.clear), thunderstorm("Thunderstorm",R.drawable.thunderstorm),
    drizzle("Drizzle",R.drawable.drizzle),unknown(R.drawable.unknown);

    private String weatherType = "null";
    private final int value;

    public int getValue() {
        return value;
    }

    public String getWeatherType() {
        return weatherType;
    }

    WeatherTypes(String weatherType, int value) {
        this.weatherType = weatherType;
        this.value=value;
    }

    WeatherTypes(int value) {
        this.value=value;
    }
}
