package com.apps.moviecatalogue.ui.movie;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
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

public class MovieViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MovieViewModel viewModel;
    private MovieRepository academyRepository = mock(MovieRepository.class);

    @Before
    public void setUp() {
        viewModel = new MovieViewModel(academyRepository);
    }

    @Test
    public void getMovies() {
        Resource<List<MovieEntity>> resource = Resource.success(DataDummy.generateDummyCourses());
        MutableLiveData<Resource<List<MovieEntity>>> dummyCourses = new MutableLiveData<>();
        dummyCourses.setValue(resource);

        when(academyRepository.getAllCourses()).thenReturn(dummyCourses);

        Observer<Resource<List<MovieEntity>>> observer = mock(Observer.class);

        viewModel.setUsername("Dicoding");

        viewModel.movies.observeForever(observer);

        verify(observer).onChanged(resource);
    }
}