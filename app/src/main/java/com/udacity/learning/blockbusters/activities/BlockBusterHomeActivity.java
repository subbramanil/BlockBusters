package com.udacity.learning.blockbusters.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.udacity.learning.blockbusters.R;

public class BlockBusterHomeActivity extends AppCompatActivity {

    private static final String TAG = BlockBusterHomeActivity.class.getSimpleName();

    ScalpelFrameLayout scalpelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_buster_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        scalpelView = (ScalpelFrameLayout) findViewById(R.id.scalpel);
        scalpelView.setLayerInteractionEnabled(true);
        scalpelView.setDrawIds(true);
        scalpelView.setDrawViews(true);
    }
}
