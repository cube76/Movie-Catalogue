package com.apps.moviecatalogue.data.source.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieDetailEntity {
    @Embedded
    public MovieEntity mCourse;

    @Relation(parentColumn = "movieId", entityColumn = "movieId")
    public List<MovieEntity> mModules;
}
