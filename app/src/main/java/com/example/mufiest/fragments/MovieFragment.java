package com.example.mufiest.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mufiest.R;
import com.example.mufiest.adapters.GenreListAdapter;
import com.example.mufiest.controller.MovieController;
import com.example.mufiest.controller.ReviewController;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements ReviewFormFragment.onExitButtonClickedListener, ReviewFormFragment.onSubmitButtonClickedListener {

    private static final String ARG_MOVIE = "movieId";

    private View view;
    private Context ctx;
    private String movieId, userId;
    private Movie movie;
    private ArrayList<ReviewWithDetail> reviews;

    private ImageView movieBackgroundIv, moviePosterIv;
    private TextView movieTitleTv, movieDirectorTv, movieYearTv, movieLengthTv, movieDescTv;
    private RecyclerView genresRv;
    private GenreListAdapter adapter;
    private RatingBar movieRatingRb;
    private ImageButton addReviewBtn, editReviewBtn;

    private ReviewWithDetail personalReview;
    private boolean personalReviewExists;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
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
        view = inflater.inflate(R.layout.fragment_movie, container, false);

        addReviewBtn = view.findViewById(R.id.fixed_add_review_btn);
        editReviewBtn = view.findViewById(R.id.fixed_edit_review_btn);

        getUserIdByAuth();
        getReviewData(view);
        getMovieData(view);

        return view;
    }

    private void getUserIdByAuth() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        String authUserId = currentUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.orderByChild("userId").equalTo(authUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userIdFound = handleUserSnapshot(dataSnapshot);
                if(userIdFound != null){
                    userId = userIdFound;
                    handleUserIdIsSet(userIdFound);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String handleUserSnapshot(DataSnapshot dataSnapshot) {
        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
            if (userSnapshot.getKey() != null && !userSnapshot.getKey().isEmpty()) {
                return userSnapshot.getKey();
            }
        }
        return null;
    }

    private void handleUserIdIsSet(String userIdParam) {
        personalReviewExists = false;
        getPersonalReviewData(movieId, userIdParam);
    }
    private void getPersonalReviewData(String movieId, String userId) {
        ReviewController.getPersonalReviewByMovieId(movieId, userId,
                new ReviewController.OnReviewLoadedListener() {
                    @Override
                    public void onReviewLoaded(ReviewWithDetail review) {
                        personalReview = review;
                        if(review != null){
                            personalReviewExists = true;
                        }
                        handlePersonalReviewLoaded(personalReview, personalReviewExists);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                }
        );
    }

    private void handlePersonalReviewLoaded(ReviewWithDetail review, boolean personalReviewExists) {

        if(personalReviewExists){
            editReviewBtn.setVisibility(View.VISIBLE);
            addReviewBtn.setVisibility(View.GONE);

            editReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addReviewFormFragment("Edit",movieId, userId, review);
                }
            });
        } else {
            editReviewBtn.setVisibility(View.GONE);
            addReviewBtn.setVisibility(View.VISIBLE);

            addReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addReviewFormFragment("Add", movieId, userId);
                }
            });
        }
    }

    private void getReviewData(View view) {
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
                fillMovieDataToView(movie);
                fillGenresRv(movie);
            }

            @Override
            public void onError(String errorMessage) {
                Log.v("Error", errorMessage);
            }
        });
    }

    private void getMovieRating(){
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

    private void addReviewFormFragment(String formType,  String movieId, String userId) {
        ReviewFormFragment reviewForm = ReviewFormFragment.newInstance(formType, movieId, userId,
                this, this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.review_form_container_fm, reviewForm);
        transaction.commit();
    }

    private void addReviewFormFragment(String formType,  String movieId, String userId, ReviewWithDetail reviewWithDetail) {
        ReviewFormFragment reviewForm = ReviewFormFragment.newInstance(formType, movieId, userId,
                this, this, reviewWithDetail);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.review_form_container_fm, reviewForm);
        transaction.commit();
    }

    private void addMovieReviewScrollListFragment(ArrayList<ReviewWithDetail> reviews) {
        MovieReviewList reviewList = MovieReviewList.newInstance(reviews);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.movie_review_list_container_fm, reviewList);
        transaction.commit();
    }

    private void fillGenresRv(Movie movie) {
        genresRv = view.findViewById(R.id.rv_movie_genre_list_fm);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        genresRv.setLayoutManager(layoutManager);
        ArrayList<String> genres = movie.getGenres();

        adapter = new GenreListAdapter(getContext(), genres);
        genresRv.setAdapter(adapter);
    }

    private void fillMovieDataToView(Movie movie) {

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
        getMovieRating();

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

    private void saveMovie(Movie movie) {
        this.movie = movie;
    }

    private void saveReviews(ArrayList<ReviewWithDetail> reviews){
        this.reviews = reviews;
    }
    @Override
    public void onExitButtonClicked() {
        hideReviewForm();
        hideSoftKeyboard();
    }

    @Override
    public void onSubmitButtonClicked() {
        handleUserIdIsSet(userId);
        getReviewData(view);
        hideReviewForm();
        hideSoftKeyboard();
    }

    private void hideReviewForm() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        ReviewFormFragment reviewForm = (ReviewFormFragment) getChildFragmentManager()
                .findFragmentById(R.id.review_form_container_fm);
        if (reviewForm != null) {
            transaction.remove(reviewForm);
            transaction.commit();
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
    }
}