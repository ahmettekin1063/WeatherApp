package com.ahmettekin.WeatherApp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;
import static android.content.Context.MODE_PRIVATE;

public class LocalDataClass {
    private final String strSqlQuery = "SELECT * FROM cities";
    private final String databaseName = "Cities";
    private final String createSqlTable = "CREATE TABLE IF NOT EXISTS cities(id INTEGER, cityname VARCHAR)";
    private final String insertSqlTable = "INSERT INTO cities(id, cityname) VALUES (?, ?)";
    private final String columnIndexCityName = "cityname";
    private final String columnIndexCityId = "id";
    private final String deleteSqlRecord = "DELETE FROM cities WHERE cityName=?";
    private final String alreadyExistSqlWarningText = "Şehir veritabanında zaten var";
    private static LocalDataClass localDataClass = null;
    private static final String cityAddedText = " şehri başarılı bir şekilde eklendi";

    private LocalDataClass() {
    }

    public static LocalDataClass getInstance() {
        if (localDataClass == null) {
            localDataClass = new LocalDataClass();
        }
        return localDataClass;
    }

    public String getCityIdFromDatabase(Context context) {
        SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        database.execSQL(createSqlTable);
        StringBuilder groupIds = new StringBuilder();
        Cursor cursor = database.rawQuery(strSqlQuery, null);
        int idIx = cursor.getColumnIndex(columnIndexCityId);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIx);
            groupIds.append(id);
            groupIds.append(",");
        }

        groupIds.deleteCharAt(groupIds.lastIndexOf(","));
        cursor.close();
        return groupIds.toString();
    }

    public void veriYaz(String cityName, int eklenecekId, Context context) {
        if (!databaseControl(cityName, context)) {
            SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
            database.execSQL(createSqlTable);
            SQLiteStatement sqLiteStatement = database.compileStatement(insertSqlTable);
            sqLiteStatement.bindString(1, String.valueOf(eklenecekId));
            sqLiteStatement.bindString(2, cityName);
            sqLiteStatement.execute();
            Toast.makeText(context, cityName + cityAddedText, Toast.LENGTH_SHORT).show();

            Cursor cursor = database.rawQuery(strSqlQuery, null);

            int cityNameIx = cursor.getColumnIndex(columnIndexCityName);
            int idIx = cursor.getColumnIndex(columnIndexCityId);

            while (cursor.moveToNext()) {
                System.out.println("Id: " + cursor.getInt(idIx));
                System.out.println("Şehir: " + cursor.getString(cityNameIx));
            }
            cursor.close();
        }else{
            Toast.makeText(context, alreadyExistSqlWarningText,Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDatabase(Context context, String cityToBeDeleted) {
        SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        SQLiteStatement sqLiteStatement = database.compileStatement(deleteSqlRecord);
        sqLiteStatement.bindString(1, String.valueOf(cityToBeDeleted));
        sqLiteStatement.execute();
    }

    public void readDatabase(Context context) {
        SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        database.execSQL(createSqlTable);
        Cursor cursor = database.rawQuery(strSqlQuery, null);

        int cityNameIx = cursor.getColumnIndex(columnIndexCityName);
        int idIx = cursor.getColumnIndex(columnIndexCityId);

        while (cursor.moveToNext()) {
            System.out.println("Id: " + cursor.getInt(idIx));
            System.out.println("Şehir: " + cursor.getString(cityNameIx));
        }
        cursor.close();
    }

    public boolean databaseControl(String eklenecekSehir ,Context context) {
        boolean sehirVar = false;

        try {
            SQLiteDatabase database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery(strSqlQuery, null);
            int cityNameIx = cursor.getColumnIndex(columnIndexCityName);

            while (cursor.moveToNext()) {
                if (cursor.getString(cityNameIx).matches(eklenecekSehir)) {
                    sehirVar = true;
                }
            }
            cursor.close();

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return sehirVar;
    }
}
