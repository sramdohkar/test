package com.example.group19_hw5;

import android.content.Context;
import android.util.Log;
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
public class ForecastAdapter extends ArrayAdapter<CityForecast> {

    List<CityForecast> mData;
    Context mContext;
    int mResource;

    public ForecastAdapter(Context context, int resource, List<CityForecast> objects) {
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

        CityForecast forecast = mData.get(position);

        TextView timeTV = (TextView) convertView.findViewById(R.id.textViewTime);
        TextView tempTV = (TextView) convertView.findViewById(R.id.textViewTemp);
        ImageView thumb = (ImageView) convertView.findViewById(R.id.imageViewThumb);

        timeTV.setText(forecast.getTime() + "\n" + forecast.getClimateType());
        tempTV.setText(forecast.getTemperature() + "ºF");

        Log.d("test", forecast.getIconURL());
        Picasso.with(mContext).load(forecast.getIconURL()).into(thumb);

        return convertView;
    }
}
