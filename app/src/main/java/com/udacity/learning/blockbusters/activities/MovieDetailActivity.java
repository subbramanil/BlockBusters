package com.udacity.learning.blockbusters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.learning.blockbusters.R;
import com.udacity.learning.blockbusters.model.Movie;

import java.text.MessageFormat;
import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private static final String SELECTED_MOVIE = "selected_movie";
    private static final String baseURL = "http://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent recdIntent = getIntent();
        Movie movie = recdIntent.getParcelableExtra(SELECTED_MOVIE);
        Log.d(TAG, "onCreate: Selected movie is: " + movie.getTitle());

        CollapsingToolbarLayout collapsibleToolbar = findViewById(R.id.toolbar_layout);
        ImageView moviePoster = findViewById(R.id.movie_poster_background);
        TextView movieReleaseDate = findViewById(R.id.movie_releaseDate);
        TextView movieOverView = findViewById(R.id.movie_overview);
        RatingBar movieRating = findViewById(R.id.movie_rating);

        collapsibleToolbar.setTitle(movie.getTitle());
        // Load Poster using Picasso library
        Picasso.with(this).load(baseURL + movie.getPosterPath()).into(moviePoster);
        movieOverView.setText(movie.getOverview());
        movieReleaseDate.setText(MessageFormat.format("Release Date: {0}", movie.getReleaseDate()));
        movieRating.setRating((float) movie.getVoteAverage());

    }
}
