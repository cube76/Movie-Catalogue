package com.apps.moviecatalogue.ui.movie;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.moviecatalogue.R;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.ui.detail.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Activity activity;
    private List<MovieEntity> mCourses = new ArrayList<>();

    MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    private List<MovieEntity> getListMovies() {
        return mCourses;
    }

    void setListCourses(List<MovieEntity> listCourses) {
        if (listCourses == null) return;
        this.mCourses.clear();
        this.mCourses.addAll(listCourses);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movie, parent, false);
        //BELUM BERHASIL
        //        final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "position = " + holder.getAdapterPosition());
//            }
//        });
//        return holder;
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.tvTitle.setText(getListMovies().get(position).getTitle());
        holder.tvDescription.setText(getListMovies().get(position).getDescription());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, getListMovies().get(position).getTitle());
            activity.startActivity(intent);
        });

        String image_base = "https://image.tmdb.org/t/p/w185";
        Glide.with(holder.itemView.getContext())
                .load(image_base + getListMovies().get(position).getImagePath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getListMovies().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvDescription;
        final ImageView imgPoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
        }
    }
}