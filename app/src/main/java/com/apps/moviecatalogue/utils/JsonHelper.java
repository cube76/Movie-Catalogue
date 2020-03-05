package com.apps.moviecatalogue.utils;

import android.app.Application;
import android.util.Log;

import com.apps.moviecatalogue.data.source.remote.remote.api.ApiServices;
import com.apps.moviecatalogue.data.source.remote.remote.api.NetworkClient;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResponse;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResult;
import com.apps.moviecatalogue.data.source.remote.remote.response.TVResponse;
import com.apps.moviecatalogue.data.source.remote.remote.response.TVResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonHelper {
    private Application application;

    public JsonHelper(Application application) {
        this.application = application;
    }

    private String parsingFileToString(String fileName) {
        try {
            InputStream is = application.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MovieResponse> loadCourses() {
        ArrayList<MovieResponse> list = new ArrayList<>();
//        final ArrayList<MovieResponse>[] movies = new ArrayList[]{new ArrayList<>()};

        ApiServices apiService =
                NetworkClient.getRetrofitClient().create(ApiServices.class);

        Call<MovieResult> call = apiService.getMovies();
        try {
            Response<MovieResult> response = call.execute();
            if (response.body() != null) {
                    ArrayList<MovieResponse> movies = new ArrayList<>();
                    movies = response.body().getResults();
                    Log.d("",""+movies);
                    list.addAll(movies);
                    Log.d("",""+list);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ASYNC TAPI TIDAK MEMUNGKINKAN
//        call.enqueue(new Callback<MovieResult>() {
//            @Override
//            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
//                if (response.body() != null) {
//                    movies[0] = response.body().getResults();
//                    Log.d("",""+ movies[0]);
//                    list.addAll(movies[0]);
//                    Log.d("",""+list);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieResult> call, Throwable t) {
//                // Log error here since request failed
//                Log.e("error", t.toString());
//            }
//        });
         return list;
    }

    public List<TVResponse> loadTVs() {
        ArrayList<TVResponse> list = new ArrayList<>();

        ApiServices apiService =
                NetworkClient.getRetrofitClient().create(ApiServices.class);

        Call<TVResult> call = apiService.getTV();
        try {
            Response<TVResult> response = call.execute();
            if (response.body() != null) {
                ArrayList<TVResponse> movies = new ArrayList<>();
                movies = response.body().getResults();
                Log.d("",""+movies);
                list.addAll(movies);
                Log.d("",""+list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

//
//    public TVResponse loadTVs(String name) {
//       TVResponse list;
//        ArrayList<TVResponse> movies;
//
//        ApiServices apiService =
//                NetworkClient.getRetrofitClient().create(ApiServices.class);
//
//        Call<TVResult> call = apiService.searchTV(name);
//        try {
//            Response<TVResult> response = call.execute();
//            if (response.body() != null) {
//                movies = response.body().getResults();
//                Log.d("",""+movies);
//                list.addAll(movies);
//                Log.d("",""+list);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
}
