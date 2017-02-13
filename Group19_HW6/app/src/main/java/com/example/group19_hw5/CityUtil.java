/*
    Assignment: HW5
    File Name:  Group19_HW5.zip
    Group:      Shashank Ramdohkar
    Extra Cred: Image Caching using Picasso
 */

package com.example.group19_hw5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shashank on 07/03/2016.
 */
public class CityUtil {
    static public class ValidCityJSONParser{
        static ArrayList<String> parseCity(String in) throws JSONException {
            ArrayList<String> cityList = new ArrayList<String>();
            JSONObject root = new JSONObject(in.substring(1, in.length()-1));
            JSONArray validCityJSONArray = root.getJSONArray("result");
            for(int i = 0 ; i < validCityJSONArray.length(); i++)
            {
                JSONObject js = validCityJSONArray.getJSONObject(i);
                cityList.add(js.getString("City").toLowerCase());
            }
            return cityList;
        }
    }
}
