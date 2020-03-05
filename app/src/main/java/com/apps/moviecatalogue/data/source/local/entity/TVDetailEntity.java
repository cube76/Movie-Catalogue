package com.apps.moviecatalogue.data.source.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TVDetailEntity {
    @Embedded
    public TVEntity mCourse;

    @Relation(parentColumn = "movieId", entityColumn = "movieId")
    public List<TVEntity> mModules;
}
