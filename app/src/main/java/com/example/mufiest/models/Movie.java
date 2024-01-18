package com.example.mufiest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    private String movieId;
    private String title;
    private Integer year;
    private Integer length;
    private String director;
    private ArrayList<String> genres;
    private String plot;
    private Integer viewCount;
    private Integer likeCount;
    private String posterUrl;
    private String backgroundUrl;

    public Movie(String movieId, String title, Integer year, Integer length, String director, ArrayList<String> genres, String plot, Integer viewCount, Integer likeCount, String posterUrl, String backgroundUrl) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.length = length;
        this.director = director;
        this.genres = genres;
        this.plot = plot;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.posterUrl = posterUrl;
        this.backgroundUrl = backgroundUrl;
    }

    public Movie() {
        // Default constructor required for Firebase
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public Movie(Parcel parcel) {
        movieId = parcel.readString();
        title = parcel.readString();
        year = parcel.readInt();
        length = parcel.readInt();
        director = parcel.readString();
        genres = new ArrayList<>();
        parcel.readList(genres, getClass().getClassLoader());
        plot = parcel.readString();
        viewCount = parcel.readInt();
        likeCount = parcel.readInt();
        posterUrl = parcel.readString();
        backgroundUrl = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(title);
        parcel.writeInt(year);
        parcel.writeInt(length);
        parcel.writeString(director);
        parcel.writeList(genres);
        parcel.writeString(plot);
        parcel.writeInt(viewCount);
        parcel.writeInt(likeCount);
        parcel.writeString(posterUrl);
        parcel.writeString(backgroundUrl);
    }


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
