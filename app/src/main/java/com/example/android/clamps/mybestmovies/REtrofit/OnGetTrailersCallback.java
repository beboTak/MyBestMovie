package com.example.android.clamps.mybestmovies.REtrofit;

import com.example.android.clamps.mybestmovies.model.Trailer;

import java.util.List;

public interface OnGetTrailersCallback {
    void onSuccess(List<Trailer> trailers);

    void onError();
}
