package com.example.mufiest.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mufiest.R;
import com.example.mufiest.controller.MovieController;
import com.example.mufiest.models.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MovieSearchFragment extends Fragment {
    private EditText searchInput;
    private ImageView searchButton;
    private String movieId, movieTitle;
    private boolean movieFound = false;
    private FirebaseDatabase database;
    private DatabaseReference movieRef;


    public MovieSearchFragment() {
        // Required empty public constructor
    }

    public static MovieSearchFragment newInstance() {
        return new MovieSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);

        searchInput = view.findViewById(R.id.searchInput);
        searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieTitle = String.valueOf(searchInput.getText());

                hideKeyboard();

                searchMovie(movieTitle);
            }
        });


        return view;
    }

    private void searchMovie(String movieTitle) {
        Log.v("search", movieTitle);
        if (movieTitle.isEmpty() || movieTitle.trim().equals("")) {
            Toast.makeText(requireContext(), "Please enter a movie title", Toast.LENGTH_SHORT).show();
            return;
        }

        database = FirebaseDatabase.getInstance();
        movieRef = database.getReference("movies");

        movieRef.orderByChild("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handleMovieSearchResult(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleMovieSearchResult(DataSnapshot dataSnapshot) {
        movieFound = false;

        ArrayList<Movie> searchMovies = new ArrayList<>();

        for (DataSnapshot movieSnapshot : dataSnapshot.getChildren()) {
            movieId = movieSnapshot.getKey();
            movieRef = FirebaseDatabase.getInstance().getReference("movies").child(movieId);

            Movie movie = movieSnapshot.getValue(Movie.class);

            if (movie != null && movie.getTitle().toLowerCase().contains(movieTitle.toLowerCase())) {
                searchMovies.add(movie);

                addMovieScrollListFragment(searchMovies, "MovieFound", R.id.searchMovieListContainer);

                movieFound = true;
            }
        }

        if (!movieFound) {
            addMovieScrollListFragment(searchMovies, "NoMovieFound", R.id.searchMovieListContainer);

            Toast.makeText(requireContext(), "Movie Title Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMovieScrollListFragment(ArrayList<Movie> searchMovies, String movieListType, int fragmentId) {
        MovieScrollList movieScrollListFragment = MovieScrollList.newInstance(searchMovies, movieListType, true);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentId, movieScrollListFragment);
        transaction.commit();
    }

    private void hideKeyboard() {
        InputMethodManager imm  = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm  != null) {
            imm .hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
        }
    }
}