package com.example.group19_hw5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shashank on 07/03/2016.
 */
public class DayForecast implements Parcelable {
    private String name, state, monthname, iconURL, windDirection, climateType;
    private int windSpeed, humidity, maximumTemp, minimumTemp, day;

    public DayForecast (String name, String state) {
        this.name = name;
        this.state = state;
        monthname = "";
        iconURL = "";
        windDirection = "";
        climateType = "";

        //arbitrary initializations
        windSpeed =  -500;
        humidity =  -500;
        maximumTemp =  -500;
        minimumTemp =  -500;
        day =  -500;
    }


    //parcelable
    protected DayForecast(Parcel in) {
        name = in.readString();
        state = in.readString();
        monthname = in.readString();
        iconURL = in.readString();
        windDirection = in.readString();
        climateType = in.readString();
        windSpeed = in.readInt();
        humidity = in.readInt();
        maximumTemp = in.readInt();
        minimumTemp = in.readInt();
        day = in.readInt();
    }

    public static final Creator<DayForecast> CREATOR = new Creator<DayForecast>() {
        @Override
        public DayForecast createFromParcel(Parcel in) {
            return new DayForecast(in);
        }

        @Override
        public DayForecast[] newArray(int size) {
            return new DayForecast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(state);
        dest.writeString(monthname);
        dest.writeString(iconURL);
        dest.writeString(windDirection);
        dest.writeString(climateType);
        dest.writeInt(windSpeed);
        dest.writeInt(humidity);
        dest.writeInt(maximumTemp);
        dest.writeInt(minimumTemp);
        dest.writeInt(day);
    }

    @Override
    public String toString() {
        return "DayForecast{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", monthname='" + monthname + '\'' +
                ", iconURL='" + iconURL + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", climateType='" + climateType + '\'' +
                ", windSpeed=" + windSpeed +
                ", humidity=" + humidity +
                ", maximumTemp=" + maximumTemp +
                ", minimumTemp=" + minimumTemp +
                ", day=" + day +
                '}';
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMonthname() {
        return monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getMaximumTemp() {
        return maximumTemp;
    }

    public void setMaximumTemp(int maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public int getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(int minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}