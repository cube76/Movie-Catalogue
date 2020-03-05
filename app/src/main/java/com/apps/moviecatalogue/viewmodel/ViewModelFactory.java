package com.apps.moviecatalogue.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.apps.moviecatalogue.data.di.Injection;
import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.ui.bookmark.BookmarkViewModel;
import com.apps.moviecatalogue.ui.detail.DetailViewModel;
import com.apps.moviecatalogue.ui.detailTV.DetailTVViewModel;
import com.apps.moviecatalogue.ui.movie.MovieViewModel;
import com.apps.moviecatalogue.ui.tvShow.TvshowViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final MovieRepository mAcademyRepository;

    private ViewModelFactory(MovieRepository academyRepository) {
        mAcademyRepository = academyRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieViewModel(mAcademyRepository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailViewModel(mAcademyRepository);
        } else if (modelClass.isAssignableFrom(DetailTVViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailTVViewModel(mAcademyRepository);
        } else if (modelClass.isAssignableFrom(TvshowViewModel.class)) {
            //noinspection unchecked
            return (T) new TvshowViewModel(mAcademyRepository);
        }else if (modelClass.isAssignableFrom(BookmarkViewModel.class)) {
            //noinspection unchecked
            return (T) new BookmarkViewModel(mAcademyRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
