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
import com.udacity.learning.blockbusters.util.MovieRestService;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class BlockBusterHomeActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = BlockBusterHomeActivityFragment.class.getSimpleName();
    private GridView moviesGrid;
    private MoviesAdapter moviesNamesAdapter;
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

        moviesNamesAdapter = new MoviesAdapter(getContext(), mListOfMovies);
        moviesGrid.setAdapter(moviesNamesAdapter);
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
        Movie selectedMovie = moviesNamesAdapter.getItem(position);
        Log.d(TAG, "onItemClick: Selected Movie is: " + selectedMovie);

        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, selectedMovie.getTitle());
        startActivity(intent);
    }

    //endregion

    //region Local Methods

    private void populateMovies() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString(getString(R.string.sort_pref_key), "Popular movies");
        Log.d(TAG, "populateMovies: Sorting order: " + sortOrder);

        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setTitle("Movie :" + i);
            movie.setVoteAverage(i);
            mListOfMovies.add(movie);
        }

        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute(sortOrder);
    }

    //endregion


    //region AsyncTask

    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            // Verify size of params for the sort order
            if (params.length == 0) {
                return null;
            }

            try {
                movieRestService.getMoviesList(BuildConfig.TMDB_API_KEY, params[0]);
            } catch (Exception e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }


    //endregion
}
