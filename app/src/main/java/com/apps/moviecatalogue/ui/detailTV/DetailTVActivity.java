package com.apps.moviecatalogue.ui.detailTV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.moviecatalogue.R;
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.viewmodel.ViewModelFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailTVActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_TV";
    private DetailTVViewModel viewModel;
    ImageView imagePoster;
    TextView textTitle, textDesc, textDate;
    private ProgressBar progressBar;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = obtainViewModel(this);
        textTitle = findViewById(R.id.tv_title);
        textDesc = findViewById(R.id.tv_desc);
        textDate = findViewById(R.id.tv_date);
        imagePoster = findViewById(R.id.img_poster);
        progressBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String courseId = extras.getString(EXTRA_TV);
            if (courseId != null) {
                viewModel.setTVName(courseId);
            }
        }

        viewModel.movieDet.observe(this, moduleEntity -> {
            if (moduleEntity != null) {
                switch (moduleEntity.status) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        if (moduleEntity.data != null) {
                            progressBar.setVisibility(View.GONE);
                            populateMovie(moduleEntity.data.mCourse);
                        }
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    @NonNull
    private static DetailTVViewModel obtainViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(DetailTVViewModel.class);
    }

    private void populateMovie(TVEntity courseEntity) {
        textTitle.setText(courseEntity.getTitle());
        textDesc.setText(courseEntity.getDescription());
        textDate.setText(courseEntity.getDeadline());

        String image_base = "https://image.tmdb.org/t/p/w185";
        Glide.with(getApplicationContext())
                .load(image_base + courseEntity.getImagePath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(imagePoster);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;
        viewModel.movieDet.observe(this, courseWithModule -> {
            if (courseWithModule != null) {
                switch (courseWithModule.status) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        if (courseWithModule.data != null) {
                            progressBar.setVisibility(View.GONE);
                            boolean state = courseWithModule.data.mCourse.isBookmarked();
                            setBookmarkState(state);
                        }
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bookmark) {
            viewModel.setBookmark();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBookmarkState(boolean state) {
        if (menu == null) return;
        MenuItem menuItem = menu.findItem(R.id.action_bookmark);
        if (state) {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_black));
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border_black));
        }
    }
}
