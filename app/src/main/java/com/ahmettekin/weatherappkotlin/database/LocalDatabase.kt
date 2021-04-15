package com.ahmettekin.weatherappkotlin.database

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast

object LocalDatabase {
    private const val strSqlQuery = "SELECT * FROM cities"
    private const val databaseName = "Cities"
    private const val createSqlTable = "CREATE TABLE IF NOT EXISTS cities(id INTEGER, cityname VARCHAR)"
    private const val insertSqlTable = "INSERT INTO cities(id, cityname) VALUES (?, ?)"
    private const val columnIndexCityName = "cityname"
    private const val columnIndexCityId = "id"
    private const val deleteSqlRecord = "DELETE FROM cities WHERE id=?"
    private const val alreadyExistSqlWarningText = "Şehir veritabanında zaten var"
    private const val cityAddedText = " şehri başarılı bir şekilde eklendi"

    internal fun getCityIdFromDatabase(context: Context): String {
        val database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        database.execSQL(createSqlTable)
        val groupIds = StringBuilder("")
        val cursor = database.rawQuery(strSqlQuery, null)
        val idIx = cursor.getColumnIndex(columnIndexCityId)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(idIx)
            groupIds.append(id)
            groupIds.append(",")
        }
        if (groupIds.lastIndexOf(",") >= 0) {
            groupIds.deleteCharAt(groupIds.lastIndexOf(","))
        }
        cursor.close()
        return groupIds.toString()

    }

    internal fun writeData(cityName: String, eklenecekId: Int, context: Context) {
        if (!databaseControl(cityName, context)) {
            val database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
            database.execSQL(createSqlTable)
            val sqLiteStatement = database.compileStatement(insertSqlTable)
            sqLiteStatement.bindString(1, eklenecekId.toString())
            sqLiteStatement.bindString(2, cityName)
            sqLiteStatement.execute()
            Toast.makeText(context, cityName + cityAddedText, Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, alreadyExistSqlWarningText, Toast.LENGTH_SHORT).show()
        }
    }

    internal fun deleteCityFromDatabase(context: Context, cityIdToBeDeleted: Int?) {
        val database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        val sqLiteStatement = database.compileStatement(deleteSqlRecord)
        sqLiteStatement.bindString(1, cityIdToBeDeleted.toString())
        sqLiteStatement.execute()
    }

    internal fun deleteAllRecords(context: Context){
        val database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        database.execSQL("DELETE FROM cities")
    }

    private fun databaseControl(eklenecekSehir: String, context: Context): Boolean {
        var sehirVar = false;
        try {
            val database = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
            val cursor = database.rawQuery(strSqlQuery, null)
            val cityNameIx = cursor.getColumnIndex(columnIndexCityName)

            while (cursor.moveToNext()) {
                if (cursor.getString(cityNameIx).equals(eklenecekSehir)) {
                    sehirVar = true
                }
            }
            cursor.close()

        } catch (e: Exception) {
            println(e.localizedMessage)
        }
        return sehirVar
    }
}