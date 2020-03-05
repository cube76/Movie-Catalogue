package com.apps.moviecatalogue.data.source.remote.remote.api;

import com.apps.moviecatalogue.BuildConfig;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResult;
import com.apps.moviecatalogue.data.source.remote.remote.response.TVResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    String key = BuildConfig.ApiKey;

    @GET("discover/movie?api_key="+key+"&language=en-US")
    Call<MovieResult> getMovies();

    @GET("discover/tv?api_key="+key+"&language=en-US")
    Call<TVResult> getTV();

    @GET("search/movie?api_key="+key+"&language=en-US")
    Call<MovieResult> searchMovie(@Query("query") String title);

    @GET("search/tv?api_key="+key+"&language=en-US")
    Call<TVResult> searchTV(@Query("query") String title);

    @GET("discover/movie?api_key="+key)
    Call<MovieResult> getUpcoming(@Query("primary_release_date.gte") String gte, @Query("primary_release_date.lte") String lte);
}
