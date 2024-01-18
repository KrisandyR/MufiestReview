package com.example.mufiest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Review implements Parcelable {

    private String reviewId;
    private String movieId;
    private String userId;
    private String description;
    private Double rating;
    private String date;
    private ArrayList<String> likedBy; // list of userId

    public Review() {
        // Default constructor required for Firebase
    }

    public Review(String reviewId, String movieId, String userId, String description, Double rating, String date, ArrayList<String> likers) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.userId = userId;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.likedBy = likers;
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

    public Review(Parcel parcel) {
        this.reviewId = parcel.readString();
        this.movieId = parcel.readString();
        this.userId = parcel.readString();
        this.description = parcel.readString();
        this.rating = parcel.readDouble();
        this.date = parcel.readString();
        this.likedBy = new ArrayList<>();
        parcel.readList(likedBy, getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(reviewId);
        parcel.writeString(movieId);
        parcel.writeString(userId);
        parcel.writeString(description);
        parcel.writeDouble(rating);
        parcel.writeString(date);
        parcel.writeList(likedBy);
    }


    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
