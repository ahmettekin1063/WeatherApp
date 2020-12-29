package com.ahmettekin.WeatherApp.model;

public enum WeatherTypes {
    mist("Mist"),snow("Snow"),rain("Rain"),clouds("Clouds"),clear("Clear"),thunderstorm("Thunderstorm"),drizzle("Drizzle");

    private String weatherType;

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    WeatherTypes(String weatherType) {
        this.weatherType=weatherType;
    }
}
