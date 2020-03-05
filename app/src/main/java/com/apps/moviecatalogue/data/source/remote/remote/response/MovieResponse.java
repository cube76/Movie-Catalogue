package com.apps.moviecatalogue.data.source.remote.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieResponse implements Parcelable {
    private int id;
    private String poster_path;
    private String title;
    private String overview;
    private String release_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster_path);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
    }

    public MovieResponse() {
    }

    protected MovieResponse(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    public static final Parcelable.Creator<MovieResponse> CREATOR = new Parcelable.Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel source) {
            return new MovieResponse(source);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}
