package com.example.mufiest.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mufiest.R;
import com.example.mufiest.controller.MovieController;
import com.example.mufiest.controller.ReviewController;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.mufiest.controller.MovieController;
import com.example.mufiest.controller.ReviewController;
import com.example.mufiest.fragments.MovieScrollList;
import com.example.mufiest.fragments.ReviewWithPosterList;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPopularMovieList();
        addFavoriteMovieList();
        addRecentReviewsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentId, movieScrollListFragment);
        transaction.commit();
    }

    private void addReviewWithPosterScrollListFragment(ArrayList<ReviewWithDetail> reviews, String sortReviewBy, int fragmentId) {
        ReviewWithPosterList reviewWithPosterList = ReviewWithPosterList.newInstance(reviews, sortReviewBy);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentId, reviewWithPosterList);
        transaction.commit();
    }
}