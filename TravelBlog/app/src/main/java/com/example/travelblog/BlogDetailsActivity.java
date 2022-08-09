package com.example.travelblog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/3436e16367c8ec2312a0644bebd2694d484eb047/images/sydney_image.jpg";
    public static final String AVATAR_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/3436e16367c8ec2312a0644bebd2694d484eb047/avatars/avatar1.jpg";
    private TextView mTextTitle;
    private TextView mTextDate;
    private TextView mTextAuthor;
    private TextView mTextRating;
    private TextView mTextViews;
    private TextView mTextDescription;
    private RatingBar mRatingBar;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        mProgressBar = findViewById(R.id.progressBar);
        ImageView imageMain = findViewById(R.id.imageMain);
        ImageView imageAvatar = findViewById(R.id.imageAvatar);
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> finish());
        mTextTitle = findViewById(R.id.textTitle);
        mTextDate = findViewById(R.id.textDate);
        mTextAuthor = findViewById(R.id.textAuthor);
        mTextRating = findViewById(R.id.textRating);
        mTextViews = findViewById(R.id.textViews);
        mTextDescription = findViewById(R.id.textDescription);
        mRatingBar = findViewById(R.id.ratingBar);

        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> showData(blogList.get(0)));
            }

            private void showData(Blog blog) {
                mProgressBar.setVisibility(View.GONE);
                mTextTitle.setText(blog.getTitle());
                mTextDate.setText(blog.getDate());
                mTextAuthor.setText(blog.getAuthor().getName());
                mTextRating.setText(String.valueOf(blog.getRating()));
                mTextViews.setText(String.format("(%d views)", blog.getViews()));
                mTextDescription.setText(blog.getDescription());
                mRatingBar.setRating(blog.getRating());

                Glide.with(BlogDetailsActivity.this)
                        .load(IMAGE_URL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageMain);
                Glide.with(BlogDetailsActivity.this)
                        .load(AVATAR_URL)
                        .transform(new CircleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageAvatar);
            }

            @Override
            public void onError() {

            }
        });

    }
}