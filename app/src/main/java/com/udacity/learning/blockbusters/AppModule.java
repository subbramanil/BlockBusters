package com.udacity.learning.blockbusters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.udacity.learning.blockbusters.adapters.MoviesAdapter;
import com.udacity.learning.blockbusters.model.Movie;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public MoviesAdapter provideMovieAdapter() {
        return new MoviesAdapter(context, new ArrayList<Movie>());
    }

    @Provides
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
