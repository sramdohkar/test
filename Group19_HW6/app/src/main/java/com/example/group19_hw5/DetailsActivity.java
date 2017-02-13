/**
 * Created by shashank on 07/03/2016.
 */

package com.example.group19_hw5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ArrayList<CityForecast> forecast;
    TextView txtCurrentLocation;
    TextView txtTemp;
    TextView txtWeather;
    TextView txtMaxTemp;
    TextView txtMinTemp;
    TextView txtFeelsLike;
    TextView txtHumidity;
    TextView txtDewPoint;
    TextView txtPressure;
    TextView txtClouds;
    TextView txtWinds;
    ImageView iv;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        forecast = getIntent().getParcelableArrayListExtra("list");
        i = getIntent().getIntExtra("index", 0);

//        if(getIntent().getExtras() != null) {
//            forecast = getIntent().getExtras().getParcelableArrayList("CityForecast");
//        }

        txtCurrentLocation = (TextView) findViewById(R.id.txtCurrentLocation);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtWeather = (TextView) findViewById(R.id.txtWeather);
        txtMaxTemp = (TextView) findViewById(R.id.txtMaxTemp);
        txtMinTemp = (TextView) findViewById(R.id.txtMinTemp);
        txtFeelsLike = (TextView) findViewById(R.id.txtFeelsLike);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtDewPoint = (TextView) findViewById(R.id.txtDewPoint);
        txtPressure = (TextView) findViewById(R.id.txtPressure);
        txtClouds = (TextView) findViewById(R.id.txtClouds);
        txtWinds = (TextView) findViewById(R.id.txtWinds);
        iv = (ImageView) findViewById(R.id.iv);
        final Button btnLeft = (Button) findViewById(R.id.btnLeft);
        final Button btnRight = (Button) findViewById(R.id.btnRight);
        insertValues(i);
        if(forecast.size() <= 1)
        {
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
        }
        else
        {
            if(i == forecast.size() -1) {
                btnLeft.setEnabled(true);
                btnRight.setEnabled(false);
            } else if(i == 0) {
                btnLeft.setEnabled(false);
                btnRight.setEnabled(true);
            } else {
                btnLeft.setEnabled(true);
                btnRight.setEnabled(true);
            }

        }
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if(i == 0)
                {
                    btnLeft.setEnabled(false);
                    btnRight.setEnabled(true);
                }
                else
                {
                    btnLeft.setEnabled(true);
                    btnRight.setEnabled(true);
                }
                insertValues(i);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i == forecast.size() - 1)
                {
                    btnLeft.setEnabled(true);
                    btnRight.setEnabled(false);
                }
                else
                {
                    btnLeft.setEnabled(true);
                    btnRight.setEnabled(true);
                }
                insertValues(i);
            }
        });
    }
    void insertValues(int index)
    {
        txtCurrentLocation.setText("Current Location " + forecast.get(index).getName() + ", "
                + forecast.get(index).getState() + " " + forecast.get(index).getTime());
        txtTemp.setText(String.valueOf(forecast.get(index).getTemperature()) + " ºF");
        txtWeather.setText(forecast.get(index).getClimateType());
        txtMaxTemp.setText("Max Temperature: " + String.valueOf(forecast.get(index).getMaximumTemp()) + " Fahrenheit");
        txtMinTemp.setText("Min Temperature: " + String.valueOf(forecast.get(index).getMinimumTemp()) + " Fahrenheit");
        txtFeelsLike.setText("Feels Like: " + String.valueOf(forecast.get(index).getFeelsLike()) + " Fahrenheit");
        txtHumidity.setText("Humidity: " + String.valueOf(forecast.get(index).getHumidity()) + "%");
        txtDewPoint.setText("Dewpoint: " + String.valueOf(forecast.get(index).getDewPoint()) + " Fahrenheit");
        txtPressure.setText("Pressure: " + String.valueOf(forecast.get(index).getPressure()) + " hPa");
        txtClouds.setText("Clouds: " + forecast.get(index).getClouds());
        txtWinds.setText("Winds: " + String.valueOf(forecast.get(index).getWindSpeed()) + " mph, "
                + forecast.get(index).getWindDirection());
        if(forecast.get(index).getIconURL() != null || !forecast.get(index).getIconURL().equals("")) {
            Picasso.with(DetailsActivity.this).load(forecast.get(index).getIconURL()).into(iv);
        }
    }
}