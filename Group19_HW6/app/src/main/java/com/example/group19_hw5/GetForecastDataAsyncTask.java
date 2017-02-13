package com.example.group19_hw5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
public class GetForecastDataAsyncTask extends AsyncTask<String, Void, ArrayList<DayForecast>> {
    ProgressDialog progressDialog;
    Activity act;
    ArrayList<DayForecast> forecast;
    String city, state;
    ListView lv;
    Note deleteNote;
    DatabaseDataManager dm;
    CityList c;
    public GetForecastDataAsyncTask(Activity activity, String ct, String st, CityList cl) {
        deleteNote = new Note();
        progressDialog = new ProgressDialog(activity);
        act = activity;
        city = ct;
        state = st;
        lv = (ListView) activity.findViewById(R.id.listView);
        dm = new DatabaseDataManager(activity);
        c = cl;
    }

    @Override
    protected ArrayList<DayForecast> doInBackground(String... params) {

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
        progressDialog = new ProgressDialog(act);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading forecast data");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<DayForecast> result) {
        super.onPostExecute(result);

        forecast = result;
        //populate listview
        if (forecast.size() == 0) {
            Toast.makeText(act, "City not found", Toast.LENGTH_SHORT).show();
            act.finish();
        } else {
            DayForecastAdapter adapter = new DayForecastAdapter(act, R.layout.row_item3, forecast);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent notesIntent = new Intent(act, AddNotesActivity.class);
                    notesIntent.putExtra("city", forecast.get(position).getName());
                    notesIntent.putExtra("state", forecast.get(position).getState());
                    notesIntent.putExtra("day", forecast.get(position).getDay());
                    notesIntent.putExtra("month", forecast.get(position).getMonthname());
                    notesIntent.putExtra("CityState", c);
                    //notesIntent.putExtra("CityState", c);
                    act.startActivity(notesIntent);
                    act.finish();
                }
            });

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long arg3) {

                    boolean check;

                    deleteNote.setCitykey(forecast.get(position).getName());
                    deleteNote.setDate(String.valueOf(forecast.get(position).getDay()) + " " + forecast.get(position).getMonthname());
                    check = dm.getNotesByCityDate(forecast.get(position).getName(), String.valueOf(forecast.get(position).getDay()) + " " + forecast.get(position).getMonthname());

                    if(check) {
                        dm.deleteNote(deleteNote);
                        Toast.makeText(act, "Note deleted successfully", Toast.LENGTH_SHORT).show();

                        Intent deleteIntent = new Intent(act, AndroidTabLayoutActivity.class);
                        deleteIntent.putExtra("noteadded", "noteadded");
                        deleteIntent.putExtra("CityState", c);
                        act.startActivity(deleteIntent);
                        act.finish();
                    }
                    return false;
                }

            });
        }
        progressDialog.dismiss();
    }

    static ArrayList<DayForecast> parseForecast(InputStream in, String st, String ct) throws XmlPullParserException, IOException {

        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(in, "UTF_8");
        boolean forecastExists = false;
        DayForecast forecastt = null;
        ArrayList<DayForecast> forecastList = new ArrayList<DayForecast>();
        int event = parser.getEventType();
        String windTemporary = "";
        String[] windParts;
        while(event != XmlPullParser.END_DOCUMENT) {
            //big switch statement for kind of event
            switch (event) {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("simpleforecast")) {
                        forecastExists = true;
                    } else if (parser.getName().equals("date")) {
                        forecastt = new DayForecast(ct, st);
                    } else if (parser.getName().equals("day")) {

                        forecastt.setDay(Integer.parseInt(parser.nextText().trim()));
                    }else if (parser.getName().equals("period")) {
                    }
                    else if (parser.getName().equals("monthname")) {
                        forecastt.setMonthname(parser.nextText().trim());
                    } else if (parser.getName().equals("high")) {
                        event = parser.next();
                        event = parser.next();
                        forecastt.setMaximumTemp(Integer.parseInt(parser.nextText().trim()));
                    } else if (parser.getName().equals("low")) {
                        event = parser.next();
                        event = parser.next();
                        forecastt.setMinimumTemp(Integer.parseInt(parser.nextText().trim()));
                    }else if (parser.getName().equals("conditions")) {
                        forecastt.setClimateType(parser.nextText().trim());

                    } else if (parser.getName().equals("icon_url")) {
                        forecastt.setIconURL(parser.nextText().trim());
                    } else if (parser.getName().equals("maxwind")) {
                        event = parser.next();
                        event = parser.next();
                        forecastt.setWindSpeed(Integer.parseInt(parser.nextText().trim()));
                    } else if (parser.getName().equals("dir")) {
                        windTemporary = parser.nextText().trim();
                        if(!windTemporary.toLowerCase().contains("east") || !windTemporary.toLowerCase().contains("west") ||
                                !windTemporary.toLowerCase().contains("north") || !windTemporary.toLowerCase().contains("south") ||
                                !windTemporary.trim().equals(""))
                            windTemporary = formatWind(windTemporary.split("(?!^)"));
                    } else if (parser.getName().equals("degrees")) {
                        windTemporary = parser.nextText().trim() + "° " + windTemporary;
                        forecastt.setWindDirection(windTemporary);
                    } else if (parser.getName().equals("avehumidity")) {
                        forecastt.setHumidity(Integer.parseInt(parser.nextText().trim()));
                    }else if (parser.getName().equals("description")) {
                        if (parser.nextText().trim().equals("No cities match your search query")) {
                            return null;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(parser.getName().equals("forecastday")) {
                        if(forecastt.getDay() != -500) {
                            forecastList.add(forecastt);
                        }
                        //forecast = null;
                    }
                    break;
                default:
                    break;
            }

            event = parser.next(); //updates based on kind of tag
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