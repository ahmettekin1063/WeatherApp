package com.ahmettekin.WeatherApp.model;

import com.ahmettekin.WeatherApp.R;

public enum WeatherImageTypes {
    mist(R.drawable.mist), snow(R.drawable.snow), clear(R.drawable.clear), rain(R.drawable.rain), clouds(R.drawable.clouds), tunderstorm(R.drawable.thunderstorm), drizzle(R.drawable.drizzle), unknown(R.drawable.unknown);

    private int value;

    WeatherImageTypes(int value) {
        this.value = value;

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static WeatherImageTypes valueOf(int value){
        WeatherImageTypes newValue = null;
        for (WeatherImageTypes weatherType : WeatherImageTypes.values()) {
            if (value == weatherType.value) {
                newValue = weatherType;
                break;
            }
        }

        if (newValue == null){
            return unknown;
        }

        return newValue;
    }
}
