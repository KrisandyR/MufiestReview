package com.example.mufiest.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mufiest.R;
import com.example.mufiest.adapters.MovieScrollListAdapter;
import com.example.mufiest.models.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieScrollList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieScrollList extends Fragment {

    private TextView movieListTextView;

    private RecyclerView recyclerView;
    private static final String ARG_PARAM1 = "movies";
    private static final String ARG_PARAM2 = "movieListType";
    private ArrayList<Movie> movies;
    private String movieListType;
    public MovieScrollList() {
        // Required empty public constructor
    }

    public static MovieScrollList newInstance(ArrayList<Movie> movies, String movieListType) {
        MovieScrollList fragment = new MovieScrollList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, movies);
        args.putString(ARG_PARAM2, movieListType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.movies = getArguments().getParcelableArrayList(ARG_PARAM1);
            this.movieListType = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_scroll_list, container, false);
        recyclerView = view.findViewById(R.id.rv_movie_scroll_list);
        movieListTextView = view.findViewById(R.id.movieListTitle);

        movieListTextView.setText(movieListType + " Movies");

        if(movies != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            MovieScrollListAdapter adapter = new MovieScrollListAdapter(getContext(), movies);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}