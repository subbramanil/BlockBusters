package com.udacity.learning.blockbusters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.learning.blockbusters.BuildConfig;
import com.udacity.learning.blockbusters.R;
import com.udacity.learning.blockbusters.adapters.MoviesAdapter;
import com.udacity.learning.blockbusters.model.Movie;
import com.udacity.learning.blockbusters.model.MoviesContainer;
import com.udacity.learning.blockbusters.util.MovieRestService;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class BlockBusterHomeActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = BlockBusterHomeActivityFragment.class.getSimpleName();
    private static final String SELECTED_MOVIE = "selected_movie";
    private GridView moviesGrid;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> mListOfMovies;
    private MovieRestService movieRestService;

    public BlockBusterHomeActivityFragment() {
    }

    //region Lifecycle methods


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieRestService = MovieRestService.getINSTANCE();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_block_buster_home, container, false);

        setHasOptionsMenu(true);

        moviesGrid = (GridView) fragView.findViewById(R.id.moviesGrid);
        mListOfMovies = new ArrayList<>();

        moviesAdapter = new MoviesAdapter(getContext(), mListOfMovies);
        moviesGrid.setAdapter(moviesAdapter);
        moviesGrid.setOnItemClickListener(this);
        return fragView;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateMovies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_block_buster_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), BlockBusterSettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Event listeners
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Movie selectedMovie = moviesAdapter.getItem(position);
        Log.d(TAG, "onItemClick: Selected Movie is: " + selectedMovie);

        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(SELECTED_MOVIE, selectedMovie);
        startActivity(intent);
    }

    //endregion

    //region Local Methods

    private void populateMovies() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString(getString(R.string.sort_pref_key), "Popular movies");
        Log.d(TAG, "populateMovies: Sorting order: " + sortOrder);

        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute(sortOrder);
    }

    //endregion


    //region AsyncTask

    public class FetchMoviesTask extends AsyncTask<String, Void, MoviesContainer> {

        private MoviesContainer movieContainer;

        @Override
        protected MoviesContainer doInBackground(String... params) {
            // Verify size of params for the sort order
            if (params.length == 0) {
                return null;
            }
            try {
                movieContainer = movieRestService.getMoviesList(params[0], BuildConfig.TMDB_API_KEY);
                return movieContainer;
            } catch (Exception e) {
                Log.e(TAG, "Error ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(MoviesContainer movieContainer) {
            Log.d(TAG, "onPostExecute: Movies: " + movieContainer);
            moviesAdapter.clear();
            if (movieContainer != null) {
                moviesAdapter.addAll(movieContainer.getMovies());
            }
        }
    }

    //endregion
}
