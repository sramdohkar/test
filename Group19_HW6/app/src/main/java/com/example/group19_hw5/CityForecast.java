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
public class CityForecast implements Parcelable{
    private String name, state, time, clouds, iconURL, windDirection, climateType;
    private int temperature, dewPoint, windSpeed, humidity, feelsLike, maximumTemp, minimumTemp;
    private float pressure;

    public CityForecast (String name, String state) {
        this.name = name;
        this.state = state;
        time = "";
        clouds = "";
        iconURL = "";
        windDirection = "";
        climateType = "";

        //arbitrary initializations
        temperature =  -500;
        dewPoint =  -500;
        windSpeed =  -500;
        humidity =  -500; 
        feelsLike =  -500;
        maximumTemp =  -500;
        minimumTemp =  -500;
        pressure =  -500;
    }


    //parcelable
    protected CityForecast(Parcel in) {
        name = in.readString();
        state = in.readString();
        time = in.readString();
        clouds = in.readString();
        iconURL = in.readString();
        windDirection = in.readString();
        climateType = in.readString();
        temperature = in.readInt();
        dewPoint = in.readInt();
        windSpeed = in.readInt();
        humidity = in.readInt();
        feelsLike = in.readInt();
        maximumTemp = in.readInt();
        minimumTemp = in.readInt();
        pressure = in.readFloat();
    }

    public static final Creator<CityForecast> CREATOR = new Creator<CityForecast>() {
        @Override
        public CityForecast createFromParcel(Parcel in) {
            return new CityForecast(in);
        }

        @Override
        public CityForecast[] newArray(int size) {
            return new CityForecast[size];
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
        dest.writeString(time);
        dest.writeString(clouds);
        dest.writeString(iconURL);
        dest.writeString(windDirection);
        dest.writeString(climateType);
        dest.writeInt(temperature);
        dest.writeInt(dewPoint);
        dest.writeInt(windSpeed);
        dest.writeInt(humidity);
        dest.writeInt(feelsLike);
        dest.writeInt(maximumTemp);
        dest.writeInt(minimumTemp);
        dest.writeFloat(pressure);
    }

    @Override
    public String toString() {
        return "CityForecast{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", time='" + time + '\'' +
                ", clouds='" + clouds + '\'' +
                ", iconURL='" + iconURL + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", climateType='" + climateType + '\'' +
                ", temperature=" + temperature +
                ", dewPoint=" + dewPoint +
                ", windSpeed=" + windSpeed +
                ", humidity=" + humidity +
                ", feelsLike=" + feelsLike +
                ", maximumTemp=" + maximumTemp +
                ", minimumTemp=" + minimumTemp +
                ", pressure=" + pressure +
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getWindDirection() {
        return windDirection;
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

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(int dewPoint) {
        this.dewPoint = dewPoint;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
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

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
}
