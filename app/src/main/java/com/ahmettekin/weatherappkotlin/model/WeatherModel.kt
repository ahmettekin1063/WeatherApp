package com.ahmettekin.weatherappkotlin.model


import com.google.gson.annotations.SerializedName

class WeatherModel(
    @SerializedName("cnt")
    var cnt: Int,
    @SerializedName("list")
    var list: List<WeatherItem>
) {
    class WeatherItem(
        @SerializedName("clouds")
        var clouds: Clouds,
        @SerializedName("coord")
        var coord: Coord,
        @SerializedName("dt")
        var dt: Int,
        @SerializedName("id")
        var id: Int,
        @SerializedName("main")
        var main: Main,
        @SerializedName("name")
        var name: String,
        @SerializedName("sys")
        var sys: Sys,
        @SerializedName("visibility")
        var visibility: Int,
        @SerializedName("weather")
        var weather: List<Weather>,
        @SerializedName("wind")
        var wind: Wind
    ) {
        class Clouds(
            @SerializedName("all")
            var all: Int
        )

        class Coord(
            @SerializedName("lat")
            var lat: Double,
            @SerializedName("lon")
            var lon: Double
        )

        class Main(
            @SerializedName("feels_like")
            var feelsLike: Double,
            @SerializedName("humidity")
            var humidity: Int,
            @SerializedName("pressure")
            var pressure: Int,
            @SerializedName("temp")
            var temp: Double,
            @SerializedName("temp_max")
            var tempMax: Double,
            @SerializedName("temp_min")
            var tempMin: Double
        )

        class Sys(
            @SerializedName("country")
            var country: String,
            @SerializedName("sunrise")
            var sunrise: Int,
            @SerializedName("sunset")
            var sunset: Int,
            @SerializedName("timezone")
            var timezone: Int
        )

        class Weather(
            @SerializedName("description")
            var description: String,
            @SerializedName("icon")
            var icon: String,
            @SerializedName("id")
            var id: Int,
            @SerializedName("main")
            var main: String
        )

        class Wind(
            @SerializedName("deg")
            var deg: Int,
            @SerializedName("speed")
            var speed: Double
        )
    }
}