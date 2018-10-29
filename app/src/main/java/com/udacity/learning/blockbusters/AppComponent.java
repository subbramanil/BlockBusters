package com.udacity.learning.blockbusters;

import com.udacity.learning.blockbusters.activities.BlockBusterHomeActivityFragment;
import com.udacity.learning.blockbusters.adapters.MoviesAdapter;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BlockBusterHomeActivityFragment fragment);

    void inject(MoviesAdapter adapter);
}
