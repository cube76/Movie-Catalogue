package com.apps.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.apps.moviecatalogue.data.source.local.entity.MovieDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.vo.Resource;

import java.util.List;

public interface MovieDataSource {
    LiveData<Resource<List<MovieEntity>>> getAllCourses();
    LiveData<Resource<MovieDetailEntity>> getMovie(String title);
    LiveData<Resource<List<TVEntity>>> getAllTVs();
    LiveData<Resource<TVDetailEntity>> getTV(String name);
    LiveData<Resource<PagedList<MovieEntity>>> getBookmarkedMovies();
    void setCourseBookmark(MovieEntity course, boolean state);
    void setTVBookmark(TVEntity course, boolean state);
}
