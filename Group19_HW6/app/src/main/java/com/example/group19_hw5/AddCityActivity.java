/*
    Assignment: HW5
    File Name:  Group19_HW5.zip
    Group:      Shashank Ramdohkar
    Extra Cred: Image Caching using Picasso
 */

package com.example.group19_hw5;
/**
 * Created by shashank on 07/03/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AddCityActivity extends AppCompatActivity {
    HashMap<String, String> hmap = new HashMap<String, String>();
    String cityName = "";
    String stateCode = "";
    String key = "1b38864edb9165f5";
    ArrayList<CityList> cityList = new ArrayList<CityList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        if(getIntent().getExtras() != null) {
            ArrayList<CityList> cityList1 = (getIntent().getExtras().getParcelableArrayList("CityList"));
            for(int i = 0; i < cityList1.size(); i++) {
                cityList.add(cityList1.get(i));
            }
        }

        Button btnSaveCity = (Button)findViewById(R.id.btnSaveCity);
        final EditText edtCity = (EditText)findViewById(R.id.edtCityName);
        final EditText edtState = (EditText)findViewById(R.id.edtStateCode);
        /*Adding elements to HashMap*/
        hmap.put("AL", "Alabama"); hmap.put("AK", "Alaska"); hmap.put("AZ", "Arizona"); hmap.put("AR", "Arkansas"); hmap.put("CA", "California");
        hmap.put("CO", "Colorado"); hmap.put("CT", "Connecticut"); hmap.put("DE", "Delaware"); hmap.put("DC", "District of Columbia"); hmap.put("FL", "Florida");
        hmap.put("GA", "Georgia"); hmap.put("HI", "Hawaii"); hmap.put("ID", "Idaho"); hmap.put("IL", "Illinois"); hmap.put("IN", "Indiana");
        hmap.put("IA", "Iowa"); hmap.put("KS", "Kansas"); hmap.put("KY", "Kentucky"); hmap.put("LA", "Louisiana"); hmap.put("ME", "Maine");
        hmap.put("MD", "Maryland"); hmap.put("MA", "Massachusetts"); hmap.put("MI", "Michigan"); hmap.put("MN", "Minnesota"); hmap.put("MO", "Missouri");
        hmap.put("MT", "Montana"); hmap.put("NE", "Nebraska"); hmap.put("NV", "Nevada"); hmap.put("NH", "New Hampshire"); hmap.put("NJ", "New Jersey");
        hmap.put("NM", "New Mexico"); hmap.put("NY", "New York"); hmap.put("NC", "North Carolina"); hmap.put("ND", "North Dakota"); hmap.put("OH", "Ohio");
        hmap.put("OK", "Oklahoma"); hmap.put("OR", "Oregon"); hmap.put("PA", "Pennsylvania"); hmap.put("RI", "Rhode Island"); hmap.put("SC", "South Carolina");
        hmap.put("SD", "South Dakota"); hmap.put("TN", "Tennessee"); hmap.put("TX", "Texas"); hmap.put("UT", "Utah"); hmap.put("VT", "Vermont");
        hmap.put("VA", "Virginia"); hmap.put("WA", "Washington"); hmap.put("WV", "West Virginia"); hmap.put("WI", "Wisconsin"); hmap.put("WY", "Wyoming");
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        btnSaveCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCity.getText().toString().trim().matches("") || edtState.getText().toString().trim().matches(""))
                {
                    Toast toast = Toast.makeText(context, "All the fields are mandatory", duration);
                    toast.show();
                }
                else
                {
                    if(hmap.containsKey(edtState.getText().toString().trim().toUpperCase())){
                        cityName = edtCity.getText().toString().trim();
                        stateCode = edtState.getText().toString().trim().toUpperCase();

                        String url = "http://gomashup.com/json.php?fds=geo/usa/zipcode/state/" + stateCode +
                                "&jsoncallback=";


//                        String url = "http://api.wunderground.com/api/" + key + "/conditions/q/" + stateCode + "/"
//                                + edtCity.getText().toString().replace(" ", "_") + ".xml";
                        new GetCityAsyncTask().execute(url);
                        //new CityUtil(AddCityActivity.this, edtCity.getText().toString(), stateCode).execute(url);
                    }
                    else
                    {
                        Toast toast = Toast.makeText(context, "Enter valid state name", duration);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public class GetCityAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        sb.append(line);
                        line = reader.readLine();
                    }
                    //Log.d("demo", sb.toString());
                    return CityUtil.ValidCityJSONParser.parseCity(sb.toString());

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final ArrayList<String> result) {
            super.onPostExecute(result);
            CityList cityDetails = new CityList();
            Set<String> set = new HashSet<String>(result);
            if (set.contains(cityName.toLowerCase()))
            {
                DatabaseDataManager dm = new DatabaseDataManager(AddCityActivity.this);

                cityDetails.cityName = cityName;
                cityDetails.stateCode = stateCode;
                //cityList.add(cityDetails);
                dm.saveCity(cityDetails);
                Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                //intent.putParcelableArrayListExtra("CityList", cityList);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast toast = Toast.makeText(AddCityActivity.this, "Enter valid city name", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}