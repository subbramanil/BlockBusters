package com.udacity.learning.blockbusters.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.learning.blockbusters.R;

public class BlockBusterHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_buster_home);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

}
