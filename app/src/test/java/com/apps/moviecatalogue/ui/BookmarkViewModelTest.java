package com.apps.moviecatalogue.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.ui.bookmark.BookmarkViewModel;
import com.apps.moviecatalogue.ui.movie.MovieViewModel;
import com.apps.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookmarkViewModelTest {
    private BookmarkViewModel viewModel;
    private MovieRepository academyRepository = mock(MovieRepository.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        viewModel = new BookmarkViewModel(academyRepository);
    }

    @Test
    public void getBookmarks() {
        MutableLiveData<Resource<PagedList<MovieEntity>>> dummyCourse = new MutableLiveData<>();
        PagedList<MovieEntity> pagedList = mock(PagedList.class);

        dummyCourse.setValue(Resource.success(pagedList));

        when(academyRepository.getBookmarkedMovies()).thenReturn(dummyCourse);

        Observer<Resource<PagedList<MovieEntity>>> observer = mock(Observer.class);

        viewModel.getBookmarks().observeForever(observer);

        verify(observer).onChanged(Resource.success(pagedList));
    }
}