package com.example.mufiest.controller;

import androidx.annotation.NonNull;

import com.example.mufiest.models.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieController {

    public static void getPopularMovies(final OnMoviesLoadedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mufiest-e2f6c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        DatabaseReference movieCloudEndPoint = myRef.child("movies");

        movieCloudEndPoint.orderByChild("viewCount")
        .limitToLast(10)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Movie> popularMovies = new ArrayList<>();

                for (DataSnapshot movieSnapshot : snapshot.getChildren()) {
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    if (movie != null) {
                        popularMovies.add(movie);
                    }
                }

                Collections.sort(popularMovies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie1, Movie movie2) {
                        return Integer.compare(movie2.getViewCount(), movie1.getViewCount());
                    }
                });

                listener.onMoviesLoaded(popularMovies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void getFavoriteMovies(final OnMoviesLoadedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mufiest-e2f6c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        DatabaseReference movieCloudEndPoint = myRef.child("movies");

        movieCloudEndPoint.orderByChild("likeCount")
        .limitToLast(10)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Movie> favoriteMovies = new ArrayList<>();

                for (DataSnapshot movieSnapshot : snapshot.getChildren()) {
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    if (movie != null) {
                        favoriteMovies.add(movie);
                    }
                }

                Collections.sort(favoriteMovies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie1, Movie movie2) {
                        return Integer.compare(movie2.getLikeCount(), movie1.getLikeCount());
                    }
                });

                listener.onMoviesLoaded(favoriteMovies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public interface OnMoviesLoadedListener {
        void onMoviesLoaded(ArrayList<Movie> movies);
        void onError(String errorMessage);
    }

}
