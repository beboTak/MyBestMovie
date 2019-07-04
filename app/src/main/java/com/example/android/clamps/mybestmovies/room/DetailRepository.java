package com.example.android.clamps.mybestmovies.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;

public class DetailRepository {
    private FavoriteDao favoriteDao;
    private LiveData<Favorite> mDetailLiveData;
    public DetailRepository(Application application, int movieId)
    {

        FavoriteDatabase database =FavoriteDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();

        mDetailLiveData=favoriteDao.searchMovie(movieId);
    }

    LiveData<Favorite> searchMovie(){return mDetailLiveData;}
}
