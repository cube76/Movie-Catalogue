package com.apps.moviecatalogue.ui.bookmark;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.moviecatalogue.R;
import com.apps.moviecatalogue.data.source.local.entity.MovieEntity;
import com.apps.moviecatalogue.ui.detail.DetailActivity;
import com.apps.moviecatalogue.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

public class BookmarkPagedAdapter extends PagedListAdapter<MovieEntity, BookmarkPagedAdapter.BookmarkViewHolder> {
    private BookmarkFragmentCallback callback;
    private final Activity activity;

    BookmarkPagedAdapter(Activity activity) {
        super(DIFF_CALLBACK);

        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookmarkViewHolder holder, int position) {
        final MovieEntity bookmark = getItem(position);
        if (bookmark != null) {
            holder.tvTitle.setText(bookmark.getTitle());
            holder.tvDescription.setText(bookmark.getDescription());
            holder.itemView.setOnClickListener(v -> {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                String courseId = bookmark.getTitle();
                intent.putExtra(DetailActivity.EXTRA_MOVIE, courseId);
                context.startActivity(intent);
            });
            String image_base = "https://image.tmdb.org/t/p/w185";
            GlideApp.with(holder.itemView.getContext())
                    .load(image_base+bookmark.getImagePath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(holder.imgPoster);
        }
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movie, parent, false);
        return new BookmarkViewHolder(view);
    }


    private static DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    MovieEntity getItemById(int swipedPosition) {
        return getItem(swipedPosition);
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvDescription;
        final ImageView imgPoster;

        BookmarkViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
