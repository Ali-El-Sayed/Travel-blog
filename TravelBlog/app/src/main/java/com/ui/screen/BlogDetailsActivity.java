package com.ui.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.data.client.BlogArticlesCallback;
import com.data.client.BlogHttpClient;
import com.data.models.Blog;
import com.example.travelblog.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {
    private static final String EXTRAS_BLOG = "BLOG_EXTRAS";
    private TextView mTextTitle;
    private TextView mTextDate;
    private TextView mTextAuthor;
    private TextView mTextRating;
    private TextView mTextViews;
    private TextView mTextDescription;
    private RatingBar mRatingBar;
    private ProgressBar mProgressBar;
    private ImageView mImageMain;
    private ImageView mImageAvatar;
    private ImageView mImageBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        mProgressBar = findViewById(R.id.progressBar);
        mImageMain = findViewById(R.id.imageMain);
        mImageAvatar = findViewById(R.id.imageAvatar);
        mImageBack = findViewById(R.id.imageBack);
        mImageBack.setOnClickListener(v -> finish());
        mTextTitle = findViewById(R.id.textTitle);
        mTextDate = findViewById(R.id.textDate);
        mTextAuthor = findViewById(R.id.textAuthor);
        mTextRating = findViewById(R.id.textRating);
        mTextViews = findViewById(R.id.textViews);
        mTextDescription = findViewById(R.id.textDescription);
        mRatingBar = findViewById(R.id.ratingBar);

//        loadData();
        showData(getIntent()
                .getExtras()
                .getParcelable(EXTRAS_BLOG));
    }

    private void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> showData(blogList.get(0)));
            }

            @Override
            public void onError() {
                runOnUiThread(() -> showErrorSnackbar());
            }
        });
    }

    private void showErrorSnackbar() {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, "Error during loading blog articles", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.orange500));
        snackbar.setAction("Retry", v -> {
            loadData();
            snackbar.dismiss();
        });
        snackbar.show();
    }

    private void showData(Blog blog) {
        mProgressBar.setVisibility(View.GONE);
        mTextTitle.setText(blog.getTitle());
        mTextDate.setText(blog.getDate());
        mTextAuthor.setText(blog.getAuthor().getName());
        mTextRating.setText(String.valueOf(blog.getRating()));
        mTextViews.setText(String.format("(%d views)", blog.getViews()));
        mTextDescription.setText(HtmlCompat.fromHtml(blog.getDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        mRatingBar.setRating(blog.getRating());

        Glide.with(this)
                .load(BlogHttpClient.BASE_URL + BlogHttpClient.PATH + blog.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mImageMain);
        Glide.with(this)
                .load(BlogHttpClient.BASE_URL + BlogHttpClient.PATH + blog.getAuthor().getAvatar())
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mImageAvatar);
    }

    public static void startBlogDetailsActivity(Activity activity, Blog blog) {
        Intent intent = new Intent(activity, BlogDetailsActivity.class);
        intent.putExtra(EXTRAS_BLOG, blog);
        activity.startActivity(intent);
    }
}

