package com.example.mufiest.models;

import java.util.ArrayList;
import java.util.List;

public class UserDetail {
    private String userDetailId;
    private String userId;
    private ArrayList<String> reviews;
    private ArrayList<String> likedMovies;
    private ArrayList<String> viewedMovies;

    public UserDetail(String userDetailId, String userId, ArrayList<String> reviews, ArrayList<String> likedMovies, ArrayList<String> viewedMovies) {
        this.userDetailId = userDetailId;
        this.userId = userId;
        this.reviews = reviews;
        this.likedMovies = likedMovies;
        this.viewedMovies = viewedMovies;
    }

    public String getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(String userDetailId) {
        this.userDetailId = userDetailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<String> getViewedMovies() {
        return viewedMovies;
    }

    public void setViewedMovies(ArrayList<String> viewedMovies) {
        this.viewedMovies = viewedMovies;
    }
}
