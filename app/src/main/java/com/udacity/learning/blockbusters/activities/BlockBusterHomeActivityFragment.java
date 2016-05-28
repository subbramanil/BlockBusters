package com.udacity.learning.blockbusters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.learning.blockbusters.R;
import com.udacity.learning.blockbusters.adapters.MoviesAdapter;
import com.udacity.learning.blockbusters.model.Movie;

import java.util.ArrayList;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_block_buster_home, container, false);

        moviesGrid = (GridView) fragView.findViewById(R.id.moviesGrid);
        mListOfMovies = new ArrayList<>();

        populateMovies();

        moviesNamesAdapter = new MoviesAdapter(getContext(), mListOfMovies);
        moviesGrid.setAdapter(moviesNamesAdapter);
        moviesGrid.setOnItemClickListener(this);
        return fragView;
    }

    private void populateMovies() {
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setMovieTitle("Movie :" + i);
            movie.setMovieRating(i);
            mListOfMovies.add(movie);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Movie selectedMovie = moviesNamesAdapter.getItem(position);
        Log.d(TAG, "onItemClick: Selected Movie is: " + selectedMovie);

        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, selectedMovie.getMovieTitle());
        startActivity(intent);
    }

}
