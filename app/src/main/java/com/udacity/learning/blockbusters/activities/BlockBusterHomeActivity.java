package com.udacity.learning.blockbusters.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.udacity.learning.blockbusters.R;

public class BlockBusterHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = BlockBusterHomeActivity.class.getSimpleName();
    private ArrayAdapter<String> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_buster_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // TODO: 5/28/16 Change the color of the Text & style
        Spinner sortingOrderSpinner = (Spinner) findViewById(R.id.spinner_sort_order);
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        String spinnerItems[] = {"Top Rated", "Most Popular"};
        mSpinnerAdapter.addAll(spinnerItems);
        sortingOrderSpinner.setAdapter(mSpinnerAdapter);
        sortingOrderSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_block_buster_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String selectedOrder = adapterView.getItemAtPosition(position).toString();
        Log.d(TAG, "onItemClick: Sort the movies by :  " + selectedOrder);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
