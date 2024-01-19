package com.example.mufiest.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;

public class GenreCardViewHolder extends RecyclerView.ViewHolder {

    public TextView genreTv;

    public GenreCardViewHolder(View itemView) {
        super(itemView);
        genreTv = itemView.findViewById(R.id.tv_movie_genre_card);
    }
}
