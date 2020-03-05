package com.apps.moviecatalogue.data.source.remote.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResponse;
import com.apps.moviecatalogue.data.source.remote.remote.response.TVResponse;
import com.apps.moviecatalogue.utils.EspressoIdlingResource;
import com.apps.moviecatalogue.utils.JsonHelper;

import java.util.List;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;
    private JsonHelper jsonHelper;

    private RemoteRepository(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public static RemoteRepository getInstance(JsonHelper helper) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository(helper);
        }
        return INSTANCE;
    }
    public void getAllCourses(LoadCoursesCallback callback) {
         callback.onAllCoursesReceived(jsonHelper.loadCourses());
    }

    public LiveData<ApiResponse<List<MovieResponse>>> getAllMoviesAsLiveData() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<MovieResponse>>> resultCourse = new MutableLiveData<>();

            resultCourse.setValue(ApiResponse.success(jsonHelper.loadCourses()));
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }

        return resultCourse;
    }

//    public LiveData<ApiResponse<List<MovieResponse>>> getAllMoviesByTitleAsLiveData(String title) {
//        EspressoIdlingResource.increment();
//        MutableLiveData<ApiResponse<List<MovieResponse>>> resultCourse = new MutableLiveData<>();
//
//            resultCourse.setValue(ApiResponse.success(jsonHelper.loadCourses(title)));
//            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
//                EspressoIdlingResource.decrement();
//            }
//
//        return resultCourse;
//    }

    public LiveData<ApiResponse<List<TVResponse>>> getAllTVsAsLiveData() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<TVResponse>>> resultCourse = new MutableLiveData<>();

            resultCourse.setValue(ApiResponse.success(jsonHelper.loadTVs()));
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }

        return resultCourse;
    }

    public void getAllTVs(LoadTVsCallback callback) {
        callback.onAllCoursesReceived(jsonHelper.loadTVs());
    }

    public interface LoadCoursesCallback {
        void onAllCoursesReceived(List<MovieResponse> courseResponses);

        void onDataNotAvailable();
    }

    public interface LoadTVsCallback {
        void onAllCoursesReceived(List<TVResponse> courseResponses);

        void onDataNotAvailable();
    }

    public interface GetMovieCallback {
        void onAllCoursesReceived(MovieResponse courseResponses);

        void onDataNotAvailable();
    }

}

