package com.example.group19_hw5;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shashank on 07/03/2016.
 */
public class CityTable {
    static final String TABLENAME = "cities";
    static final String COLUMN_ID = "id";
    static final String COLUMN_CITY_KEY = "citykey";
    static final String COLUMN_NAME = "cityname";
    static final String COLUMN_STATE = "state";

    public static void onCreate(SQLiteDatabase db) {
        //build SQL statement
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_CITY_KEY + " text not null, ");
        sb.append(COLUMN_NAME + " text not null, ");
        sb.append(COLUMN_STATE + " text not null);");


        //execute it
        try {
            db.execSQL(sb.toString());
        } catch (android.database.SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        NotesTable.onCreate(db);
    }
}

