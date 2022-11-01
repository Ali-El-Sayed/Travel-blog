package com.ui.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.data.client.BlogArticlesCallback;
import com.data.client.BlogHttpClient;
import com.data.models.Blog;
import com.example.travelblog.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ui.adapter.IOnItemClickListener;
import com.ui.adapter.MainAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IOnItemClickListener {

    private static final int SORT_TITLE = 0; // 1
    private static final int SORT_DATE = 1; // 1

    private int currentSort = SORT_DATE; // 2

    private MainAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private MaterialToolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = findViewById(R.id.appBar);
        mAdapter = new MainAdapter(this);

        toolBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.sort) {
                onSortClicked(); // implemented later in this lesson
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this::loadData);
        loadData();


    }

    private void loadData() {
        refreshLayout.setRefreshing(true);
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> {
                    refreshLayout.setRefreshing(false);
                    mAdapter.setList(blogList);
                });
            }

            @Override
            public void onError() {
                runOnUiThread(() -> {
                    refreshLayout.setRefreshing(false);
                    showErrorSnackbar();
                });
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

    @Override
    public void onItemClicked(Blog blog) {
        BlogDetailsActivity.startBlogDetailsActivity(this, blog);
    }


    private void onSortClicked() {
        String[] items = {"Title", "Date"};

        new MaterialAlertDialogBuilder(this)
                .setTitle("Sort Order")
                .setSingleChoiceItems(items, currentSort, (dialog, which) -> {
                    dialog.dismiss();
                    currentSort = which;
                    sortData();
                }).show();
    }

    private void sortData() {
        if (currentSort == SORT_DATE)
            mAdapter.sortByDate();
        else if (currentSort == SORT_TITLE)
            mAdapter.sortByTitle();
    }
}