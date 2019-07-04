package com.example.android.clamps.mybestmovies.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void favoriteInsert(Favorite favorite);
    @Update
    void favoriteUpdate(Favorite favorite);
    @Delete
    void favoriteDelete(Favorite favorite);
    @Query("DELETE FROM favorite_table")
    void deleteAllFavorites();
    @Query("SELECT * FROM favorite_table  ")
   LiveData<List<Favorite>>getAllFavorite();
    @Query("SELECT * FROM favorite_table WHERE id==:mid")
    LiveData<Favorite>searchMovie(int mid);
}
