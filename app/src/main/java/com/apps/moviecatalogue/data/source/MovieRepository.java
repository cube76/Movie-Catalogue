package com.apps.moviecatalogue.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.apps.moviecatalogue.data.source.local.LocalRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVDetailEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.data.source.remote.remote.ApiResponse;
import com.apps.moviecatalogue.data.source.remote.remote.NetworkBoundResource;
import com.apps.moviecatalogue.data.source.remote.remote.RemoteRepository;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResponse;
import com.apps.moviecatalogue.data.source.remote.remote.response.TVResponse;
import com.apps.moviecatalogue.utils.AppExecutors;
import com.apps.moviecatalogue.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements MovieDataSource{
    private volatile static MovieRepository INSTANCE = null;

    private final LocalRepository localRepository;
    private final RemoteRepository remoteRepository;
    private final AppExecutors appExecutors;

    private MovieRepository(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository, AppExecutors appExecutors) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.appExecutors = appExecutors;
    }

    public static MovieRepository getInstance(LocalRepository localRepository, RemoteRepository remoteData, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(localRepository, remoteData, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> getAllCourses() {
        return new NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            @Override
            public LiveData<List<MovieEntity>> loadFromDB() {
                return localRepository.getAllCourses();
            }

            @Override
            public Boolean shouldFetch(List<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            public LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return remoteRepository.getAllMoviesAsLiveData();
            }

            @Override
            public void saveCallResult(List<MovieResponse> courseResponses) {
                List<MovieEntity> courseEntities = new ArrayList<>();

                for (MovieResponse courseResponse : courseResponses) {

                    courseEntities.add(new MovieEntity(courseResponse.getId(),
                            courseResponse.getTitle(),
                            courseResponse.getOverview(),
                            courseResponse.getRelease_date(),
                            false,
                            courseResponse.getPoster_path()));
                }

                localRepository.insertMovies(courseEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieDetailEntity>> getMovie(String title) {
        return new NetworkBoundResource<MovieDetailEntity, List<MovieResponse>>(appExecutors) {
            @Override
            protected LiveData<MovieDetailEntity> loadFromDB() {
                return localRepository.getCourseWithModules(title);
            }

            @Override
            protected Boolean shouldFetch(MovieDetailEntity courseWithModule) {
                return (courseWithModule == null || courseWithModule.mModules == null) || (courseWithModule.mModules.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return remoteRepository.getAllMoviesAsLiveData();
            }

            @Override
            protected void saveCallResult(List<MovieResponse> moduleResponses) {

                List<MovieEntity> moduleEntities = new ArrayList<>();

                for (MovieResponse moduleResponse : moduleResponses) {
                    moduleEntities.add(new MovieEntity(moduleResponse.getId(),
                            moduleResponse.getTitle(),
                            moduleResponse.getOverview(),
                            moduleResponse.getRelease_date(),
                            false,
                            moduleResponse.getPoster_path()));
                }

                localRepository.insertMovies(moduleEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<TVEntity>>> getAllTVs() {
        return new NetworkBoundResource<List<TVEntity>, List<TVResponse>>(appExecutors) {
            @Override
            public LiveData<List<TVEntity>> loadFromDB() {
                return localRepository.getAllTVs();
            }

            @Override
            public Boolean shouldFetch(List<TVEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            public LiveData<ApiResponse<List<TVResponse>>> createCall() {
                return remoteRepository.getAllTVsAsLiveData();
            }

            @Override
            public void saveCallResult(List<TVResponse> courseResponses) {
                List<TVEntity> courseEntities = new ArrayList<>();

                for (TVResponse courseResponse : courseResponses) {

                    courseEntities.add(new TVEntity(courseResponse.getId(),
                            courseResponse.getName(),
                            courseResponse.getOverview(),
                            courseResponse.getFirst_air_date(),
                            false,
                            courseResponse.getPoster_path()));
                }

                localRepository.insertTVs(courseEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TVDetailEntity>> getTV(String name) {
        return new NetworkBoundResource<TVDetailEntity, List<TVResponse>>(appExecutors) {
            @Override
            protected LiveData<TVDetailEntity> loadFromDB() {
                return localRepository.getTVDetail(name);
            }

            @Override
            protected Boolean shouldFetch(TVDetailEntity courseWithModule) {
                return (courseWithModule == null || courseWithModule.mModules == null) || (courseWithModule.mModules.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<TVResponse>>> createCall() {
                return remoteRepository.getAllTVsAsLiveData();
            }

            @Override
            protected void saveCallResult(List<TVResponse> moduleResponses) {

                List<TVEntity> moduleEntities = new ArrayList<>();

                for (TVResponse moduleResponse : moduleResponses) {
                    moduleEntities.add(new TVEntity(moduleResponse.getId(),
                            moduleResponse.getName(),
                            moduleResponse.getOverview(),
                            moduleResponse.getFirst_air_date(),
                            false,
                            moduleResponse.getPoster_path()));
                }

                localRepository.insertTVs(moduleEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getBookmarkedMovies() {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getBookmarkedMovies(), /* page size */ 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<MovieResponse> data) {

            }
        }.asLiveData();
    }

    @Override
    public void setCourseBookmark(MovieEntity course, boolean state) {
        Runnable runnable = () -> localRepository.setCourseBookmark(course, state);

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setTVBookmark(TVEntity course, boolean state) {
        Runnable runnable = () -> localRepository.setTVBookmark(course, state);

        appExecutors.diskIO().execute(runnable);
    }
}
