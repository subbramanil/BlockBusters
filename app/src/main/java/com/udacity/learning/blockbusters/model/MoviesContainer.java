package com.udacity.learning.blockbusters.model;

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
     * @return The page
     */
    public long getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(long page) {
        this.page = page;
    }

    public MoviesContainer withPage(long page) {
        this.page = page;
        return this;
    }

    /**
     * @return The Movies
     */
    public List<Movie> getMovies() {
        return Movies;
    }

    /**
     * @param Movies The Movies
     */
    public void setMovies(List<Movie> Movies) {
        this.Movies = Movies;
    }

    public MoviesContainer withMovies(List<Movie> Movies) {
        this.Movies = Movies;
        return this;
    }

    @Override
    public String toString() {
        return "MoviesContainer{" +
                "page=" + page +
                ", Movies=" + Movies +
                '}';
    }
}
