package com.apps.moviecatalogue.ui.bookmark;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.moviecatalogue.R;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResponse;
import com.apps.moviecatalogue.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class BookmarkFragment extends Fragment{

    private BookmarkViewModel viewModel;
    private MovieResponse movies;
    private RecyclerView rvCourse;
    private ProgressBar progressBar;
    private BookmarkPagedAdapter movieAdapter;
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (getView() != null) {
                // Sebelum melakukan penghapusan, course harus mendapatkan posisi dari item yang di swipe
                int swipedPosition = viewHolder.getAdapterPosition();

                // Kemudian memanggil CourseEntity sesuai posisi ketika diswipe
                MovieEntity courseEntity = movieAdapter.getItemById(swipedPosition);

                // Melakukan setBookmark untuk menghapus bookmark dari list course
                viewModel.setBookmark(courseEntity);

                // Memanggil Snackbar untuk melakukan pengecekan, apakah benar melakukan penghapusan bookmark
                Snackbar snackbar = Snackbar.make(getView(), "UNDO", Snackbar.LENGTH_LONG);

                // Mengembalikan item yang terhapus
                snackbar.setAction("OK", v -> viewModel.setBookmark(courseEntity));

                // Menampilkan snackbar
                snackbar.show();
            }
        }
    });

        public BookmarkFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new BookmarkFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        if (getActivity() != null) {
//            viewModel = obtainViewModel(getActivity());
//            viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
//            movies = viewModel.getMovies();
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCourse = view.findViewById(R.id.rv_bookmark);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            viewModel = obtainViewModel(getActivity());
            movieAdapter = new BookmarkPagedAdapter(getActivity());

            viewModel.getBookmarks().observe(this, movies -> {
                if (movies != null) {
                    switch (movies.status) {
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            movieAdapter.submitList(movies.data);
                            movieAdapter.notifyDataSetChanged();
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
            });
            rvCourse.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCourse.setHasFixedSize(true);
            rvCourse.setAdapter(movieAdapter);

            itemTouchHelper.attachToRecyclerView(rvCourse);
        }
    }

    @NonNull
    private static BookmarkViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(BookmarkViewModel.class);
    }
}