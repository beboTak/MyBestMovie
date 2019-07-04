package com.example.android.clamps.mybestmovies.model;

import android.support.annotation.NonNull;

import com.example.android.clamps.mybestmovies.REtrofit.FetchApi;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCall;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCallback;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCallback;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetReviewsCallback;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetTrailersCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    private static MoviesRepository repository;

    private FetchApi api;

    private MoviesRepository(FetchApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(FetchApi.class));
        }

        return repository;
    }

    public void getpopularMovies(final OnGetMovieCallback callback) {
        api.getPopularMovies("8be170b83fbdde6532a95030711df202", LANGUAGE, 1)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
    public void getTopRatedMovies(final OnGetMovieCallback callback){
        api.getTopRatedMovies("8be170b83fbdde6532a95030711df202", LANGUAGE, 1)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError();
                    }
                });


    }
    public void getReviews(int movieId, final OnGetReviewsCallback callback) {
        api.getReviews(movieId,"8be170b83fbdde6532a95030711df202",LANGUAGE)
                .enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        if (response.isSuccessful()) {
                            ReviewResponse reviewResponse = response.body();
                            if (reviewResponse != null && reviewResponse.getReviews() != null) {
                                callback.onSuccess(reviewResponse.getReviews());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
    public void getTrailers(int movieId, final OnGetTrailersCallback callback){
        api.getTrailers(movieId,"8be170b83fbdde6532a95030711df202",LANGUAGE)
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        if (response.isSuccessful())
                        {
                            TrailerResponse trailerResponse=response.body();
                            if (trailerResponse!=null && trailerResponse.getTrailers()!=null)
                            {
                                callback.onSuccess(trailerResponse.getTrailers());
                            }
                            else {
                                callback.onError();
                            }
                        }
                        else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        callback.onError();

                    }
                });


    }
    public void getMovie(int movieId, final OnGetMovieCall callback) {
        api.getMovie(movieId, "8be170b83fbdde6532a95030711df202", LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onError();
                    }
                });
    }


}
