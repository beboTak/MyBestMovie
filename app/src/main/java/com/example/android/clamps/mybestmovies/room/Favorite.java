package com.example.android.clamps.mybestmovies.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.clamps.mybestmovies.model.Review;
import com.example.android.clamps.mybestmovies.model.Trailer;

import java.util.List;

@Entity(tableName = "favorite_table",indices = {@Index(value = {"title", "poster_path"}, unique = true)})
public class Favorite {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private String releaseDate;
    private float rating;
    private int runtime;
    private boolean fav;

    public Favorite(int id,String title, String poster_path,String overview, String releaseDate, float rating, int runtime,boolean fav) {
        this.id=id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview=overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.runtime = runtime;
        this.fav=fav;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public int getRuntime() {
        return runtime;
    }
    public void setFav(Boolean fav)
    {
        this.fav=fav;
    }
    public boolean isFav()
    {
        return fav;
    }
}
