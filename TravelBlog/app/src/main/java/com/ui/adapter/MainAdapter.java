package com.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.data.models.Blog;
import com.example.travelblog.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Blog> blogList = new ArrayList<>();

    private final IOnItemClickListener mListener;

    public MainAdapter(IOnItemClickListener iOnItemClickListener) {
        this.mListener = iOnItemClickListener;
    }

    // executes onCreateViewHolder the number of times
    // which is equal to the number of list items which can appear on the screen at the same time
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view, mListener);
    }

    // executes onBindViewHolder every time we need to render list item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(blogList.get(position));
        holder.itemView.setOnClickListener(v -> mListener.onItemClicked(blogList.get(position)));
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Blog> list) {
        this.blogList = list;
        notifyDataSetChanged();
    }

    public void sortByTitle() {
        List<Blog> currentList = new ArrayList<>(blogList);
        Collections.sort(currentList, (o1, o2) ->
                o1.getTitle().compareTo(o2.getTitle())
        );
        setList(currentList);
    }

    public void sortByDate() {
        List<Blog> currentList = new ArrayList<>(blogList);
        Collections.sort(currentList, (o1, o2) ->
                o2.getDateMillis().compareTo(o1.getDateMillis())
        );
        setList(currentList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle;
        private TextView mTextDate;
        private ImageView mImageAvatar;

        public ViewHolder(@NonNull View itemView, IOnItemClickListener listener) {
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

