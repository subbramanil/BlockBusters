package com.udacity.learning.blockbusters.util;

import com.udacity.learning.blockbusters.model.MoviesContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Subbu on 5/28/16.
 */
public interface MovieAPIInterface {
    @GET("movie/{sort}")
    Call<MoviesContainer> fetchMoviesList(@Path("sort") String sortOrder, @Query("api_key") String key, @Query("page") String pageNumber);
}

