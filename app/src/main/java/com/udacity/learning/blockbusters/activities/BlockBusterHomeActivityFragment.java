package com.udacity.learning.blockbusters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class BlockBusterHomeActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = BlockBusterHomeActivityFragment.class.getSimpleName();
    private GridView moviesGrid;
    private MoviesAdapter moviesNamesAdapter;
    private ArrayList<Movie> mListOfMovies;

    public BlockBusterHomeActivityFragment() {
    }

    //region Lifecycle methods

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
        intent.putExtra(Intent.EXTRA_TEXT, selectedMovie.getMovieTitle());
        startActivity(intent);
    }

    //endregion

    //region Local Methods

    private void populateMovies() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        Map<String, ?> prefMap = preferences.getAll();
        Log.d(TAG, "populateMovies: size: " + prefMap.size());
        for (String key : prefMap.keySet()) {
            Log.d(TAG, "populateMovies: Key: " + key);
        }

        String sortOrder = preferences.getString(getString(R.string.sort_pref_key), "Popular movies");
        Log.d(TAG, "populateMovies: Sorting order: " + sortOrder);

        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setMovieTitle("Movie :" + i);
            movie.setMovieRating(i);
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

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL =
                        "http://api.themoviedb.org/3/movie/popular?";
                final String API_KEY_PARAM = "api_key";
                final String PAGE_PARAM = "page";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.TMDB_API_KEY)
                        .appendQueryParameter(PAGE_PARAM, "1")
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
                Log.d(TAG, "doInBackground: Movies: " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        private String[] getMoviesDataFromJson(String moviesJsonStr) throws JSONException {
            JSONArray moviesArray = new JSONArray(moviesJsonStr);
            Log.d(TAG, "getMoviesDataFromJson: JSON Array: " + moviesArray);

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }


    //endregion
}
