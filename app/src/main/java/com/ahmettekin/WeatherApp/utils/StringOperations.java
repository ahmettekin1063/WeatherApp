package com.ahmettekin.WeatherApp.utils;

public class StringOperations {
    public static String upperCaseWords(String line) {
        line = line.trim().toLowerCase();
        String[] data = line.split("\\s");
        StringBuilder lineBuilder = new StringBuilder();

        for (String datum : data) {
            if (datum.length() > 1) {
                lineBuilder.append(datum.substring(0, 1).toUpperCase()).append(datum.substring(1)).append(" ");
            } else {
                lineBuilder.append(datum.toUpperCase());
            }
        }

        line = lineBuilder.toString();
        return line.trim();
    }
}
