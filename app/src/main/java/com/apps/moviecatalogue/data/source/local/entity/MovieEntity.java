package com.apps.moviecatalogue.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moviesetities")
public class MovieEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    private int movieId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "deadline")
    private String deadline;

    @ColumnInfo(name = "bookmarked")
    private boolean bookmarked = true;

    @ColumnInfo(name = "imagePath")
    private String imagePath;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public MovieEntity(int movieId, String title, String description, String deadline, Boolean bookmarked, String imagePath) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.bookmarked = bookmarked;
        this.imagePath = imagePath;
    }
}
