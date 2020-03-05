package com.apps.moviecatalogue.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.apps.moviecatalogue.data.source.MovieRepository;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.vo.Resource;

public class BookmarkViewModel extends ViewModel {
    private MovieRepository academyRepository;

    public BookmarkViewModel(MovieRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> getBookmarks() {
        return academyRepository.getBookmarkedMovies();
    }

    void setBookmark(MovieEntity courseEntity) {
        // Kode di bawah menggunakan tanda seru (!),
        // karena akan mengganti status dari apakah sudah di bookmark atau tidak menjadi apakah sudah siap dibookmark atau tidak
        final boolean newState = !courseEntity.isBookmarked();
        academyRepository.setCourseBookmark(courseEntity, newState);
    }
}