package com.apps.moviecatalogue.ui.movie;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.moviecatalogue.R;
import com.apps.moviecatalogue.data.source.remote.remote.response.MovieResponse;
import com.apps.moviecatalogue.viewmodel.ViewModelFactory;

import static com.apps.moviecatalogue.data.source.remote.remote.StatusResponse.ERROR;
import static com.apps.moviecatalogue.vo.Status.LOADING;
import static com.apps.moviecatalogue.vo.Status.SUCCESS;

public class MovieFragment extends Fragment {

    private MovieViewModel viewModel;
    private MovieResponse movies;
    private RecyclerView rvCourse;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MovieFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
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
        rvCourse = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            viewModel = obtainViewModel(getActivity());
            movieAdapter = new MovieAdapter(getActivity());
            viewModel.setUsername("Dicoding");
            viewModel.movies.observe(this, movies -> {
                if (movies != null) {
                    switch (movies.status) {
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            movieAdapter.setListCourses(movies.data);
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
        }
    }


    @NonNull
    private static MovieViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }
}