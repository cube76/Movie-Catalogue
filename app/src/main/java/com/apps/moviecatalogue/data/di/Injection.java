package com.apps.moviecatalogue.data.di;

import android.app.Application;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.LocalRepository;
import com.apps.moviecatalogue.data.source.local.room.MovieDatabase;
import com.apps.moviecatalogue.data.source.remote.remote.RemoteRepository;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResponse;
import com.apps.moviecatalogue.utils.AppExecutors;
import com.apps.moviecatalogue.utils.JsonHelper;

public class Injection {
    public static MovieRepository provideRepository(Application application) {

        MovieDatabase database = MovieDatabase.getInstance(application);

        LocalRepository localRepository = LocalRepository.getInstance(database.academyDao());
        RemoteRepository remoteRepository = RemoteRepository.getInstance(new JsonHelper(application));
        AppExecutors appExecutors = new AppExecutors();

        return MovieRepository.getInstance(localRepository, remoteRepository, appExecutors);
    }
}
