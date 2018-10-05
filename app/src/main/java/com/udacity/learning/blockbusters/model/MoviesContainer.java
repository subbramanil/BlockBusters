package com.udacity.learning.blockbusters.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subbu on 5/28/16.
 */
public class MoviesContainer {

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<Movie> Movies = new ArrayList<>();

    /**
     * @return The Movies
     */
    public List<Movie> getMovies() {
        return Movies;
    }

    @NonNull
    @Override
    public String toString() {
        return "MoviesContainer{" +
                "page=" + page +
                ", Movies=" + Movies +
                '}';
    }
}
