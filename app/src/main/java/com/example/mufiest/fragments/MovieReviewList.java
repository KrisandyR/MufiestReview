package com.example.mufiest.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mufiest.R;
import com.example.mufiest.adapters.MovieReviewListAdapter;
import com.example.mufiest.adapters.MovieScrollListAdapter;
import com.example.mufiest.adapters.ReviewWithPosterListAdapter;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;

import java.util.ArrayList;

public class MovieReviewList extends Fragment {
    private RecyclerView reviewListRv;
    private TextView noReviewTv;
    private static final String ARG_PARAM1 = "reviews";
    private ArrayList<ReviewWithDetail> reviews;
    public MovieReviewList() {

    }

    public static MovieReviewList newInstance(ArrayList<ReviewWithDetail> reviews) {
        MovieReviewList fragment = new MovieReviewList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, reviews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.reviews = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_review_list, container, false);
        reviewListRv = view.findViewById(R.id.rv_review_scroll_list_fmrl);

        if(reviews == null){
            return view;
        }

        if(!reviews.isEmpty()) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            reviewListRv.setLayoutManager(layoutManager);

            MovieReviewListAdapter adapter = new MovieReviewListAdapter(getContext(), reviews);
            reviewListRv.setAdapter(adapter);
        }

        if (reviews.isEmpty()){
            Log.v("else", "else");
            noReviewTv = view.findViewById(R.id.no_review_tv);
            noReviewTv.setVisibility(View.VISIBLE);
        }

        if(reviews.isEmpty() || reviews.size() <= 3){
            ViewGroup.LayoutParams layoutParams = reviewListRv.getLayoutParams();
            layoutParams.height = 200;
            reviewListRv.setLayoutParams(layoutParams);
        }

        return view;
    }

}