package com.example.group19_hw5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by shashank on 07/03/2016.
 */
public class ForecastActivity extends Activity {

    ProgressDialog progressDialog;
    ArrayList<DayForecast> forecast;
    String city, state, url, key = "1b38864edb9165f5";
    ListView lv;
    CityList c;
    DatabaseDataManager dm;
    Note deleteNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        deleteNote = new Note();
        dm = new DatabaseDataManager(ForecastActivity.this);
        lv = (ListView) findViewById(R.id.listView);
        final Intent intent = getIntent();
        c = intent.getParcelableExtra("CityState");
        city = c.cityName;
        state = c.stateCode;
        //http://api.wunderground.com/api/1b38864edb9165f5/forecast10day/q/CA/San_Francisco.json
        url = "http://api.wunderground.com/api/" + key + "/forecast10day/q/" + state + "/" + city.replace(" ", "_") + ".xml";
        new GetForecastDataAsyncTask(ForecastActivity.this, city, state, c).execute(url);
        //new GetForecastAsyncTask().execute(url);
        TextView currentLoc = (TextView) findViewById(R.id.textViewCurrentLoc);
        currentLoc.setText(getString(R.string.current_location) + city + ", " + state);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForecastActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
