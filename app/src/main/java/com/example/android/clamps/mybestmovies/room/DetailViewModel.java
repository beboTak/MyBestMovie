package com.example.android.clamps.mybestmovies.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {
    private final LiveData<Favorite> mMovieLiveData;

    public DetailViewModel(Application application,int mid)
    {

        DetailRepository detailRepository=new DetailRepository(application,mid);
        mMovieLiveData=detailRepository.searchMovie();
    }
    public LiveData<Favorite> searchMovie(){return mMovieLiveData;}



}
