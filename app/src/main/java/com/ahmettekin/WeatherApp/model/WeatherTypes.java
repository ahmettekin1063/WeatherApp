package com.ahmettekin.WeatherApp.model;

import com.ahmettekin.WeatherApp.R;

public enum WeatherTypes {
    mist("Mist", R.drawable.mist),
    snow("Snow", R.drawable.snow),
    rain("Rain", R.drawable.rain),
    clouds("Clouds", R.drawable.clouds),
    clear("Clear", R.drawable.clear),
    thunderstorm("Thunderstorm", R.drawable.thunderstorm),
    drizzle("Drizzle", R.drawable.drizzle),
    unknown(R.drawable.unknown);

    private String weatherType = "null";
    private final int weatherImage;

    public int getWeatherImage() {
        return weatherImage;
    }

    public String getWeatherType() {
        return weatherType;
    }

    WeatherTypes(String weatherType, int weatherImage) {
        this.weatherType = weatherType;
        this.weatherImage = weatherImage;
    }

    WeatherTypes(int weatherImage) {
        this.weatherImage = weatherImage;
    }
}
