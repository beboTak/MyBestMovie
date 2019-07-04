package com.example.android.clamps.mybestmovies.REtrofit;

import com.example.android.clamps.mybestmovies.model.Movie;

public interface OnGetMovieCall {
    void onSuccess(Movie movie);

    void onError();
}
