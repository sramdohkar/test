/*
    Assignment HW4
    Group#19_HW04.zip
    Shashank Ramdohkar
 */

package com.example.group19_hw04;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {
    String title, imdbID, poster, genre, director, actors, plot, released;
    int year;
    double imdbRating;

    Movies(String title, String imdbID, String poster, String genre, String director, String actors, String plot, String released,
            int year, double imdbRating)
    {
        super();
        this.title = title;
        this.imdbID = imdbID;
        this.poster = poster;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.released = released;
        this.year = year;
        this.imdbRating = imdbRating;

    }

    Movies()
    {
        title = "";
        imdbID = "";
        poster = "";
        genre = "";
        director = "";
        actors = "";
        plot = "";
        released = "";
        year = 0;
        imdbRating = -1;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "title='" + title + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", poster='" + poster + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", released='" + released + '\'' +
                ", year=" + year +
                ", imdbRating=" + imdbRating +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imdbID);
        dest.writeString(poster);
        dest.writeString(genre);
        dest.writeString(director);
        dest.writeString(actors);
        dest.writeString(plot);
        dest.writeString(released);
        dest.writeInt(year);
        dest.writeDouble(imdbRating);

    }

    public static final Parcelable.Creator<Movies> CREATOR
            = new Parcelable.Creator<Movies>() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    private Movies(Parcel in) {
        this.title = in.readString();
        this.imdbID = in.readString();
        this.poster = in.readString();
        this.genre = in.readString();
        this.director = in.readString();
        this.actors = in.readString();
        this.plot = in.readString();
        this.released = in.readString();
        this.year = in.readInt();
        this.imdbRating = in.readDouble();
    }
}
