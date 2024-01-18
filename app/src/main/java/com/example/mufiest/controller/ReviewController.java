package com.example.mufiest.controller;

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

public class ReviewController {

    public static void getRecentReviews(final ReviewController.OnReviewsLoadedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mufiest-e2f6c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        DatabaseReference movieCloudEndPoint = myRef.child("movies");
        DatabaseReference userCloudEndPoint = myRef.child("users");
        DatabaseReference reviewCloudEndPoint = myRef.child("reviews");


        reviewCloudEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ReviewWithDetail> reviewsWithDetails = new ArrayList<>();

                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    Review review = reviewSnapshot.getValue(Review.class);

                    if (review != null) {
                        // Retrieve movie data using movieId
                        DatabaseReference moviesRef = movieCloudEndPoint.child(review.getMovieId());
                        moviesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot movieSnapshot) {
                                Movie movie = movieSnapshot.getValue(Movie.class);

                                if (movie != null) {
                                    // Retrieve username using userId
                                    DatabaseReference usersRef = userCloudEndPoint.child(review.getUserId());
                                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                            User user = userSnapshot.getValue(User.class);

                                            if (user != null) {
                                                // Create ReviewWithDetails object and add to the list
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

                                                // Notify the listener when all data is retrieved
                                                if (reviewsWithDetails.size() == dataSnapshot.getChildrenCount()) {
                                                    listener.onReviewsLoaded(reviewsWithDetails);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle error
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public interface OnReviewsLoadedListener {
        void onReviewsLoaded(ArrayList<ReviewWithDetail> reviews);
        void onError(String errorMessage);
    }

}
