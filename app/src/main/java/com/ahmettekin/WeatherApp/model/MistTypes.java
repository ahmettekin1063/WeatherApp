package com.ahmettekin.WeatherApp.model;

public enum MistTypes {
   mist("Mist"),smoke("Smoke"),haze("Haze"),ash("Ash"),fog("Fog"),dust("Dust"),sand("Sand"),squall("Squall"),tornado("Tornado");

   private String mistType;

    public String getMistType() {
        return mistType;
    }

    public void setMistType(String mistType) {
        this.mistType = mistType;
    }

    MistTypes(String mistType) {
        this.mistType = mistType;
    }
}
