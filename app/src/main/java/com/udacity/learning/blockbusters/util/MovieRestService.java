package com.udacity.learning.blockbusters.util;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor;
import com.udacity.learning.blockbusters.model.MoviesContainer;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Subbu on 5/28/16.
 */
public class MovieRestService {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String TAG = MovieRestService.class.getSimpleName();
    private static MovieRestService INSTANCE;
    private Retrofit retrofit;
    private MovieAPIInterface movieAPIInterface;

    private MovieRestService() {
        retrofit = createRestService();
    }

    public static MovieRestService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRestService();
        }
        return INSTANCE;
    }

    /**
     * Method to create Retrofit instance & initialize
     *
     * @return Retrofit instance
     */
    private Retrofit createRestService() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();

        //logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new OkHttpProfilerInterceptor())
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
     * @param sortOrder  : Movie Sort order {"Most Popular", "User Rating"}
     * @param apiKey     : API KEY for TMDB
     * @param pageNumber : Page number
     */
    public MoviesContainer getMoviesList(String sortOrder, String apiKey, String pageNumber) {
        Log.d(TAG, "getMoviesList: Sort order: " + sortOrder);

        MoviesContainer moviesContainer = null;
        Call<MoviesContainer> call = movieAPIInterface.fetchMoviesList(sortOrder, apiKey, pageNumber);

        try {
            // Synchronous API Call
            Response<MoviesContainer> response = call.execute();
            if (response != null) {
                moviesContainer = response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return moviesContainer;
    }
}
