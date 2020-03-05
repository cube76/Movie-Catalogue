package com.apps.moviecatalogue.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.apps.moviecatalogue.data.source.local.entity.MovieDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;

import java.util.List;

@Dao
public interface MovieDao {
    @WorkerThread
    @Query("SELECT * FROM moviesetities")
    LiveData<List<MovieEntity>> getMovies();

    @WorkerThread
    @Query("SELECT * FROM tvsetities")
    LiveData<List<TVEntity>> getTVs();

//    @WorkerThread
//    @Query("SELECT * FROM moviesetities where bookmarked = 1")
//    LiveData<List<MovieEntity>> getBookmarkedMovie();

    @Query("SELECT * FROM moviesetities where bookmarked = 1")
    DataSource.Factory<Integer, MovieEntity> getBookmarkedMovie();

    @Transaction
    @Query("SELECT * FROM moviesetities WHERE title = :courseId")
    LiveData<MovieDetailEntity> getCourseWithModuleById(String courseId);

    @Transaction
    @Query("SELECT * FROM tvsetities WHERE title = :courseId")
    LiveData<TVDetailEntity> getTVById(String courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCourses(List<MovieEntity> courses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTVs(List<TVEntity> courses);

    @Update(onConflict = OnConflictStrategy.FAIL)
    int updateCourse(MovieEntity course);

    @Update(onConflict = OnConflictStrategy.FAIL)
    int updateTV(TVEntity course);
}
