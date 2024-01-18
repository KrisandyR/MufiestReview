package com.example.mufiest.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;

public class ReviewCardWithPosterViewHolder extends RecyclerView.ViewHolder  {
    public TextView movieTitleTv;
    public TextView movieYearTv;
    public TextView reviewerTv;
    public RatingBar reviewRatingRb;
    public ImageView moviePosterIv;
    public TextView reviewDescTv;

    public ReviewCardWithPosterViewHolder(View itemView) {
        super(itemView);
        movieTitleTv = itemView.findViewById(R.id.tv_movie_title_rcwp);
        movieYearTv = itemView.findViewById(R.id.tv_movie_year_rcwp);
        reviewerTv = itemView.findViewById(R.id.tv_reviewer_rcwp);
        reviewRatingRb = itemView.findViewById(R.id.rb_review_rating_rcwp);
        moviePosterIv = itemView.findViewById(R.id.iv_movie_poster_rcwp);
        reviewDescTv = itemView.findViewById(R.id.tv_review_desc_rcwp);
    }
}
