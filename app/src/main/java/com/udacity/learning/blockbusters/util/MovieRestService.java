package com.udacity.learning.blockbusters.util;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.learning.blockbusters.model.Movie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Subbu on 5/28/16.
 */
public class MovieRestService {

    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String TAG = MovieRestService.class.getSimpleName();
    private static MovieRestService INSTANCE;
    Gson gson;
    Retrofit retrofit;
    MovieAPIInterface movieAPIInterface;

    private MovieRestService() {
        retrofit = createRestService();
        gson = new Gson();
    }

    public static MovieRestService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRestService();
        }
        return INSTANCE;
    }

    private Retrofit createRestService() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();


        //logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        movieAPIInterface = retrofit.create(MovieAPIInterface.class);

        return retrofit;
    }

    /**
     * Method to make the Rest API call to the TMDB API service and the list of movies
     *
     * @param apiKey    : API KEY for TMDB
     * @param sortOrder : Movie Sort order {"Most Popular", "User Rating"}
     */
    public void getMoviesList(String apiKey, String sortOrder) {
        Log.d(TAG, "getMoviesList: Sort order: " + sortOrder);
        Call<ArrayList<Movie>> call = movieAPIInterface.fetchMoviesList(apiKey);
        call.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                int statusCode = response.code();
                Log.d(TAG, "onResponse: ResponseCode: " + statusCode);
                List<Movie> movies = response.body();
                Log.d(TAG, "onResponse: Movies: " + movies);
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {

            }
        });

        Log.d(TAG, "getMoviesList: Async call done");
    }
}
