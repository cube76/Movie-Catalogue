package com.apps.moviecatalogue.ui.tvShow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.utils.DataDummy;
import com.apps.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvshowFragmentTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TvshowViewModel viewModel;
    private MovieRepository academyRepository = mock(MovieRepository.class);


    @Before
    public void setUp() {
        viewModel = new TvshowViewModel(academyRepository);
    }

    @Test
    public void getMovies() {
        Resource<List<TVEntity>> resource = Resource.success(DataDummy.generateDummyTVs());
        MutableLiveData<Resource<List<TVEntity>>> dummyCourses = new MutableLiveData<>();
        dummyCourses.setValue(resource);

        when(academyRepository.getAllTVs()).thenReturn(dummyCourses);

        Observer<Resource<List<TVEntity>>> observer = mock(Observer.class);

        viewModel.setUsername("contoh");

        viewModel.movies.observeForever(observer);

        verify(observer).onChanged(resource);
    }
}