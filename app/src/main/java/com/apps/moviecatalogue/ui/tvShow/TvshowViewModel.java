package com.apps.moviecatalogue.ui.tvShow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.vo.Resource;

import java.util.List;

public class TvshowViewModel extends ViewModel {

    private MovieRepository academyRepository;
    private MutableLiveData<String> mLogin = new MutableLiveData<>();


    public TvshowViewModel(MovieRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<Resource<List<TVEntity>>> movies = Transformations.switchMap(mLogin,
            data -> academyRepository.getAllTVs());

    void setUsername(String username) {
        mLogin.setValue(username);
    }
}