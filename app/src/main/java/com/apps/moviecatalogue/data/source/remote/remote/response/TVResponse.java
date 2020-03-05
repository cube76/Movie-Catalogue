package com.apps.moviecatalogue.data.source.remote.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

public class TVResponse implements Parcelable {
    private int id;
    private String poster_path;
    private String name;
    private String overview;

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    private String first_air_date;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster_path);
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.first_air_date);
    }

    public TVResponse() {
    }

    protected TVResponse(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.name = in.readString();
        this.overview = in.readString();
        this.first_air_date = in.readString();
    }

    public static final Parcelable.Creator<TVResponse> CREATOR = new Parcelable.Creator<TVResponse>() {
        @Override
        public TVResponse createFromParcel(Parcel source) {
            return new TVResponse(source);
        }

        @Override
        public TVResponse[] newArray(int size) {
            return new TVResponse[size];
        }
    };
}
