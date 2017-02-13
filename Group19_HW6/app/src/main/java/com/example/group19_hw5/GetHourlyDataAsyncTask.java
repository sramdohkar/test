package com.example.group19_hw5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shashank on 07/03/2016.
 */
public class GetHourlyDataAsyncTask extends AsyncTask<String, Void, ArrayList<CityForecast>> {
    ProgressDialog progressDialog;
    Activity act;
    ArrayList<CityForecast> forecast;
    String city, state;
    ListView lv;
    public GetHourlyDataAsyncTask(Activity activity, String ct, String st) {
        progressDialog = new ProgressDialog(activity);
        act = activity;
        city = ct;
        state = st;
        lv = (ListView) activity.findViewById(R.id.listView);
    }

    @Override
    protected ArrayList<CityForecast> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();

                try {
                    return parseForecast(in, state, city);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = new ProgressDialog(HourlyDataActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading hourly data");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<CityForecast> result) {
        super.onPostExecute(result);

        forecast = result;
        //populate listview
        if (forecast.size() == 0) {
            Toast.makeText(act, "City not found", Toast.LENGTH_SHORT).show();
            act.finish();
        } else {
            ForecastAdapter adapter = new ForecastAdapter(act, R.layout.row_item, forecast);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inten = new Intent(act, DetailsActivity.class);
                    inten.putExtra("index", position);
                    Log.d("test", forecast.get(position).toString());
                    inten.putParcelableArrayListExtra("list", forecast);
                    act.startActivity(inten);
                }
            });
        }
        progressDialog.dismiss();

    }

    static ArrayList<CityForecast> parseForecast(InputStream in, String st, String ct) throws XmlPullParserException, IOException {

        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(in, "UTF_8");
        boolean forecastExists = false;
        int tempCount = 0;
        int minTemp = 4000, maxTemp = -4000, temp;
        CityForecast forecast = null;
        ArrayList<CityForecast> forecastList = new ArrayList<CityForecast>();
        int event = parser.getEventType();
        String windTemporary = "";
        String[] windParts;

        while(event != XmlPullParser.END_DOCUMENT) {
            //big switch statement for kind of event
            switch (event) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("hourly_forecast")) {
                        forecastExists = true;
                    } else if (parser.getName().equals("forecast")) {
                        forecast = new CityForecast(ct, st);
                    } else if (parser.getName().equals("civil")) {
                        forecast.setTime(parser.nextText().trim());
                    } else if (parser.getName().equals("temp")) {
                        event = parser.next();
                        event = parser.next();
                        temp = Integer.parseInt(parser.nextText().trim());
                        forecast.setTemperature(temp);
                        if(temp > maxTemp) {
                            maxTemp = temp;
                        }
                        if(temp < minTemp) {
                            minTemp = temp;
                        }
                    } else if (parser.getName().equals("dewpoint")) {
                        event = parser.next();
                        event = parser.next();
                        forecast.setDewPoint(Integer.parseInt(parser.nextText().trim()));
                    } else if (parser.getName().equals("condition")) {
                        forecast.setClouds(parser.nextText().trim());

                    } else if (parser.getName().equals("icon_url")) {
                        forecast.setIconURL(parser.nextText().trim());
                        Log.d("icon", forecast.getIconURL());
                    } else if (parser.getName().equals("wspd")) {
                        event = parser.next();
                        event = parser.next();
                        forecast.setWindSpeed(Integer.parseInt(parser.nextText().trim()));
                    } else if (parser.getName().equals("dir")) {
                        windTemporary = parser.nextText().trim();
                        windTemporary = formatWind(windTemporary.split("(?!^)"));
                    } else if (parser.getName().equals("degrees")) {
                        windTemporary = parser.nextText().trim() + "° " + windTemporary;
                        forecast.setWindDirection(windTemporary);
                    } else if (parser.getName().equals("wx")) {
                        forecast.setClimateType(parser.nextText().trim());
                    } else if (parser.getName().equals("humidity")) {
                        forecast.setHumidity(Integer.parseInt(parser.nextText().trim()));
                    } else if (parser.getName().equals("feelslike")) {
                        event = parser.next();
                        event = parser.next();
                        forecast.setFeelsLike(Integer.parseInt(parser.nextText().trim()));
                    } else if (parser.getName().equals("mslp")) {
                        boolean finished = false;
                        do {
                            event = parser.next();
                            if(event == XmlPullParser.START_TAG && parser.getName().equals("metric")) {
                                forecast.setPressure(Float.parseFloat(parser.nextText().trim()));
                                finished = true;
                            }
                        } while (!finished);
                    }
                    else if (parser.getName().equals("description")) {
                        if (parser.nextText().trim().equals("No cities match your search query")) {
                            return null;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(parser.getName().equals("forecast"))
                        forecastList.add(forecast);
                default:
                    break;
            }

            event = parser.next(); //updates based on kind of tag
        }
        //assign correct max/min temps to all
        if (forecastExists) {
            for(int i = 0 ; i < forecastList.size() ; i++) {
                forecastList.get(i).setMaximumTemp(maxTemp);
                forecastList.get(i).setMinimumTemp(minTemp);
            }
        }

        return forecastList;
    }

    public static String formatWind(String[] windParts) {
        String windString = "";
        for(int i = 0 ; i < windParts.length ; i++) {
            if(windParts[i].equals("N")) {
                windString += "North ";
            } else if(windParts[i].equals("S")) {
                windString += "South ";
            } else if(windParts[i].equals("E")) {
                windString += "East ";
            } else if(windParts[i].equals("W")) {
                windString += "West ";
            }
        }
        return windString;
    }

}