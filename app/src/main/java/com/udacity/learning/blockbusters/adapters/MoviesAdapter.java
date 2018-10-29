package com.udacity.learning.blockbusters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.learning.blockbusters.AppComponent;
import com.udacity.learning.blockbusters.BlockbustersApp;
import com.udacity.learning.blockbusters.R;
import com.udacity.learning.blockbusters.model.Movie;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Subbu on 5/27/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    private static final String baseURL = "http://image.tmdb.org/t/p/w500";
    // TODO: 10/9/18 Inject #4
    private final Context context;

    //region Constructors

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        /*AppComponent appComponent = ((BlockbustersApp) context.getApplication()).getAppComponent();
        appComponent.inject(this);*/
    }

    //endregion

    //region Lifecycle methods

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MovieViewHolder viewHolder;
        Movie movieItem = getItem(position);

        View movieHolderView = convertView;

        if (movieHolderView == null) {
            viewHolder = new MovieViewHolder();
            movieHolderView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
            viewHolder.movieTitle = movieHolderView.findViewById(R.id.movie_name_text_view);
            viewHolder.movieImageView = movieHolderView.findViewById(R.id.movie_poster_image_view);
            viewHolder.movieRatingBar = movieHolderView.findViewById(R.id.movie_rating_bar);
            movieHolderView.setTag(viewHolder);
        } else {
            viewHolder = (MovieViewHolder) movieHolderView.getTag();
        }

        viewHolder.movieTitle.setText(Objects.requireNonNull(movieItem).getTitle());
        viewHolder.movieRatingBar.setRating((float) movieItem.getVoteAverage());
        //load movie poster using Picasso Image library
        Picasso.with(context).load(baseURL + movieItem.getPosterPath()).into(viewHolder.movieImageView);

        return movieHolderView;
    }

    //endregion


    //region Classes & Interfaces declaration

    private static class MovieViewHolder {
        private TextView movieTitle;
        private RatingBar movieRatingBar;
        private ImageView movieImageView;
    }

    //endregion

}
