/*
    Assignment: HW5
    File Name:  Group19_HW5.zip
    Group:      Shashank Ramdohkar
    Extra Cred: Image Caching using Picasso
 */

package com.example.group19_hw5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shashank on 07/03/2016.
 */
public class CityList implements Parcelable {
    String id;
    String cityName;
    String stateCode;
    String temperature;


    CityList(String cityName, String temp, String stateCode)
    {
        super();
        this.cityName = cityName;
        this.stateCode = stateCode;
    }
    public CityList() {
        cityName = "";
        stateCode = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityKey() {
        return cityName + " " + stateCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
        dest.writeString(stateCode);
        dest.writeString(id);
    }

    public static final Parcelable.Creator<CityList> CREATOR
            = new Parcelable.Creator<CityList>() {
        public CityList createFromParcel(Parcel in) {
            return new CityList(in);
        }

        public CityList[] newArray(int size) {
            return new CityList[size];
        }
    };

    private CityList(Parcel in) {
        this.cityName = in.readString();
        this.stateCode = in.readString();
        this.id = in.readString();
    }
}