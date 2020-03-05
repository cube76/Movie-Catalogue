package com.apps.moviecatalogue.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.apps.moviecatalogue.data.source.local.entity.MovieDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.data.source.local.room.MovieDao;

import java.util.List;

public class LocalRepository {
    private final MovieDao mAcademyDao;

    private LocalRepository(MovieDao mAcademyDao) {
        this.mAcademyDao = mAcademyDao;
    }

    private static LocalRepository INSTANCE;

    public static LocalRepository getInstance(MovieDao academyDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(academyDao);
        }
        return INSTANCE;
    }

    public LiveData<List<MovieEntity>> getAllCourses() {
        return mAcademyDao.getMovies();
    }

    public LiveData<List<TVEntity>> getAllTVs() {
        return mAcademyDao.getTVs();
    }

    public LiveData<MovieDetailEntity> getCourseWithModules(final String courseId) {
        return mAcademyDao.getCourseWithModuleById(courseId);
    }

    public LiveData<TVDetailEntity> getTVDetail(final String courseId) {
        return mAcademyDao.getTVById(courseId);
    }

    public DataSource.Factory<Integer, MovieEntity> getBookmarkedMovies() {
        return mAcademyDao.getBookmarkedMovie();
    }

    public void insertMovies(List<MovieEntity> modules) {
        mAcademyDao.insertCourses(modules);
    }

    public void insertTVs(List<TVEntity> modules) {
        mAcademyDao.insertTVs(modules);
    }

    public void setCourseBookmark(MovieEntity course, boolean newState) {
        course.setBookmarked(newState);
        mAcademyDao.updateCourse(course);
    }

    public void setTVBookmark(TVEntity course, boolean newState) {
        course.setBookmarked(newState);
        mAcademyDao.updateTV(course);
    }

}
