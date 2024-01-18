package com.example.mufiest.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mufiest.R;
import com.example.mufiest.models.Movie;
import com.example.mufiest.views.MovieCardViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieScrollListAdapter extends RecyclerView.Adapter<MovieCardViewHolder> {

    private ArrayList<Movie> movies;
    private LayoutInflater mInflater;

    public MovieScrollListAdapter(Context ctx, ArrayList<Movie> movies) {
        this.mInflater = LayoutInflater.from(ctx);
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movie_card, parent, false);
        return new MovieCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCardViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String moviePosterUrl = movie.getPosterUrl();
        Picasso.get().load(moviePosterUrl).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
