package com.example.mufiest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.mufiest.controller.MovieController;
import com.example.mufiest.controller.ReviewController;
import com.example.mufiest.fragments.MovieScrollList;
import com.example.mufiest.fragments.ReviewWithPosterList;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addPopularMovieList();
        addFavoriteMovieList();
        addRecentReviewsList();
    }

    private void addRecentReviewsList() {
        ReviewController.getRecentReviews(new ReviewController.OnReviewsLoadedListener() {
            @Override
            public void onReviewsLoaded(ArrayList<ReviewWithDetail> reviews) {
                addReviewWithPosterScrollListFragment(reviews, "Recent", R.id.recentReviewListContainer);
            }

            @Override
            public void onError(String errorMessage) {
                Log.v("Error", "Error loading the recent reviews data");
            }
        });
    }

    private void addPopularMovieList() {
        MovieController.getPopularMovies(new MovieController.OnMoviesLoadedListener() {
            @Override
            public void onMoviesLoaded(ArrayList<Movie> movies) {
                addMovieScrollListFragment(movies, "Popular", R.id.popularMovieListContainer);
            }

            @Override
            public void onError(String errorMessage) {
                Log.v("Error", "Error loading the popular movie data");
            }
        });
    }

    private void addFavoriteMovieList() {
        MovieController.getFavoriteMovies(new MovieController.OnMoviesLoadedListener() {
            @Override
            public void onMoviesLoaded(ArrayList<Movie> movies) {
                addMovieScrollListFragment(movies, "Favorite", R.id.favoriteMovieListContainer);
            }

            @Override
            public void onError(String errorMessage) {
                Log.v("Error", "Error loading the favorite movie data");
            }
        });
    }

    private void addMovieScrollListFragment(ArrayList<Movie> movies, String movieListType, int fragmentId) {
        MovieScrollList movieScrollListFragment = MovieScrollList.newInstance(movies, movieListType);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentId, movieScrollListFragment);
        transaction.commit();
    }

    private void addReviewWithPosterScrollListFragment(ArrayList<ReviewWithDetail> reviews, String sortReviewBy, int fragmentId) {
        ReviewWithPosterList reviewWithPosterList = ReviewWithPosterList.newInstance(reviews, sortReviewBy);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentId, reviewWithPosterList);
        transaction.commit();
    }

}