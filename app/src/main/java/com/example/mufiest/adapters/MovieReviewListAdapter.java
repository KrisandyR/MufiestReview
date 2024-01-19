package com.example.mufiest.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import com.example.mufiest.views.MovieCardViewHolder;
import com.example.mufiest.views.MovieReviewCardViewHolder;
import com.example.mufiest.views.ReviewCardWithPosterViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewCardViewHolder> {

    private ArrayList<ReviewWithDetail> reviews;
    private LayoutInflater mInflater;

    public MovieReviewListAdapter(Context ctx, ArrayList<ReviewWithDetail> reviews) {
        this.mInflater = LayoutInflater.from(ctx);
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MovieReviewCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movie_review_card, parent, false);
        return new MovieReviewCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewCardViewHolder holder, int position) {
        ReviewWithDetail review = reviews.get(position);
        holder.reviewerTv.setText(review.getUsername());
        holder.reviewRatingRb.setRating(review.getRating().floatValue());
        holder.reviewDescTv.setText(review.getDescription());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
