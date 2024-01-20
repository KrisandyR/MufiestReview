package com.example.mufiest.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
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
public class MovieScrollList extends Fragment implements MovieScrollListAdapter.OnMovieClickListener {

    private TextView movieListTextView, movieSearchResultTextView;
    private RecyclerView recyclerView;
    private static final String ARG_PARAM1 = "movies";
    private static final String ARG_PARAM2 = "movieListType";
    private static final String ARG_PARAM3 = "isScrollableDisabled";
    private ArrayList<Movie> movies;
    private String movieListType;
    private MovieScrollListAdapter adapter;
    private boolean isVertical;
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

    public static MovieScrollList newInstance(ArrayList<Movie> movies, String movieListType, boolean isVertical) {
        MovieScrollList fragment = new MovieScrollList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, movies);
        args.putString(ARG_PARAM2, movieListType);
        args.putBoolean(ARG_PARAM3, isVertical);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.movies = getArguments().getParcelableArrayList(ARG_PARAM1);
            this.movieListType = getArguments().getString(ARG_PARAM2);
            this.isVertical = false;
            if(getArguments().containsKey(ARG_PARAM3)){
                this.isVertical = getArguments().getBoolean(ARG_PARAM3);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_scroll_list, container, false);
        recyclerView = view.findViewById(R.id.rv_movie_scroll_list);
        movieListTextView = view.findViewById(R.id.movieListTitle);
        movieSearchResultTextView = view.findViewById(R.id.movieSearchResultTitle);

        if(movieListType.equals("NoMovieFound")){
            movieListTextView.setVisibility(View.GONE);
            movieSearchResultTextView.setVisibility(View.GONE);
        } else if (movieListType.equals("MovieFound")) {
            movieListTextView.setVisibility(View.GONE);
            movieSearchResultTextView.setVisibility(View.VISIBLE);
            movieSearchResultTextView.setText("Found " + movies.size() + " movies");
        } else{
            movieListTextView.setVisibility(View.VISIBLE);
            movieSearchResultTextView.setVisibility(View.GONE);
            movieListTextView.setText(movieListType + " Movies");
        }

        if(movies != null) {

            Log.v("check", String.valueOf(isVertical));

            if(isVertical){
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
            } else {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
            }

            adapter = new MovieScrollListAdapter(getContext(), movies, this);
            recyclerView.setAdapter(adapter);

        }
        return view;
    }

    @Override
    public void onMovieClicked(String movieId) {
        if (getActivity() instanceof MovieScrollListAdapter.OnMovieClickListener) {
            ((MovieScrollListAdapter.OnMovieClickListener) getActivity()).onMovieClicked(movieId);
        } else {
            Log.w("MovieScrollListFragment", "MainActivity does not implement OnMovieClickedListener");
        }
    }
}