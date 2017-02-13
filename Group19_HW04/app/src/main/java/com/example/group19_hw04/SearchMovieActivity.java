/*
    Assignment HW4
    Group#19_HW04.zip
    Shashank Ramdohkar
 */

package com.example.group19_hw04;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchMovieActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    LinearLayout lLay;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        String movieName = "";
        lLay = (LinearLayout) findViewById(R.id.linLay);
        if (getIntent().getExtras() != null) {
            movieName = getIntent().getExtras().getString("MovieName");
        }
        new GetMoviesAsyncTask().execute("http://www.omdbapi.com/?type=movie&s=" + movieName);
    }

    public class GetMoviesAsyncTask extends AsyncTask<String, Void, ArrayList<Movies>> {
        @Override
        protected ArrayList<Movies> doInBackground(String... params) {

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
                    return MoviesUtil.MoviesJSONParser.parseMovies(sb.toString());
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
            progressDialog = new ProgressDialog(SearchMovieActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.load_list));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            progressDialog.dismiss();
            if (movies != null) {
                Collections.sort(movies, new Comparator<Movies>() {
                    @Override
                    public int compare(Movies lhs, Movies rhs) {
                        return String.valueOf(rhs.year).compareTo(String.valueOf(lhs.year));
                    }
                });
                Log.d("demo", movies.toString());
                for (int i = 0; i < movies.size(); i++) {
                    TextView tv = new TextView(SearchMovieActivity.this);
                    tv.setText(movies.get(i).getTitle() + " (" + movies.get(i).getYear() + ")");
                    tv.setTextSize(20);
                    tv.setTag(movies.get(i));

                    lLay.addView(tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SearchMovieActivity.this, MovieDetailsActivity.class);
                            intent.putExtra("index", movies.indexOf(v.getTag()));
                            intent.putParcelableArrayListExtra("MovieList", movies); //complete movie list
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}
