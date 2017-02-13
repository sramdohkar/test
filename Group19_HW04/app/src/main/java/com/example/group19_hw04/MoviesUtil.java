/*
    Assignment HW4
    Group#19_HW04.zip
    Shashank Ramdohkar, James Budday
 */

package com.example.group19_hw04;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesUtil {
    static public class MoviesJSONParser{
        static ArrayList<Movies> parseMovies(String in) throws JSONException {
            ArrayList<Movies> movieList = new ArrayList<Movies>();

            JSONObject root = new JSONObject(in);
            JSONArray moviesJSONArray = root.getJSONArray("Search");
            for(int i = 0 ; i < moviesJSONArray.length(); i++)
            {
                JSONObject js = moviesJSONArray.getJSONObject(i);
//                Movies movie = Movies.CreateMovie(movieJSONObject);
                Movies movies = new Movies();
                movies.setTitle(js.getString("Title"));
                movies.setImdbID(js.getString("imdbID"));
                movies.setPoster(js.getString("Poster"));
                //movies.setGenre(js.getString("Genre"));
                //movies.setDirector(js.getString("Director"));
                //movies.setActors(js.getString("Actors"));
                //movies.setPlot(js.getString("Plot"));
                //movies.setReleased(js.getString("Released"));
                movies.setYear(js.getInt("Year"));
                //movies.setImdbRating(js.getDouble("imdbRating"));
                movieList.add(movies);

            }
            return movieList;
        }

        static Movies parseMovie(String in) throws JSONException {
            JSONObject js = new JSONObject(in);
            Movies movies = new Movies();
            movies.setTitle(js.getString("Title"));
            movies.setImdbID(js.getString("imdbID"));
            movies.setPoster(js.getString("Poster"));
            movies.setGenre(js.getString("Genre"));
            movies.setDirector(js.getString("Director"));
            movies.setActors(js.getString("Actors"));
            movies.setPlot(js.getString("Plot"));
            movies.setReleased(js.getString("Released"));
            movies.setYear(js.getInt("Year"));
            movies.setImdbRating(js.getDouble("imdbRating"));

            return movies;
        }
    }
}