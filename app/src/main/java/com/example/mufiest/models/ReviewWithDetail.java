package com.example.mufiest.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.util.ArrayList;

public class ReviewWithDetail implements Parcelable {

    private String movieName;
    private int movieYear;
    private String moviePosterUrl;
    private String username;
    private String reviewId;
    private String movieId;
    private String userId;
    private String description;
    private Double rating;
    private String date;
    private ArrayList<String> likedBy; // list of userId

    public ReviewWithDetail(String movieName, int movieYear, String moviePosterUrl, String username, String reviewId, String movieId, String userId, String description, Double rating, String date, ArrayList<String> likedBy) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.moviePosterUrl = moviePosterUrl;
        this.username = username;
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.userId = userId;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.likedBy = likedBy;
    }

    public ReviewWithDetail(){

    }

    protected ReviewWithDetail(Parcel in) {
        movieName = in.readString();
        movieYear = in.readInt();
        moviePosterUrl = in.readString();
        username = in.readString();
        reviewId = in.readString();
        movieId = in.readString();
        userId = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        likedBy = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeInt(movieYear);
        dest.writeString(moviePosterUrl);
        dest.writeString(username);
        dest.writeString(reviewId);
        dest.writeString(movieId);
        dest.writeString(userId);
        dest.writeString(description);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeStringList(likedBy);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewWithDetail> CREATOR = new Creator<ReviewWithDetail>() {
        @Override
        public ReviewWithDetail createFromParcel(Parcel in) {
            return new ReviewWithDetail(in);
        }

        @Override
        public ReviewWithDetail[] newArray(int size) {
            return new ReviewWithDetail[size];
        }
    };

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }
}
