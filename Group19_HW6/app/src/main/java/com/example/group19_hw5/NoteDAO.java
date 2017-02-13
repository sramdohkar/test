package com.example.group19_hw5;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashank on 07/03/2016.
 */
public class NoteDAO {
    private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note) {
        //used to specify content cleanly
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_CITY_KEY, note.getCitykey());
        values.put(NotesTable.COLUMN_DATE, note.getDate());
        values.put(NotesTable.COLUMN_NOTE, note.getNote());

        return db.insert(NotesTable.TABLENAME, null, values);
    }

    public boolean delete(Note note) {
        return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_CITY_KEY + "=? AND " + NotesTable.COLUMN_DATE + "=?", new String[]{note.getCitykey(), note.getDate()}) != -1;
        //if confused, see comments for update above
    }

    public boolean deleteAll(){
        return db.delete(NotesTable.TABLENAME, null, null) > 0;
    }

    public boolean getByCityDate(String city, String date) {
        //Note note = null;
        boolean exists = false;
        //first argument distinct, name, listing of columns, where, replace ? in where, groupBy
        //having, orderBy, limit, cancellation
        Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_CITY_KEY,  NotesTable.COLUMN_DATE}
        , NotesTable.COLUMN_CITY_KEY + "=? AND " + NotesTable.COLUMN_DATE + "=?", new String[]{city, date}, null, null, null, null);

        //need to use cursor to make note which we do be calling this function
        //move to first makes sure you have a cursor (??) may not work in other scenarios??
        if(c != null && c.moveToFirst()) {
            //note = buildNoteFromCursor1(c);
            exists = true;
            if(!c.isClosed()) {
                c.close();
            }
        }

        return exists;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor c = db.query(NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_ID, NotesTable.COLUMN_CITY_KEY, NotesTable.COLUMN_DATE, NotesTable.COLUMN_NOTE},
                null, null, null, null, null);

        if(c != null && c.moveToFirst()) {
            do {
                Note note = buildNoteFromCursor(c);
                if(note != null) {
                    notes.add(note);
                }
            } while(c.moveToNext());

            if(!c.isClosed()) {
                c.close();
            }
        }

        return notes;
    }

    private Note buildNoteFromCursor(Cursor c) {
        Note note = null;
        if(c != null) {
            note = new Note();
            //columns called in their order
            note.setId(String.valueOf(c.getLong(0)));
            note.setCitykey(c.getString(1));
            note.setDate(c.getString(2));
            note.setNote(c.getString(3));
        }

        return note;
    }

    private Note buildNoteFromCursor1(Cursor c) {
        Note note = null;
        if(c != null) {
            note = new Note();
            //columns called in their order
            note.setCitykey(c.getString(0));
            note.setDate(c.getString(1));
        }

        return note;
    }
}
