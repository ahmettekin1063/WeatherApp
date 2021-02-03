package com.ahmettekin.WeatherApp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmettekin.WeatherApp.service.CityService;

public class YerelVeriSinifi extends AppCompatActivity {


    //    String id="2013159,524901,2643743,745042,1850144,6167865,5106292,2988507,4140963";

    public String idGetir(Context context) {

        SQLiteDatabase database = context.openOrCreateDatabase("Cities", MODE_PRIVATE, null);
        StringBuilder stringBuilder = new StringBuilder("2013159");
        try {

            Cursor cursor = database.rawQuery("SELECT * FROM cities ", null);

            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIx);
                stringBuilder.append(id);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            cursor.close();
        } catch (Exception e) {
            System.out.println("id getir hatası");
        }
        return stringBuilder.toString();
    }


    public void veriYaz(String cityName, Context context) {


        int eklenecekid = servistenGirilenSehrinIdsiniCek(cityName);

        SQLiteDatabase database = context.openOrCreateDatabase("Cities", MODE_PRIVATE, null);

        database.execSQL("CREATE TABLE IF NOT EXISTS cities(id INTEGER, cityname VARCHAR)");

        String toCompile = "INSERT INTO cities(id, cityname) VALUES (?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(toCompile);

        sqLiteStatement.bindString(1, String.valueOf(eklenecekid));
        sqLiteStatement.bindString(2, cityName);

        sqLiteStatement.execute();

        Cursor cursor = database.rawQuery("SELECT * FROM cities ", null);
        int cityNameIx = cursor.getColumnIndex("cityname");
        int idIx = cursor.getColumnIndex("id");

        while (cursor.moveToNext()) {

            System.out.println("Id: " + cursor.getInt(idIx));
            System.out.println("Şehir: " + cursor.getString(cityNameIx));

        }

        cursor.close();
    }

    private int servistenGirilenSehrinIdsiniCek(String cityName) {

        CityService cityService = CityService.getInstance();
        return cityService.loadData(cityName);

    }


}
