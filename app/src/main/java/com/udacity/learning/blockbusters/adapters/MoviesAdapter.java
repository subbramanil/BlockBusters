package com.udacity.learning.blockbusters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.learning.blockbusters.R;
import com.udacity.learning.blockbusters.model.Movie;

import java.util.ArrayList;


/**
 * Created by Subbu on 5/27/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private static final String baseURL = "http://image.tmdb.org/t/p/w500";
    private final Context context;

    //region Constructors

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
    }

    //endregion

    //region Lifecycle methods

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movieItem = getItem(position);

        MovieViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new MovieViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.movie_name_textview);
            viewHolder.movieImageView = (ImageView) convertView.findViewById(R.id.movie_poster_imageview);
            viewHolder.movieRatingBar = (RatingBar) convertView.findViewById(R.id.movie_ratingbar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MovieViewHolder) convertView.getTag();
        }

        viewHolder.movieTitle.setText(movieItem.getTitle());
        viewHolder.movieRatingBar.setRating((float) movieItem.getVoteAverage());
        //load movie poster using Picasso Image library
        Picasso.with(context).load(baseURL + movieItem.getPosterPath()).into(viewHolder.movieImageView);

        return convertView;
    }

    //endregion


    //region Classes & Interfaces declaration

    private static class MovieViewHolder {
        TextView movieTitle;
        RatingBar movieRatingBar;
        ImageView movieImageView;
    }

    //endregion

}
