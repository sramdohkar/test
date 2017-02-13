package com.example.group19_hw5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shashank on 07/03/2016.
 */
public class Note implements Parcelable{
    private String id, date, note, citykey; //City key should be city + " " + stateCode

    public Note() {
        id = "";
        date = "";
        note = "";
        citykey = "";
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Note(Parcel in) {
        date = in.readString();
        note = in.readString();
        citykey = in.readString();
        id = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString("date");
        dest.writeString("note");
        dest.writeString("citykey");
        dest.writeString("id");
    }
}
