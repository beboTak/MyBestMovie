package com.example.android.clamps.mybestmovies.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository favoriteRepository;
    private LiveData<List<Favorite>>allFavorites;

    public FavoriteViewModel( Application application) {
        super(application);
        favoriteRepository=new FavoriteRepository(application);
        allFavorites=favoriteRepository.getAllFavorite();
    }
    public void insert(Favorite favorite)
    {
        favoriteRepository.insert(favorite);
    }
    public void update(Favorite favorite){
        favoriteRepository.update(favorite);
    }
    public void delete(Favorite favorite){
        favoriteRepository.delete(favorite);
    }
    public void deletAllFavorite()
    {
        favoriteRepository.deleteAllFavorite();
    }
    public LiveData<List<Favorite>>getAllFavorite()
    {
        return allFavorites;
    }
}
