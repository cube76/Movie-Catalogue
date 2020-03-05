package com.apps.moviecatalogue.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;

@Database(entities = {MovieEntity.class, TVEntity.class},
        version = 1,
        exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase INSTANCE;

    public abstract MovieDao academyDao();

    private static final Object sLock = new Object();

    public static MovieDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, "Movies.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
