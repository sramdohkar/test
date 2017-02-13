/*
    Assignment: HW5
    File Name:  Group19_HW5.zip
    Group:      Shashank Ramdohkar
    Extra Cred: Image Caching using Picasso
 */

package com.example.group19_hw5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shashank on 07/03/2016.
 */
public class CityAdapter extends ArrayAdapter<CityList> {

    List<CityList> mData;
    Context mContext;
    int mResource;

    public CityAdapter(Context context, int resource, List<CityList> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        CityList city = mData.get(position);

        TextView tv = (TextView) convertView.findViewById(R.id.showCity);
        tv.setText(city.cityName + ", " + city.stateCode);

        return convertView;
    }
}