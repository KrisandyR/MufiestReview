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
import com.example.mufiest.views.GenreCardViewHolder;
import com.example.mufiest.views.MovieCardViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GenreListAdapter extends RecyclerView.Adapter<GenreCardViewHolder> {

    private ArrayList<String> genres;
    private LayoutInflater mInflater;

    public GenreListAdapter(Context ctx, ArrayList<String> genres) {
        this.mInflater = LayoutInflater.from(ctx);
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.genre_card, parent, false);
        return new GenreCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreCardViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.genreTv.setText(genre);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

}
