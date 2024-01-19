package com.example.mufiest.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mufiest.R;
import com.example.mufiest.adapters.GenreListAdapter;
import com.example.mufiest.adapters.MovieReviewListAdapter;
import com.example.mufiest.adapters.MovieScrollListAdapter;
import com.example.mufiest.controller.MovieController;
import com.example.mufiest.controller.ReviewController;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private static final String ARG_MOVIE = "movieId";

    private String movieId;
    private Movie movie;
    private ArrayList<ReviewWithDetail> reviews;

    private ImageView movieBackgroundIv, moviePosterIv;
    private TextView movieTitleTv, movieDirectorTv, movieYearTv, movieLengthTv, movieDescTv;
    private RecyclerView genresRv;
    private GenreListAdapter adapter;
    private RatingBar movieRatingRb;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(String movieId) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        getReviewData(view);
        getMovieData(view);


        return view;
    }

    private void getReviewData(View view) {

        Log.v("reviews", "terst masuk getreviewdata");
        ReviewController.getReviewsByMovieId(movieId, new ReviewController.OnReviewsLoadedListener() {
            @Override
            public void onReviewsLoaded(ArrayList<ReviewWithDetail> reviews) {

                saveReviews(reviews);
                addMovieReviewScrollListFragment(reviews);
            }

            @Override
            public void onError(String errorMessage) {
                Log.v("Error", errorMessage);
            }
        });
    }

    private void getMovieData(View view) {
        MovieController.getMovie(movieId, new MovieController.OnMovieLoadedListener() {
            @Override
            public void onMovieLoaded(Movie movie) {
                saveMovie(movie);
                fillMovieDataToView(view, movie);
                fillGenresRv(view, movie);
            }

            @Override
            public void onError(String errorMessage) {
                Log.v("Error", errorMessage);
            }
        });
    }

    private void getMovieRating(View view){
        MovieController.getMovieRating(movieId, new MovieController.OnMovieRatingLoadedListener() {
            @Override
            public void onMovieRatingLoaded(float rating) {
                RatingBar rb = view.findViewById(R.id.review_rating_rb_fm);
                rb.setRating(rating);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void saveMovie(Movie movie) {
        this.movie = movie;
    }

    private void saveReviews(ArrayList<ReviewWithDetail> reviews){
        this.reviews = reviews;
    }

    private void addMovieReviewScrollListFragment(ArrayList<ReviewWithDetail> reviews) {
        Log.v("tag", "sampai sini addmoviereviewscrolllisrfragment");
        MovieReviewList reviewList = MovieReviewList.newInstance(reviews);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.movie_review_list_container_fm, reviewList);
        transaction.commit();
    }

    private void fillGenresRv(View view, Movie movie) {
        genresRv = view.findViewById(R.id.rv_movie_genre_list_fm);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        genresRv.setLayoutManager(layoutManager);
        ArrayList<String> genres = movie.getGenres();

        adapter = new GenreListAdapter(getContext(), genres);
        genresRv.setAdapter(adapter);
    }

    private void fillMovieDataToView(View view, Movie movie) {

        movieBackgroundIv = view.findViewById(R.id.movie_background_iv_fm);
        Picasso.get().load(movie.getBackgroundUrl()).into(movieBackgroundIv);

        String moviePosterUrl = movie.getPosterUrl();
        moviePosterIv = view.findViewById(R.id.movie_poster_iv_fm);
        Picasso.get().load(moviePosterUrl).into(moviePosterIv);

        movieTitleTv = view.findViewById(R.id.movie_name_tv_fm);
        movieTitleTv.setText(movie.getTitle());
        if(movie.getTitle().length() > 30){
            movieTitleTv.setTextSize(22);
        }

        movieRatingRb = view.findViewById(R.id.review_rating_rb_fm);
        getMovieRating(view);

        movieDirectorTv = view.findViewById(R.id.movie_director_tv_fm);
        movieDirectorTv.setText(movie.getDirector());

        movieYearTv = view.findViewById(R.id.movie_year_tv_fm);
        movieYearTv.setText(String.valueOf(movie.getYear()));

        String movieLength = String.valueOf(movie.getLength()) + " min";
        movieLengthTv = view.findViewById(R.id.movie_length_tv_fm);
        movieLengthTv.setText(movieLength);

        movieDescTv = view.findViewById(R.id.movie_desc_tv_fm);
        movieDescTv.setText(movie.getPlot());

    }
}