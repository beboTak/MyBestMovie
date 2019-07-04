package com.example.android.clamps.mybestmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.clamps.mybestmovies.R;
import com.example.android.clamps.mybestmovies.REtrofit.OnMoviesClickCallback;
import com.example.android.clamps.mybestmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private OnMoviesClickCallback callback;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MoviesAdapter(List<Movie> movies,OnMoviesClickCallback callback) {
        this.movies = movies;
        this.callback=callback;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder  {
        Movie movie;

        ImageView im;


        public MovieViewHolder(View itemView) {
            super(itemView);

            im=itemView.findViewById(R.id.item_movie_poster);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   callback.onClick(movie);
               }
           });
        }

        public void bind(Movie movie) {
            this.movie=movie;
            String s=IMAGE_BASE_URL+movie.getPosterPath();
            Picasso.get().load(s).into(im);

        }


    }

}
