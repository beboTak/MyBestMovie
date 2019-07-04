package com.example.android.clamps.mybestmovies;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.clamps.mybestmovies.REtrofit.OnFavoriteClickCallBack;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCall;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCallback;
import com.example.android.clamps.mybestmovies.REtrofit.OnMoviesClickCallback;
import com.example.android.clamps.mybestmovies.adapter.FavoriteAdapter;
import com.example.android.clamps.mybestmovies.adapter.MoviesAdapter;
import com.example.android.clamps.mybestmovies.model.Movie;
import com.example.android.clamps.mybestmovies.model.MoviesRepository;
import com.example.android.clamps.mybestmovies.room.Favorite;
import com.example.android.clamps.mybestmovies.room.FavoriteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;
    private MoviesRepository moviesRepository;
    private int movieId;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteViewModel favoriteViewModel;
    public static String MOVIE_ID = "movie_id";
    private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteViewModel= ViewModelProviders.of(this).get(FavoriteViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings)
        {
            Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popular(){
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();
        // dialog.dismiss();

        moviesRepository = MoviesRepository.getInstance();

        moviesList = findViewById(R.id.movie_list);
        GridLayoutManager lim=new GridLayoutManager (MainActivity.this,2);
        lim.setOrientation(GridLayoutManager.VERTICAL);
        moviesList.setLayoutManager(lim);

        moviesRepository.getpopularMovies(new OnGetMovieCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {

                adapter = new MoviesAdapter(movies, new OnMoviesClickCallback() {
                    @Override
                    public void onClick(Movie movie) {

                        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                        intent.putExtra(MainActivity.MOVIE_ID, movie.getId());
                        startActivity(intent);
                    }
                });
                moviesList.setAdapter(adapter);
                //adapter.setOnMovieListner(MainActivity.this);
                dialog.dismiss();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

            }

        });


    }
    public void getTopRated()
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();
        // dialog.dismiss();

        moviesRepository = MoviesRepository.getInstance();

        moviesList = findViewById(R.id.movie_list);
        GridLayoutManager lim=new GridLayoutManager (MainActivity.this,2);
        lim.setOrientation(GridLayoutManager.VERTICAL);
        moviesList.setLayoutManager(lim);

        moviesRepository.getTopRatedMovies(new OnGetMovieCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MoviesAdapter(movies, new OnMoviesClickCallback() {
                    @Override
                    public void onClick(Movie movie) {
                        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                        intent.putExtra(MainActivity.MOVIE_ID, movie.getId());
                        startActivity(intent);
                    }
                });
                moviesList.setAdapter(adapter);
                // adapter.setOnMovieListner(MainActivity.this);
                dialog.dismiss();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

            }

        });
    }
    public void getFavorite(){
        moviesList = findViewById(R.id.movie_list);
        GridLayoutManager lim=new GridLayoutManager (MainActivity.this,2);
        lim.setOrientation(GridLayoutManager.VERTICAL);
        moviesList.setLayoutManager(lim);
        favoriteViewModel= ViewModelProviders.of(this).get(FavoriteViewModel.class);

        favoriteViewModel.getAllFavorite().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                favoriteAdapter=new FavoriteAdapter(favorites, new OnFavoriteClickCallBack() {
                    @Override
                    public void onClick(Favorite favorite) {
                        Intent intent =new Intent(MainActivity.this,FavoriteDetailsActivity.class);
                        intent.putExtra(MOVIE_ID,favorite.getId());
                        intent.putExtra("title",favorite.getTitle());
                        intent.putExtra("poster",favorite.getPoster_path());
                        intent.putExtra("date",favorite.getReleaseDate());
                        intent.putExtra("overview",favorite.getOverview());
                        intent.putExtra("time",favorite.getRuntime());
                        intent.putExtra("rate",favorite.getRating());

                        startActivity(intent);
                    }

                });
                moviesList.setAdapter(favoriteAdapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //String defaultValue = "-1"; // assign some meaningful default value
        //int amountPlayers = Integer.parseInt(sharedPref.getString("movie_orrder", defaultValue));
        String s=sharedPref.getString("movie_orrder","");
        if (s.equals("popular"))
        {
            popular();
        }
        else if (s.equals("top_rated"))
        {
            getTopRated();
        }
        else if(s.equals("favoriate"))
        {
            getFavorite();
            //Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show();
        }

    }


}
