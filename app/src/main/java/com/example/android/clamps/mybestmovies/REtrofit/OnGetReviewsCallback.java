package com.example.android.clamps.mybestmovies.REtrofit;

import com.example.android.clamps.mybestmovies.model.Review;

import java.util.List;

public interface OnGetReviewsCallback {
    void onSuccess(List<Review> reviews);

    void onError();
}
