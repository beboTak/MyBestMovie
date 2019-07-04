package com.example.android.clamps.mybestmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCall;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetReviewsCallback;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetTrailersCallback;
import com.example.android.clamps.mybestmovies.model.Movie;
import com.example.android.clamps.mybestmovies.model.MoviesRepository;
import com.example.android.clamps.mybestmovies.model.Review;
import com.example.android.clamps.mybestmovies.model.Trailer;
import com.example.android.clamps.mybestmovies.room.DetailViewModel;
import com.example.android.clamps.mybestmovies.room.Favorite;
import com.example.android.clamps.mybestmovies.room.FavoriteViewModel;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.util.List;
import java.util.zip.Inflater;

public class DetailsActivity extends AppCompatActivity {
    private MoviesRepository moviesRepository;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";
    public static String MOVIE_ID = "movie_id";
    private int movieId;
    private TextView trailersLabel;
    private TextView reviewsLabel;
    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private TextView overView;
    private TextView runtime;
    private LinearLayout movieTrailers;
    private LinearLayout movieReviews;
    private ImageButton favorite;
    private int test=0;
    private FavoriteViewModel favoriteViewModel;
    private static DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("MovieDetail");
        movieId = getIntent().getIntExtra(MOVIE_ID,movieId);
        moviesRepository = MoviesRepository.getInstance();
        favoriteViewModel= ViewModelProviders.of(this).get(FavoriteViewModel.class);

        initUI();
        getMovie();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else if (id==R.id.action_favorite)
        {

          makeFavorite();
        }


        return super.onOptionsItemSelected(item);
    }
    private void getMovie() {
        moviesRepository.getMovie(movieId, new OnGetMovieCall() {
            @Override
            public void onSuccess(final Movie movie) {
                movieTitle.setText(movie.getTitle());

                movieRating.setVisibility(View.VISIBLE);
                movieRating.setRating(movie.getRating() / 2);
                runtime.setText(String.valueOf(movie.getRuntime()+"min"));
                movieReleaseDate.setText(movie.getReleaseDate().split("-")[0]);
                String im=IMAGE_BASE_URL+movie.getPosterPath();
                overView.setText(movie.getOverview());
                Picasso.get().load(im).into(movieBackdrop);
                getTrailers(movie);
                getReviews(movie);

                ////***********************************************
                final Favorite f1=new Favorite(movie.getId(),movie.getTitle(),movie.getPosterPath(),movie.getOverview(),movie.getReleaseDate(),movie.getRating(),movie.getRuntime(),false);

                detailViewModel=new DetailViewModel(getApplication(),movie.getId());
                detailViewModel.searchMovie().observe(DetailsActivity.this, new Observer<Favorite>() {

                    @Override
                    public void onChanged(@Nullable Favorite favorite) {

                        if (favorite==null)
                        {

                            f1.setFav(false);
                            // favoriteViewModel.insert(f1);
                            // Toast.makeText(DetailsActivity.this,"Movie Added As Favorite ",Toast.LENGTH_SHORT).show();
                        }
                        else if (favorite!=null)
                        {
                            f1.setFav(true);
                            favorite.setFav(true);
                            //favoriteViewModel.delete(favorite);
                            // Toast.makeText(DetailsActivity.this,"Movie Deleted ",Toast.LENGTH_SHORT).show();
                        }

                    }

                });
                //  Favorite favorite=new Favorite(movie.getId(),movie.getTitle(),movie.getPosterPath(),movie.getOverview(),movie.getReleaseDate(),movie.getRating(),movie.getRuntime());


                ////***************************************************

                favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (f1.isFav())
                        {
                            favoriteViewModel.delete(f1);
                            Toast.makeText(DetailsActivity.this,"Movie Deleted",Toast.LENGTH_SHORT).show();
                        }
                        else if(!(f1.isFav())) {
                            favoriteViewModel.insert(f1);
                            Toast.makeText(DetailsActivity.this,"Movie Added As Favorite",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onError() {
               finish();
            }
        });

    }
    private static int x;
    private void makeFavorite(){
        //Toast.makeText(DetailsActivity.this,String.valueOf(x),Toast.LENGTH_SHORT).show();
        detailViewModel=new DetailViewModel(getApplication(),movieId);
        detailViewModel.searchMovie().observe(DetailsActivity.this, new Observer<Favorite>() {
            @Override
            public void onChanged(@Nullable Favorite favorite) {
                if (favorite==null)
                {
                    x=0;
                }
                else
                {
                    x=1;
                }
            }
        });

        moviesRepository.getMovie(movieId, new OnGetMovieCall() {
            @Override
            public void onSuccess(final Movie movie)
                    {
                     Favorite fav=new Favorite(movie.getId(),movie.getTitle(),movie.getPosterPath(),movie.getOverview(),movie.getReleaseDate(),movie.getRating(),movie.getRuntime(),false);
                        if (x==1)
                        {
                            fav.setFav(false);
                            favoriteViewModel.delete(fav);
                            Toast.makeText(DetailsActivity.this,"Movie Deleted",Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            fav.setFav(true);
                            favoriteViewModel.insert(fav);
                            Toast.makeText(DetailsActivity.this,"Movie Added As Favorite",Toast.LENGTH_SHORT).show();
                        }



                    }

            @Override
            public void onError()
            {
                finish();
            }
        });
    }
    private void initUI() {
        movieBackdrop = findViewById(R.id.movie_details_poster);
        movieTitle = findViewById(R.id.movie_details_title);
        movieReleaseDate = findViewById(R.id.movie_details_date);
        movieRating = findViewById(R.id.movie_details_rating);
        runtime=findViewById(R.id.movie_details_time);
        overView=findViewById(R.id.movie_details_overview);
        trailersLabel = findViewById(R.id.trailersLabel);
        movieTrailers = findViewById(R.id.movieTrailers);
        movieReviews = findViewById(R.id.movieReviews);
        reviewsLabel = findViewById(R.id.reviewsLabel);
        favorite=findViewById(R.id.movie_button_favorite);
    }
    private void getTrailers(Movie movie) {
        moviesRepository.getTrailers(movie.getId(), new OnGetTrailersCallback() {
            @Override
            public void onSuccess(List<Trailer> trailers) {
                trailersLabel.setVisibility(View.VISIBLE);
                movieTrailers.removeAllViews();
                for (final Trailer trailer : trailers) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer, movieTrailers, false);
                    ImageView thumbnail = parent.findViewById(R.id.thumbnail);
                    thumbnail.requestLayout();
                    thumbnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showTrailer(String.format(YOUTUBE_VIDEO_URL, trailer.getKey()));
                        }
                    });
                    Glide.with(DetailsActivity.this)
                            .load(String.format(YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                            .into(thumbnail);
                    movieTrailers.addView(parent);
                }
            }
            @Override
            public void onError() {
                // Do nothing
                trailersLabel.setVisibility(View.GONE);
            }
        });
    }

    private void showTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private void getReviews(Movie movie) {
        moviesRepository.getReviews(movie.getId(), new OnGetReviewsCallback() {
            @Override
            public void onSuccess(List<Review> reviews) {
                reviewsLabel.setVisibility(View.VISIBLE);
                movieReviews.removeAllViews();
                for (Review review : reviews) {
                    View parent = getLayoutInflater().inflate(R.layout.review, movieReviews, false);
                    TextView author = parent.findViewById(R.id.reviewAuthor);
                    TextView content = parent.findViewById(R.id.reviewContent);
                    author.setText(review.getAuthor());
                    content.setText(review.getContent());
                    movieReviews.addView(parent);
                }
            }

            @Override
            public void onError() {
                // Do nothing
            }
        });
    }

}



