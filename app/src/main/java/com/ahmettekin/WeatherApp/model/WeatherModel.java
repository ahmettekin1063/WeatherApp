package com.ahmettekin.WeatherApp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class WeatherModel {

    @SerializedName("list")
    public ArrayList<WeatherItem> weatherItems;
    
    public class WeatherItem  {
        private Weather[] weather;
        private Main main;
        private Coord coord;
        private String name;

        public String getName() {
            return name;
        }

        public Weather[] getWeathers() {
            return weather;
        }

        public Main getMain() {
            return main;
        }

        public Coord getCoord() {
            return coord;
        }

        public class Weather {
            private long id;
            private String main;
            private String description;
            private String icon;

            public int getSkyId() {
                int picture = WeatherTypes.unknown.getWeatherImage();

                for (MistTypes mistType : MistTypes.values()) {
                    if (this.main.matches(mistType.getMistType())) {
                        picture = WeatherTypes.mist.getWeatherImage();
                    }
                }

                for (WeatherTypes temp : WeatherTypes.values()) {
                    if (this.main.matches(temp.getWeatherType())) {
                        picture = temp.getWeatherImage();
                    }
                }
                return picture;
            }

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

        public class Coord {
            private double lon;
            private double lat;

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeatherItem that = (WeatherItem) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}


