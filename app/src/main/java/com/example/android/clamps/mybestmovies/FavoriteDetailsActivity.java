package com.example.android.clamps.mybestmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetMovieCall;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetReviewsCallback;
import com.example.android.clamps.mybestmovies.REtrofit.OnGetTrailersCallback;
import com.example.android.clamps.mybestmovies.model.Movie;
import com.example.android.clamps.mybestmovies.model.MoviesRepository;
import com.example.android.clamps.mybestmovies.model.Review;
import com.example.android.clamps.mybestmovies.model.Trailer;
import com.example.android.clamps.mybestmovies.room.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.clamps.mybestmovies.DetailsActivity.MOVIE_ID;

public class FavoriteDetailsActivity extends AppCompatActivity {
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";
    private MoviesRepository moviesRepository;
    private TextView trailersLabel;
    private TextView reviewsLabel;
    private ImageView movieBackdrop;
    private TextView favoriteTitle;
    private TextView overview;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private LinearLayout movieTrailers;
    private LinearLayout movieReviews;
    private TextView runtime;
    private String title,poster_path,release_date,soverview;
    private float rating;
    private int intruntime;
    private String s;
    private Movie movie;
    private int movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_details);
        movieId = getIntent().getIntExtra(DetailsActivity.MOVIE_ID,movieId);

        moviesRepository = MoviesRepository.getInstance();
        setTitle("FavoriteDetail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



       initUI();
       getFavorite();
       getMovie();

    }
    private void initUI() {
        movieBackdrop = findViewById(R.id.favorite_details_poster);
        favoriteTitle = findViewById(R.id.favorite_details_title);
        movieReleaseDate = findViewById(R.id.favorite_details_date);
        movieRating = findViewById(R.id.favorite_details_rating);
        runtime=findViewById(R.id.favorite_details_time);
        overview=findViewById(R.id.favorite_details_overview);
        trailersLabel=findViewById(R.id.trailersfaLabel);
        reviewsLabel=findViewById(R.id.reviewsfaLabel);
        movieTrailers=findViewById(R.id.favoriteTrailers);
        movieReviews=findViewById(R.id.favoriteReviews);
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
    private void getTrailers(Movie movie) {
        moviesRepository.getTrailers(movie.getId(), new OnGetTrailersCallback() {
            @Override
            public void onSuccess(List<Trailer> trailers) {
                trailersLabel.setVisibility(View.VISIBLE);
                movieTrailers.removeAllViews();
                for (final Trailer trailer : trailers) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer,movieTrailers, false);
                    ImageView thumbnail = parent.findViewById(R.id.thumbnail);
                    thumbnail.requestLayout();
                    thumbnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showTrailer(String.format(YOUTUBE_VIDEO_URL, trailer.getKey()));
                        }
                    });
                    Glide.with(FavoriteDetailsActivity.this)
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
    private void getMovie() {
        moviesRepository.getMovie(movieId, new OnGetMovieCall() {
            @Override
            public void onSuccess(final Movie movie) {
                getTrailers(movie);
                getReviews(movie);

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
                        reviewsLabel.setVisibility(View.GONE);

                    }
                });
            }

            @Override
            public void onError() {

            }
        });

    }
    private void getFavorite()
    {
        Bundle intent=getIntent().getExtras();
        title=intent.getString("title");
        poster_path=intent.getString("poster");
        release_date=intent.getString("date");
        soverview=intent.getString("overview");
        intruntime=intent.getInt("time");
        rating=intent.getFloat("rate");

        favoriteTitle.setText(title);
        movieReleaseDate.setText(release_date.split("-")[0]);
        movieRating.setVisibility(View.VISIBLE);
        movieRating.setRating(rating/2);
        runtime.setText(intruntime+"min");
        overview.setText(soverview);
        Picasso.get().load(IMAGE_BASE_URL+poster_path).into(movieBackdrop);



    }
}
