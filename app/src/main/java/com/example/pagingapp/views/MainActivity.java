package com.example.pagingapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pagingapp.R;
import com.example.pagingapp.adapters.PostsPagedListAdapter;
import com.example.pagingapp.databinding.ActivityMainBinding;
import com.example.pagingapp.model.entities.Post;
import com.example.pagingapp.viewmodel.MainActivityViewModel;
import com.example.pagingapp.viewmodel.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);
        PostsPagedListAdapter postsPagedListAdapter = new PostsPagedListAdapter(Post.postItemCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityMainBinding.postsRv.setLayoutManager(linearLayoutManager);
        activityMainBinding.postsRv.setAdapter(postsPagedListAdapter);

        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(getApplication()))
                .get(MainActivityViewModel.class);

        mainActivityViewModel.getPagedList().observe(this, new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(PagedList<Post> posts) {
                postsPagedListAdapter.submitList(posts);
            }
        });

        mainActivityViewModel.getLoadState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                postsPagedListAdapter.setLoadState(state);
            }
        });

    }
}