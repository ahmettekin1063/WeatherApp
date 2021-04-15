package com.ahmettekin.weatherappkotlin

import android.view.View
import android.view.ViewGroup
import com.ahmettekin.weatherappkotlin.model.MistTypes
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import com.ahmettekin.weatherappkotlin.model.WeatherTypes

internal fun WeatherModel.WeatherItem?.getSkyImage(): Int {
    var picture = WeatherTypes.UNKNOWN.weatherImage

    for (temp in MistTypes.values()) {
        if (this?.weather?.get(0)?.main.equals(temp.name)) {
            picture = WeatherTypes.MIST.weatherImage
        }
    }

    for (temp in WeatherTypes.values()) {
        if (this?.weather?.get(0)?.main.equals(temp.weatherType)) {
            picture = temp.weatherImage
        }
    }
    return picture
}

internal fun String.upperCaseWords(): String {
    var line = this
    line = line.trim().toLowerCase()
    val data = line.split("\\s")
    val lineBuilder = StringBuilder()

    for (datum in data) {
        if (datum.length > 1) {
            lineBuilder.append(datum.substring(0, 1).toUpperCase()).append(datum.substring(1)).append(" ")
        } else {
            lineBuilder.append(datum.toUpperCase())
        }
    }
    line = lineBuilder.toString()
    return line.trim()
}

internal fun View.removeFromSuperView(){
    if(this.parent !=null){
        (this.parent as ViewGroup).removeView(this)
    }
}