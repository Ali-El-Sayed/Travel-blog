package com.example.travelblog.com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.travelblog.Blog;
import com.example.travelblog.R;

public class MainAdapter extends
        ListAdapter<Blog, MainAdapter.MainViewHolder> {

    private static final DiffUtil.ItemCallback<Blog> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Blog>() {
                @Override
                public boolean areItemsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public MainAdapter() {
        super(DIFF_CALLBACK);
    }


    // executes onCreateViewHolder the number of times
    // which is equal to the number of list items which can appear on the screen at the same time
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(view);
    }

    // executes onBindViewHolder every time we need to render list item
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle;
        private TextView mTextDate;
        private ImageView mImageAvatar;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.textTitle);
            mTextDate = itemView.findViewById(R.id.textDate);
            mImageAvatar = itemView.findViewById(R.id.imageAvater);
        }

        void bindTo(Blog blog) {
            mTextTitle.setText(blog.getTitle());
            mTextDate.setText(blog.getDate());

            Glide.with(itemView)
                    .load(blog.getAuthor().getAvatarURL())
                    .transform(new CircleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mImageAvatar);
        }
    }
}

