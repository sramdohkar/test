package com.example.group19_hw5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by shashank on 07/03/2016.
 */
public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;
    private CityDAO cityDAO;

    public DatabaseDataManager(Context mContext) {

        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
        cityDAO = new CityDAO(db);
    }

    public void close() {
        if(db != null) {
            db.close();
        }
    }

    public NoteDAO getNoteDAO() {
        return noteDAO;
    }

    //these methods interface with noteDAO
    public long saveNote(Note note) {
        return noteDAO.save(note);
    }

    public boolean deleteNote(Note note) {
        return noteDAO.delete(note);
    }

    public boolean deleteAllNotes() {
        return noteDAO.deleteAll();
    }

    public boolean getNotesByCityDate(String city, String date) {
        return noteDAO.getByCityDate(city, date);
    }

    public List<Note> getAllNotes() {
        return noteDAO.getAllNotes();
    }

    //interface cityDAO
    public CityDAO getCityDAO() {
        return cityDAO;
    }

    public long saveCity(CityList city) {
        return cityDAO.save(city);
    }

    public boolean deleteCity(String cityId) {
        return cityDAO.delete(cityId);
    }

    public boolean deleteAllCity() {
        return cityDAO.deleteAll();
    }

    public List<CityList> getAllCities() {
        return cityDAO.getAll();
    }
}
