package com.udacity.learning.blockbusters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import com.udacity.learning.blockbusters.util.EndlessScrollListener;
import com.udacity.learning.blockbusters.util.MovieRestService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A placeholder fragment containing the list of movies presented in GridView
 */
public class BlockBusterHomeActivityFragment
        extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = BlockBusterHomeActivityFragment.class.getSimpleName();
    private static final String SELECTED_MOVIE = "selected_movie";
    private MoviesAdapter moviesAdapter;
    private String prevSortOrder;

    //region Lifecycle methods

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_block_buster_home, container, false);

        setHasOptionsMenu(true);

        GridView moviesGrid = fragView.findViewById(R.id.moviesGrid);
        ArrayList<Movie> mListOfMovies = new ArrayList<>();

        moviesAdapter = new MoviesAdapter(getContext(), mListOfMovies);
        moviesGrid.setAdapter(moviesAdapter);
        moviesGrid.setOnItemClickListener(this);


        // Referred from https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews
        moviesGrid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Log.d(TAG, "onLoadMore: Load Move movies from page: " + page);
                Log.d(TAG, "onLoadMore: Total Movies so far: " + totalItemsCount);
                populateMovies(prevSortOrder, page);
                return true;
            }
        });
        return fragView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString(getString(R.string.sort_pref_key), getString(R.string.default_sort_value));
        if (!sortOrder.equals(prevSortOrder)) {
            Log.d(TAG, "onStart: prevSortOrder: " + prevSortOrder + " Change in Sorting Order: " + !sortOrder.equals(prevSortOrder));
            prevSortOrder = sortOrder;
            moviesAdapter.clear();
            Log.d(TAG, "onStart: Clearing the movies list & update views");
            populateMovies(sortOrder, 1);
        }
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

    private void populateMovies(String sortOrder, int pageNumber) {
        Log.d(TAG, "populateMovies: Sorting order: " + sortOrder);
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(this);
        fetchMoviesTask.execute(sortOrder, String.valueOf(pageNumber));
    }

    //endregion


    //region AsyncTask

    /**
     * Async Task to fetch the list of movies from tmdb api
     */
    static class FetchMoviesTask extends AsyncTask<String, Void, MoviesContainer> {

        private MoviesContainer movieContainer;
        private final WeakReference<BlockBusterHomeActivityFragment> fragmentWeakReference;
        private MovieRestService movieRestService;

        FetchMoviesTask(BlockBusterHomeActivityFragment fragment) {
            this.fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            movieRestService = MovieRestService.getINSTANCE();
        }

        @Override
        protected MoviesContainer doInBackground(String... params) {
            // Verify size of params for the sort order
            if (params.length == 0) {
                return null;
            }
            try {
                movieContainer = movieRestService.getMoviesList(params[0], BuildConfig.TMDB_API_KEY, params[1]);
                return movieContainer;
            } catch (Exception e) {
                Log.e(TAG, "Error ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(MoviesContainer movieContainer) {
            Log.d(TAG, "onPostExecute: Movies: " + movieContainer);
            if (movieContainer != null) {
                fragmentWeakReference.get().moviesAdapter.addAll(movieContainer.getMovies());
            }
        }
    }

    //endregion
}
