package com.ahmettekin.WeatherApp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmettekin.WeatherApp.service.CityService;

public class LocalDataClass extends AppCompatActivity {
    private String strSqlQuery = "SELECT * FROM cities";
    private String databaseName = "Cities";
    private String createSqlTable = "CREATE TABLE IF NOT EXISTS cities(id INTEGER, cityname VARCHAR)";
    private String insertSqlTable = "INSERT INTO cities(id, cityname) VALUES (?, ?)";
    private String columnIndexCityName = "cityname";
    private String columnIndexCityId = "id";
    private String deleteSqlCommand = "DELETE FROM cities";
    private static LocalDataClass localDataClass = null;

    private LocalDataClass() {
    }

    //    String id="2013159,524901,2643743,745042,1850144,6167865,5106292,2988507,4140963";
    public static LocalDataClass getInstance() {
        if (localDataClass == null) {
            localDataClass = new LocalDataClass();
        }
        return localDataClass;
    }

    public String idGetir(Context context) {
        SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        StringBuilder groupIds = new StringBuilder("2013159");

        try {
            Cursor cursor = database.rawQuery(strSqlQuery, null);
            int idIx = cursor.getColumnIndex(columnIndexCityId);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIx);
                groupIds.append(id);
                groupIds.append(",");
            }

            groupIds.deleteCharAt(groupIds.lastIndexOf(","));
            cursor.close();
        } catch (Exception e) {
            System.out.println("error fetching id");
        }
        return groupIds.toString();
    }

    public void veriYaz(String cityName, Context context) {
        int eklenecekId = servistenGirilenSehrinIdsiniCek(cityName);

        SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        database.execSQL(createSqlTable);
        String toCompile = insertSqlTable;
        SQLiteStatement sqLiteStatement = database.compileStatement(toCompile);
        sqLiteStatement.bindString(1, String.valueOf(eklenecekId));
        sqLiteStatement.bindString(2, cityName);
        sqLiteStatement.execute();
        Cursor cursor = database.rawQuery(strSqlQuery, null);

        int cityNameIx = cursor.getColumnIndex(columnIndexCityName);
        int idIx = cursor.getColumnIndex(columnIndexCityId);

        while (cursor.moveToNext()) {
            System.out.println("Id: " + cursor.getInt(idIx));
            System.out.println("Şehir: " + cursor.getString(cityNameIx));
        }
        cursor.close();
    }

    private int servistenGirilenSehrinIdsiniCek(String cityName) {
        CityService.getInstance().getCityId(cityName);
        return CityService.getInstance().getCityId(cityName);
    }

    public void deleteDatabase(Context context) {
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        database.execSQL(deleteSqlCommand);
    }
}
