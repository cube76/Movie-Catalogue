package com.apps.moviecatalogue.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.vo.Resource;

public class DetailViewModel extends ViewModel {

//    private String movieName;
    private MovieRepository academyRepository;
    private MutableLiveData<String> movieName = new MutableLiveData<>();


    public DetailViewModel(MovieRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<Resource<MovieDetailEntity>> movieDet = Transformations.switchMap(movieName,
            mCourseId -> academyRepository.getMovie(mCourseId));

    public String getMovieName() {
        if (movieName.getValue() == null) return null;
        return movieName.getValue();
    }

    public void setMovieName(String movieName) {
        this.movieName.setValue(movieName);
    }

    void setBookmark() {
        if (movieDet.getValue() != null) {
            MovieDetailEntity courseWithModule = movieDet.getValue().data;

            if (courseWithModule != null) {
                MovieEntity courseEntity = courseWithModule.mCourse;

                // Kode di bawah menggunakan tanda seru (!),
                // karena akan mengganti status dari apakah sudah di bookmark atau tidak menjadi apakah sudah siap dibookmark atau tidak
                final boolean newState = !courseEntity.isBookmarked();
                academyRepository.setCourseBookmark(courseEntity, newState);
            }
        }
    }

}
