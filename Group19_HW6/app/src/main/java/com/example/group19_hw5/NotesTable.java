package com.example.group19_hw5;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by shashank on 07/03/2016.
 */
public class NotesTable {

    static final String TABLENAME = "notes";
    static final String COLUMN_ID = "id";
    static final String COLUMN_CITY_KEY = "citykey";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_NOTE = "note";


    static public void onCreate(SQLiteDatabase db) {
        //build SQL statement
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_CITY_KEY + " text not null, ");
        sb.append(COLUMN_DATE + " text not null, ");
        sb.append(COLUMN_NOTE + " text not null);");

        String createStatement =
                String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL);",
                        TABLENAME,
                        COLUMN_ID,
                        COLUMN_CITY_KEY,
                        COLUMN_DATE,
                        COLUMN_NOTE);

        //execute it
        try {

            db.execSQL(sb.toString());
            Log.d("demo", "Notes Table Created");
        } catch (android.database.SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        NotesTable.onCreate(db);
    }
}
