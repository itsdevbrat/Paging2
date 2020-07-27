/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingapp.model.datasources.PostsDataSource;
import com.example.pagingapp.model.datasources.PostsDataSourceFactory;
import com.example.pagingapp.model.entities.Post;
import com.example.pagingapp.network.Api;

import java.util.concurrent.Executors;

public class PostRepository {

    private final PostsDataSourceFactory postsDataSourceFactory;
    private Api api;
    private LiveData<Integer> loadState;

    public PostRepository(Api api) {
        this.api = api;
        postsDataSourceFactory = new PostsDataSourceFactory(api);
        loadState = Transformations.switchMap(postsDataSourceFactory.getPostsDataSourceMutableLiveData(), PostsDataSource::getLoadState);
    }

    public LiveData<PagedList<Post>> getPagedList() {


        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(5)
                .setPrefetchDistance(1)//300
                .setMaxSize(10)//Maximum size must be at least pageSize + 2*prefetchDist
                .build();

        return new LivePagedListBuilder<>(postsDataSourceFactory, config)
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build();

    }

    public LiveData<Integer> getLoadState() {
        return loadState;
    }

}
