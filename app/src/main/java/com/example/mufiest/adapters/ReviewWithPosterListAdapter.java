package com.example.mufiest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import com.example.mufiest.views.MovieCardViewHolder;
import com.example.mufiest.views.ReviewCardWithPosterViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewWithPosterListAdapter extends RecyclerView.Adapter<ReviewCardWithPosterViewHolder> {

    private ArrayList<ReviewWithDetail> reviews;
    private LayoutInflater mInflater;

    public ReviewWithPosterListAdapter(Context ctx, ArrayList<ReviewWithDetail> reviews) {
        this.mInflater = LayoutInflater.from(ctx);
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewCardWithPosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.review_card_with_poster, parent, false);
        return new ReviewCardWithPosterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewCardWithPosterViewHolder holder, int position) {
        ReviewWithDetail review = reviews.get(position);
        String moviePosterUrl = review.getMoviePosterUrl();
        holder.movieTitleTv.setText(review.getMovieName());
        holder.movieYearTv.setText("(" + String.valueOf(review.getMovieYear()) + ")");
        holder.reviewerTv.setText(review.getUsername());
        holder.reviewRatingRb.setRating(review.getRating().floatValue());
        holder.reviewDescTv.setText(review.getDescription());
        Picasso.get().load(moviePosterUrl).into(holder.moviePosterIv);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
