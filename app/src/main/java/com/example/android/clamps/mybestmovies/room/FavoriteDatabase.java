package com.example.android.clamps.mybestmovies.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.clamps.mybestmovies.model.Review;
import com.example.android.clamps.mybestmovies.model.Trailer;

@Database(entities = {Favorite.class},version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static synchronized FavoriteDatabase getInstance(final Context context){
        if (instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),FavoriteDatabase.class,"favorite_database")
                    .fallbackToDestructiveMigration()
                   .addCallback(roomCallback)
                    .build();
        }
       return instance;
    }

  public static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
          new PopulateDbasynctask(instance).execute();
      }
  };
    public static class PopulateDbasynctask extends AsyncTask<Void,Void,Void>
    {
        private final FavoriteDao favoriteDao;
        private PopulateDbasynctask(FavoriteDatabase database)
        {
            favoriteDao=database.favoriteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           // favoriteDao.deleteAllFavorites();
           // favoriteDao.favoriteInsert(new Favorite("Chapie","http://image.tmdb.org/t/p/w500\\w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg","2015",8.1f,120));
            return null;
        }

}

 }
