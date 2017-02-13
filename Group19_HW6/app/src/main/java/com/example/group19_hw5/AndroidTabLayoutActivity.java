package com.example.group19_hw5;
/**
 * Created by shashank on 07/03/2016.
 */
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class AndroidTabLayoutActivity extends TabActivity {
    /** Called when the activity is first created. */
    String activityType = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tab_layout);

        Intent intent = getIntent();
        CityList c = intent.getParcelableExtra("CityState");

        TabHost tabHost = getTabHost();

        // Tab for Hourly Data
        TabHost.TabSpec hourlyData = tabHost.newTabSpec("HOURLY DATA");
        // setting Title and Icon for the Tab
        hourlyData.setIndicator("HOURLY DATA");
        Intent hourlyDataIntent = new Intent(this, HourlyDataActivity.class);
        hourlyDataIntent.putExtra("CityState", c);
        hourlyData.setContent(hourlyDataIntent);

        // Tab for Forecast
        TabHost.TabSpec forecast = tabHost.newTabSpec("FORECAST");
        forecast.setIndicator("FORECAST");
        Intent forecastIntent = new Intent(this, ForecastActivity.class);
        forecastIntent.putExtra("CityState", c);
        forecast.setContent(forecastIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(hourlyData); // Adding Hourly data tab
        tabHost.addTab(forecast); // Adding forecast tab

        if(getIntent().getStringExtra("noteadded") != null)
            tabHost.setCurrentTab(1);
        else
            tabHost.setCurrentTab(0);
    }
}
