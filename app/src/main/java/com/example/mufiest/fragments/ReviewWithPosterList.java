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
import com.example.mufiest.adapters.ReviewWithPosterListAdapter;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;

import java.util.ArrayList;

public class ReviewWithPosterList extends Fragment {

    private TextView reviewListTitleTv;
    private RecyclerView reviewListRv;
    private static final String ARG_PARAM1 = "reviews";
    private static final String ARG_PARAM2 = "sortReviewBy";
    private ArrayList<ReviewWithDetail> reviews;
    private String sortReviewBy;

    public ReviewWithPosterList() {

    }

    public static ReviewWithPosterList newInstance(ArrayList<ReviewWithDetail> reviews, String sortReviewBy) {
        ReviewWithPosterList fragment = new ReviewWithPosterList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, reviews);
        args.putString(ARG_PARAM2, sortReviewBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.reviews = getArguments().getParcelableArrayList(ARG_PARAM1);
            this.sortReviewBy = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review_with_poster_list, container, false);
        reviewListRv = view.findViewById(R.id.rv_review_scroll_list_frwpl);
        reviewListTitleTv = view.findViewById(R.id.review_list_title_frwpl);

        reviewListTitleTv.setText(sortReviewBy + " Reviews");

        if(reviews != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            reviewListRv.setLayoutManager(layoutManager);

            ReviewWithPosterListAdapter adapter = new ReviewWithPosterListAdapter(getContext(), reviews);
            reviewListRv.setAdapter(adapter);
        }

        return view;
    }
}