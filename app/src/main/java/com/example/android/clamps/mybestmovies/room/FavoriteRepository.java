package com.example.android.clamps.mybestmovies.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavoriteRepository {

    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>>allFavorite;
    public FavoriteRepository(Application application)
    {
        FavoriteDatabase database=FavoriteDatabase.getInstance(application);
        favoriteDao=database.favoriteDao();
        allFavorite=favoriteDao.getAllFavorite();
    }
    public void insert(Favorite favorite)
    {
        new InsertFavoriteAsyncTask(favoriteDao).execute(favorite);

    }
    public void update(Favorite favorite){
        new UpdateFavoriteAsyncTask(favoriteDao).execute(favorite);
    }
    public void delete(Favorite favorite){
        new DeleteFavoriteAsyncTask(favoriteDao).execute(favorite);

    }
    public void deleteAllFavorite()
    {
        new DeletAllFavoriteAsyncTask(favoriteDao).execute();
    }
    public LiveData<List<Favorite>>getAllFavorite(){
        return allFavorite;
    }
    public static class InsertFavoriteAsyncTask extends AsyncTask<Favorite,Void,Void>{

        private FavoriteDao afavoriteDao;

     private InsertFavoriteAsyncTask(FavoriteDao favoriteDao)
     {
         this.afavoriteDao=favoriteDao;
     }
        @Override
        protected Void doInBackground(Favorite... favorites) {
            afavoriteDao.favoriteInsert(favorites[0]);
            return null;

        }


    }
    public static class UpdateFavoriteAsyncTask extends AsyncTask<Favorite,Void,Void>{

        private FavoriteDao afavoriteDao;

        private UpdateFavoriteAsyncTask(FavoriteDao favoriteDao)
        {
            this.afavoriteDao=favoriteDao;
        }
        @Override
        protected Void doInBackground(Favorite... favorites) {
            afavoriteDao.favoriteUpdate(favorites[0]);
            return null;

        }


    }
    public static class DeleteFavoriteAsyncTask extends AsyncTask<Favorite,Void,Void>{

        private FavoriteDao afavoriteDao;

        private DeleteFavoriteAsyncTask(FavoriteDao favoriteDao)
        {
            this.afavoriteDao=favoriteDao;
        }
        @Override
        protected Void doInBackground(Favorite... favorites) {
            afavoriteDao.favoriteDelete(favorites[0]);
            return null;

        }


    }
    public static class DeletAllFavoriteAsyncTask extends AsyncTask<Void,Void,Void>{
        private FavoriteDao afavoriteDao;

        private DeletAllFavoriteAsyncTask(FavoriteDao favoriteDao)
        {
            this.afavoriteDao=favoriteDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            afavoriteDao.deleteAllFavorites();
            return null;
        }
    }
}
