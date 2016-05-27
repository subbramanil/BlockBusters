package com.udacity.learning.blockbusters.model;

/**
 * Created by Subbu on 5/27/16.
 */
public class Movie {

    private String movieTitle;
    private String moviePosterName;
    private int movieRating;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePosterName() {
        return moviePosterName;
    }

    public void setMoviePosterName(String moviePosterName) {
        this.moviePosterName = moviePosterName;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(int movieRating) {
        this.movieRating = movieRating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieTitle='" + movieTitle + '\'' +
                ", moviePosterName='" + moviePosterName + '\'' +
                ", movieRating=" + movieRating +
                '}';
    }
}
