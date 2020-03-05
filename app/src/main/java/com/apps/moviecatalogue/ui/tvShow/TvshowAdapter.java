package com.apps.moviecatalogue.ui.tvShow;

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
import com.apps.moviecatalogue.data.source.local.entity.TVEntity;
import com.apps.moviecatalogue.ui.detailTV.DetailTVActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.TvshowViewHolder> {
    private final Activity activity;
    private List<TVEntity> mCourses = new ArrayList<>();

    TvshowAdapter(Activity activity) {
        this.activity = activity;
    }

    private List<TVEntity> getListCourses() {
        return mCourses;
    }

    void setListCourses(List<TVEntity> listCourses) {
        if (listCourses == null) return;
        this.mCourses.clear();
        this.mCourses.addAll(listCourses);
    }

    @NonNull
    @Override
    public TvshowAdapter.TvshowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movie, parent, false);
        return new TvshowAdapter.TvshowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvshowAdapter.TvshowViewHolder holder, int position) {
        holder.tvTitle.setText(getListCourses().get(position).getTitle());
        holder.tvDescription.setText(getListCourses().get(position).getDescription());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailTVActivity.class);
            intent.putExtra(DetailTVActivity.EXTRA_TV, getListCourses().get(position).getTitle());
            activity.startActivity(intent);
        });

        String image_base = "https://image.tmdb.org/t/p/w185";
        Glide.with(holder.itemView.getContext())
                .load(image_base + getListCourses().get(position).getImagePath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getListCourses().size();
    }

    class TvshowViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvDescription;
        final ImageView imgPoster;

        TvshowViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
        }
    }
}