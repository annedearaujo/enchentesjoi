package com.example.appalertaenchentes.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_REPORTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE_REPORTS)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "flood_reports.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_REPORTS =
            "CREATE TABLE reports ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "location TEXT NOT NULL, " +
                    "severity TEXT NOT NULL, " +
                    "description TEXT, " +
                    "photos BLOB " +
                    ")"

        private const val SQL_DELETE_TABLE_REPORTS =
            "DROP TABLE IF EXISTS reports"
    }

}