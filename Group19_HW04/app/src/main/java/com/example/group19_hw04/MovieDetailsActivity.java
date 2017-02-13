/*
    Assignment HW4
    Group#19_HW04.zip
    Shashank Ramdohkar
 */

package com.example.group19_hw04;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetailsActivity extends AppCompatActivity {

    int index, loadCounter; //loadCounter = 2 when complete
    Movies movie;
    ArrayList<Movies> moviesList;

    TextView tvTitle, tvRelease, tvGenre, tvDirector, tvActors, tvPlot;
    RatingBar rb;
    ImageView iv;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        moviesList = intent.getParcelableArrayListExtra("MovieList");

        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvRelease = (TextView) findViewById(R.id.textViewRelease);
        tvGenre = (TextView) findViewById(R.id.textViewGenre);
        tvDirector = (TextView) findViewById(R.id.textViewDirector);
        tvActors = (TextView) findViewById(R.id.textViewActors);
        tvPlot = (TextView) findViewById(R.id.textViewPlot);
        rb = (RatingBar) findViewById(R.id.ratingBar);
        iv = (ImageView) findViewById(R.id.imageViewCover);
        progressDialog = new ProgressDialog(MovieDetailsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.load_movie));

        buildView();

        findViewById(R.id.imageViewPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < 1) {
                    index = moviesList.size() - 1;
                } else {
                    index--;
                }

                buildView();
            }
        });

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.imageViewNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index > moviesList.size() - 2) {
                    index = 0;
                } else {
                    index++;
                }

                buildView();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MovieDetailsActivity.this, MovieWebviewActivity.class);
                intent1.putExtra("id", movie.getImdbID());
                startActivity(intent1);
            }
        });
    }

    private void buildView() {
        loadCounter = 0;
        progressDialog.show();
        movie = moviesList.get(index);
        if(movie != null && movie.getPlot().equals("")) {
            new GetMovieAsyncTask().execute("http://www.omdbapi.com/?i=" + movie.getImdbID());
        } else {
            loadCounter++;
            fillText();
        }
        if(movie != null && movie.getPoster()!=null)
            new GetImage().execute(movie.getPoster());
        else
            progressDialog.dismiss();
    }

    private void fillText() {
        if(movie != null) {
            if (movie.getTitle() != null)
                tvTitle.setText(movie.getTitle());
            if (movie.getReleased() != null)
                tvRelease.setText(formatDate(movie.getReleased()));
            if (movie.getGenre() != null)
                tvGenre.setText(movie.getGenre());
            if (movie.getDirector() != null)
                tvDirector.setText(movie.getDirector());
            if (movie.getActors() != null)
                tvActors.setText(movie.getActors());
            if (movie.getPlot() != null)
                tvPlot.setText(movie.getPlot());
            if (movie.getImdbRating() != -1)
                rb.setRating(Float.parseFloat(Double.toString(movie.getImdbRating() / 2)));
        } else {
            fillNotFound();
        }

    }

    private void fillNotFound() {
        tvTitle.setText(getString(R.string.not_found));
        tvRelease.setText(getString(R.string.not_found));
        tvGenre.setText(getString(R.string.not_found));
        tvDirector.setText(getString(R.string.not_found));
        tvActors.setText(getString(R.string.not_found));
        tvPlot.setText(getString(R.string.not_found));
        rb.setRating(0);
    }

    private class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                in = con.getInputStream();

                Bitmap image = BitmapFactory.decodeStream(in);


                return image;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            loadCounter++;
            if(loadCounter > 1)
                progressDialog.dismiss();
            iv.setImageBitmap(b);
        }
    }

    public class GetMovieAsyncTask extends AsyncTask<String, Void, Movies> {
        @Override
        protected Movies doInBackground(String... params) {

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
                    return MoviesUtil.MoviesJSONParser.parseMovie(sb.toString());
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
        protected void onPostExecute(Movies movies) {
            moviesList.set(index, movies);
            movie = movies;
            loadCounter++;
            if(loadCounter > 1)
                progressDialog.dismiss();

            if(movies == null) {
                fillNotFound();
            } else
                fillText();
        }
    }

    public String formatDate(String in) {
        SimpleDateFormat inDate, outDate;
        Date date;

        inDate = new SimpleDateFormat("dd MMM yyyy");
        outDate = new SimpleDateFormat("MMM dd yyyy");

        try {
            date = inDate.parse(in);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return in;
        }
        return outDate.format(date);
    }
}