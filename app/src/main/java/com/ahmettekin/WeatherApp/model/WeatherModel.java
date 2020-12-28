package com.ahmettekin.WeatherApp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherModel {

    @SerializedName("list")
    public ArrayList<WeatherItem> weatherItems;

  //  public ArrayList<WeatherItem> getWeatherItems() {
   //     return weatherItems;
   // }


    public class WeatherItem{

        private Weather[] weather;

        private Main main;

        private String name;

        public String getName() {

            return name;

        }

        public Weather[] getWeather() {
            return weather;
        }

        public Main getMain() {
            return main;
        }



        public class Weather {
            private long id;
            private String main;
            private String description;
            private String icon;


            public long getId() {
                return id;
            }

            public String getMain() {
            return main;
            }
            public String getDescription() {
                return description;
            }
            public String getIcon() {
                return icon;
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
            public double getFeelsLike() {
                return feelsLike;
            }
            public double getTempMin() {
                return tempMin;
            }
            public double getTempMax() {
                return tempMax;
            }
            public double getPressure() {
                return pressure;
            }
            public double getHumidity() {
                return humidity;
            }

        }

    }



}


