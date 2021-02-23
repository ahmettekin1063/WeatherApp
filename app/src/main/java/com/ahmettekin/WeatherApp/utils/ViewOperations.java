package com.ahmettekin.WeatherApp.utils;

import android.view.ViewGroup;

public class ViewOperations {

    public static void removeFromSuperView(ViewGroup childView) {
        ViewGroup parent = (ViewGroup) childView.getParent();
        if (parent != null) {
            parent.removeView(childView);
        }
    }
}
