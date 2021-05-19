package com.ahmettekin.WeatherApp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherModel implements Parcelable {

    @SerializedName("list")
    public ArrayList<WeatherItem> weatherItems;

    public static class WeatherItem implements Serializable, Parcelable {
        private Weather[] weather;
        private Main main;
        private Coord coord;
        private String name;
        private int id;

        protected WeatherItem(Parcel in) {
            weather = in.createTypedArray(Weather.CREATOR);
            main = in.readParcelable(Main.class.getClassLoader());
            coord = in.readParcelable(Coord.class.getClassLoader());
            name = in.readString();
            id = in.readInt();
        }

        public int getId() {
            return id;
        }

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

        public static class Weather implements Serializable, Parcelable {
            private long id;
            private String main;
            private String description;
            private String icon;

            protected Weather(Parcel in) {
                id = in.readLong();
                main = in.readString();
                description = in.readString();
                icon = in.readString();
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

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(id);
                dest.writeString(main);
                dest.writeString(description);
                dest.writeString(icon);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<Weather> CREATOR = new Creator<Weather>() {
                @Override
                public Weather createFromParcel(Parcel in) {
                    return new Weather(in);
                }

                @Override
                public Weather[] newArray(int size) {
                    return new Weather[size];
                }
            };

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

        }

        public static class Main implements Serializable, Parcelable {
            private double temp;
            @SerializedName("feels_like")
            private double feelsLike;
            @SerializedName("temp_min")
            private double tempMin;
            @SerializedName("temp_max")
            private double tempMax;
            private double pressure;
            private double humidity;

            protected Main(Parcel in) {
                temp = in.readDouble();
                feelsLike = in.readDouble();
                tempMin = in.readDouble();
                tempMax = in.readDouble();
                pressure = in.readDouble();
                humidity = in.readDouble();
            }

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

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeDouble(temp);
                dest.writeDouble(feelsLike);
                dest.writeDouble(tempMin);
                dest.writeDouble(tempMax);
                dest.writeDouble(pressure);
                dest.writeDouble(humidity);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<Main> CREATOR = new Creator<Main>() {
                @Override
                public Main createFromParcel(Parcel in) {
                    return new Main(in);
                }

                @Override
                public Main[] newArray(int size) {
                    return new Main[size];
                }
            };


        }

        public static class Coord implements Serializable, Parcelable {
            private double lon;
            private double lat;

            protected Coord(Parcel in) {
                lon = in.readDouble();
                lat = in.readDouble();
            }

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

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeDouble(lon);
                dest.writeDouble(lat);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<Coord> CREATOR = new Creator<Coord>() {
                @Override
                public Coord createFromParcel(Parcel in) {
                    return new Coord(in);
                }

                @Override
                public Coord[] newArray(int size) {
                    return new Coord[size];
                }
            };


        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedArray(weather, flags);
            dest.writeParcelable(main, flags);
            dest.writeParcelable(coord, flags);
            dest.writeString(name);
            dest.writeInt(id);
        }


        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<WeatherItem> CREATOR = new Creator<WeatherItem>() {
            @Override
            public WeatherItem createFromParcel(Parcel in) {
                return new WeatherItem(in);
            }

            @Override
            public WeatherItem[] newArray(int size) {
                return new WeatherItem[size];
            }
        };

    }

    protected WeatherModel(Parcel in) {
        weatherItems = in.createTypedArrayList(WeatherItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(weatherItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherModel> CREATOR = new Creator<WeatherModel>() {
        @Override
        public WeatherModel createFromParcel(Parcel in) {
            return new WeatherModel(in);
        }

        @Override
        public WeatherModel[] newArray(int size) {
            return new WeatherModel[size];
        }
    };
}


