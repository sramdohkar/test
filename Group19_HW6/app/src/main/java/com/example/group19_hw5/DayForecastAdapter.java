package com.example.group19_hw5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shashank on 07/03/2016.
 */
public class DayForecastAdapter extends ArrayAdapter<DayForecast> {


    List<DayForecast> mData;
    Context mContext;
    int mResource;
    boolean check;
    public DayForecastAdapter(Context context, int resource, List<DayForecast> objects) {
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
        DatabaseDataManager dm;
        DayForecast forecast = mData.get(position);
        dm = new DatabaseDataManager(mContext);
        check = dm.getNotesByCityDate(forecast.getName(), String.valueOf(forecast.getDay()) + " " + forecast.getMonthname());
        TextView timeTV = (TextView) convertView.findViewById(R.id.textViewTime5);
        TextView tempTV = (TextView) convertView.findViewById(R.id.textViewTemp5);
        ImageView thumb = (ImageView) convertView.findViewById(R.id.imageViewThumb5);
        ImageView thumb1 = (ImageView) convertView.findViewById(R.id.imageViewThumb6);

        String day = "";
        if(String.valueOf(forecast.getDay()).length() == 1)
        {
            day = "0" + String.valueOf(forecast.getDay());
        }
        else
        {
            day = String.valueOf(forecast.getDay());
        }
        timeTV.setText(day + " " + forecast.getMonthname() + "\n" + forecast.getClimateType());
        tempTV.setText(forecast.getMaximumTemp() + "ºF" + "/" + forecast.getMinimumTemp() + "ºF");

        if(check){
            thumb1.setVisibility(View.VISIBLE);
            thumb1.setImageResource(R.drawable.notesicon);
        }

        else
        {
            thumb1.setVisibility(View.INVISIBLE);
        }
        Picasso.with(mContext).load(forecast.getIconURL()).into(thumb);

        return convertView;
    }
}