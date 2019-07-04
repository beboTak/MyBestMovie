package com.example.android.clamps.mybestmovies.REtrofit;

import com.example.android.clamps.mybestmovies.model.Movie;

import java.util.List;

public interface OnGetMovieCallback {
    void onSuccess(List<Movie>movies);

    void onError();
}
