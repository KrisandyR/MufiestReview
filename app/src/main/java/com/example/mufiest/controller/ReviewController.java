package com.example.mufiest.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mufiest.models.Movie;
import com.example.mufiest.models.Review;
import com.example.mufiest.models.ReviewWithDetail;
import com.example.mufiest.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ReviewController {

    public static void getRecentReviews(final ReviewController.OnReviewsLoadedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mufiest-e2f6c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        DatabaseReference movieCloudEndPoint = myRef.child("movies");
        DatabaseReference userCloudEndPoint = myRef.child("users");
        DatabaseReference reviewCloudEndPoint = myRef.child("reviews");

        reviewCloudEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ReviewWithDetail> reviewsWithDetails = new ArrayList<>();

                for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                    Review review = reviewSnapshot.getValue(Review.class);

                    if (review != null) {
                        DatabaseReference moviesRef = movieCloudEndPoint.child(review.getMovieId());
                        moviesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Movie movie = snapshot.getValue(Movie.class);
                                if (movie != null) {
                                    DatabaseReference usersRef = userCloudEndPoint.child(review.getUserId());
                                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User user = snapshot.getValue(User.class);

                                            if (user != null) {
                                                ReviewWithDetail reviewWithDetail = new ReviewWithDetail();
                                                reviewWithDetail.setMovieName(movie.getTitle());
                                                reviewWithDetail.setMovieYear(movie.getYear());
                                                reviewWithDetail.setMoviePosterUrl(movie.getPosterUrl());
                                                reviewWithDetail.setUsername(user.getUsername());
                                                reviewWithDetail.setReviewId(review.getReviewId());
                                                reviewWithDetail.setMovieId(review.getMovieId());
                                                reviewWithDetail.setUserId(review.getUserId());
                                                reviewWithDetail.setDescription(review.getDescription());
                                                reviewWithDetail.setDate(review.getDate());
                                                reviewWithDetail.setRating(review.getRating());
                                                reviewWithDetail.setLikedBy(review.getLikedBy());

                                                reviewsWithDetails.add(reviewWithDetail);

                                                if (reviewsWithDetails.size() == snapshot.getChildrenCount()) {
                                                    listener.onReviewsLoaded(reviewsWithDetails);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            listener.onError(error.getMessage());
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                listener.onError(error.getMessage());
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void getReviewsByMovieId(String movieId, OnReviewsLoadedListener listener) {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mufiest-e2f6c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        DatabaseReference movieCloudEndPoint = myRef.child("movies").child(movieId);
        DatabaseReference userCloudEndPoint = myRef.child("users");
        DatabaseReference reviewCloudEndPoint = myRef.child("reviews");
        movieCloudEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Movie movie = snapshot.getValue(Movie.class);
                reviewCloudEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<ReviewWithDetail> reviewsWithDetails = new ArrayList<>();

                        for(DataSnapshot reviewSnapshot : snapshot.getChildren()){
                            Review review = reviewSnapshot.getValue(Review.class);
                            if(movie != null && review != null && review.getMovieId().equals(movie.getMovieId())){
                                DatabaseReference usersRef = userCloudEndPoint.child(review.getUserId());
                                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                        User user = userSnapshot.getValue(User.class);
                                        if (user != null) {
                                            ReviewWithDetail reviewWithDetail = new ReviewWithDetail();
                                            reviewWithDetail.setMovieName(movie.getTitle());
                                            reviewWithDetail.setMovieYear(movie.getYear());
                                            reviewWithDetail.setMoviePosterUrl(movie.getPosterUrl());
                                            reviewWithDetail.setUsername(user.getUsername());
                                            reviewWithDetail.setReviewId(review.getReviewId());
                                            reviewWithDetail.setMovieId(review.getMovieId());
                                            reviewWithDetail.setUserId(review.getUserId());
                                            reviewWithDetail.setDescription(review.getDescription());
                                            reviewWithDetail.setDate(review.getDate());
                                            reviewWithDetail.setRating(review.getRating());
                                            reviewWithDetail.setLikedBy(review.getLikedBy());
                                            reviewsWithDetails.add(reviewWithDetail);

                                            if (reviewsWithDetails.size() == snapshot.getChildrenCount()) {
                                                listener.onReviewsLoaded(reviewsWithDetails);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        listener.onError(error.getMessage());
                                    }
                                });
                            }
                        }
                        if(reviewsWithDetails.isEmpty()){
                            listener.onReviewsLoaded(reviewsWithDetails);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onError(error.getMessage());
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.getMessage());
            }
        });


    }

    public interface OnReviewsLoadedListener {
        void onReviewsLoaded(ArrayList<ReviewWithDetail> reviews);
        void onError(String errorMessage);
    }

}
