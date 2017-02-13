package com.example.group19_hw5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by shashank on 07/03/2016.
 */
public class HourlyDataActivity extends Activity {

    ProgressDialog progressDialog;
    ArrayList<CityForecast> forecast;
    String city, state, url, key = "1b38864edb9165f5";
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_data);
        lv = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        Log.d("test", "got to before get parcelable");
        CityList c = intent.getParcelableExtra("CityState");
        Log.d("test", "got parcelable");
        city = c.cityName;
        Log.d("test", "got city " + city);
        state = c.stateCode;
        Log.d("test", "got state " + state);
        url = "http://api.wunderground.com/api/" + key + "/hourly/q/" + state + "/" + city.replace(" ", "_") + ".xml";
        //new GetWeatherAsyncTask().execute(url);
        new GetHourlyDataAsyncTask(HourlyDataActivity.this, city, state).execute(url);
        TextView currentLoc = (TextView) findViewById(R.id.textViewCurrentLoc);
        currentLoc.setText(getString(R.string.current_location) + city + ", " + state);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HourlyDataActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
