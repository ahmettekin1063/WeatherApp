package com.ahmettekin.retrofitjava.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherModel {

    @SerializedName("list")
    public ArrayList<WeatherItem> weatherItems;

    public ArrayList<WeatherItem> getWeatherItems() {
        return weatherItems;
    }

    public void setWeatherItems(ArrayList<WeatherItem> weatherItems) {
        this.weatherItems = weatherItems;
    }

    public class WeatherItem{
        private Weather[] weather;

        private Main main;

        public Weather[] getWeather() {
            return weather;
        }

        public void setWeather(Weather[] weather) {
            this.weather = weather;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public class Weather {
            private long id;
            private String main;
            private String description;
            private String icon;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        public class Main {

            private double temp;
            @SerializedName("feels_like")
            private double feelsLike;
            @SerializedName("temp_min")
            private double tempMin;
            @SerializedName("temp_max")
            private double tempMax;
            private double pressure;
            private double humidity;

            public double getTemp() {
                return temp;
            }

            public void setTemp(double temp) {
                this.temp = temp;
            }

            public double getFeelsLike() {
                return feelsLike;
            }

            public void setFeelsLike(double feelsLike) {
                this.feelsLike = feelsLike;
            }

            public double getTempMin() {
                return tempMin;
            }

            public void setTempMin(double tempMin) {
                this.tempMin = tempMin;
            }

            public double getTempMax() {
                return tempMax;
            }

            public void setTempMax(double tempMax) {
                this.tempMax = tempMax;
            }

            public double getPressure() {
                return pressure;
            }

            public void setPressure(double pressure) {
                this.pressure = pressure;
            }

            public double getHumidity() {
                return humidity;
            }

            public void setHumidity(double humidity) {
                this.humidity = humidity;
            }
        }

    }



}


