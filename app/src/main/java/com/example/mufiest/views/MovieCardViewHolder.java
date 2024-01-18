package com.example.mufiest.views;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;

public class MovieCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView posterImageView;

    public MovieCardViewHolder(View itemView) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.moviePosterImage);
        posterImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
