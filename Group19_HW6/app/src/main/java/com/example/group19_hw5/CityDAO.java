package com.example.group19_hw5;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashank on 07/03/2016.
 */
public class CityDAO {

    private SQLiteDatabase db;

    public CityDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(CityList city) {
        //used to specify content cleanly
        ContentValues values = new ContentValues();
        values.put(CityTable.COLUMN_CITY_KEY, city.getCityKey());
        values.put(CityTable.COLUMN_NAME, city.cityName);
        values.put(CityTable.COLUMN_STATE, city.stateCode);

        return db.insert(CityTable.TABLENAME, null, values);
    }

    public boolean delete(String cityId){
        return db.delete(CityTable.TABLENAME, CityTable.COLUMN_ID + "=?", new String[]{cityId + ""}) > 0;
    }

    public boolean deleteAll(){
        return db.delete(CityTable.TABLENAME, null, null) > 0;
    }

    public List<CityList> getAll() {
        List<CityList> cities = new ArrayList<CityList>();
        Cursor c = db.query(CityTable.TABLENAME, new String[]{CityTable.COLUMN_ID, CityTable.COLUMN_CITY_KEY,  CityTable.COLUMN_NAME, CityTable.COLUMN_STATE},
                null, null, null, null, null);
        if(c != null && c.moveToFirst()) {
            do {
                CityList city = buildFromCursor(c);
                if(city != null) {
                    cities.add(city);
                }
            } while(c.moveToNext());

            if(!c.isClosed()) {
                c.close();
            }
        }

        return cities;


    }

    private CityList buildFromCursor(Cursor c) {
        CityList city = null;
        if(c != null) {
            city = new CityList();
            //columns called in their order

            city.id = String.valueOf(c.getLong(0));
            city.cityName = c.getString(2);
            city.stateCode = c.getString(3);
        }

        return city;
    }
}
