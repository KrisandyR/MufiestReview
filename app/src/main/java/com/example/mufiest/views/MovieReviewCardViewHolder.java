package com.example.mufiest.views;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;

public class MovieReviewCardViewHolder extends RecyclerView.ViewHolder {
    public TextView reviewerTv;
    public TextView reviewDescTv;
    public RatingBar reviewRatingRb;
    public MovieReviewCardViewHolder(View itemView) {
        super(itemView);
        reviewerTv = itemView.findViewById(R.id.tv_reviewer_mrc);
        reviewDescTv = itemView.findViewById(R.id.tv_review_desc_mrc);
        reviewRatingRb = itemView.findViewById(R.id.rb_review_rating_mrc);
    }
}
