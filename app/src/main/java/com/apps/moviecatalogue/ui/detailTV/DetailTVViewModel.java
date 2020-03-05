package com.apps.moviecatalogue.ui.detailTV;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.vo.Resource;

public class DetailTVViewModel extends ViewModel {
    private TVEntity mCourse;
    private MutableLiveData<String> movieName = new MutableLiveData<>();
    private MovieRepository academyRepository;

    public DetailTVViewModel(MovieRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<Resource<TVDetailEntity>> movieDet = Transformations.switchMap(movieName,
            mCourseId -> academyRepository.getTV(mCourseId));

    public String getTVName() {
        if (movieName.getValue() == null) return null;
        return movieName.getValue();
    }

    public void setTVName(String movieName) {
        this.movieName.setValue(movieName);
    }

    void setBookmark() {
        if (movieDet.getValue() != null) {
            TVDetailEntity courseWithModule = movieDet.getValue().data;

            if (courseWithModule != null) {
                TVEntity courseEntity = courseWithModule.mCourse;

                // Kode di bawah menggunakan tanda seru (!),
                // karena akan mengganti status dari apakah sudah di bookmark atau tidak menjadi apakah sudah siap dibookmark atau tidak
                final boolean newState = !courseEntity.isBookmarked();
                academyRepository.setTVBookmark(courseEntity, newState);
            }
        }
    }

}
