package com.example.android.clamps.mybestmovies;

import android.app.ProgressDialog;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCallback;
import com.example.android.clamps.mybestmovies.adapter.MoviesAdapter;
import com.example.android.clamps.mybestmovies.model.Movie;
import com.example.android.clamps.mybestmovies.model.MoviesRepository;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView moviesList;
    private MoviesAdapter adapter;
    ProgressDialog pd;

    private MoviesRepository moviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }




}
