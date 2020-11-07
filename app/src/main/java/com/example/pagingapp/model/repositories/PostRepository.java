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
        
        //IMPORTANT:
        //since we can only access PostsDataSourceFactory which has no method 
        //to access the loading state present within PostsDataSource
        //we use this Transformations API which helps us to get the load state livedata from PostsDataSource 
        //which we can let other classes to access it from here by creating a getter
        loadState = Transformations.switchMap(postsDataSourceFactory.getPostsDataSourceMutableLiveData(), PostsDataSource::getLoadState);
        
    }

    public LiveData<PagedList<Post>> getPagedList() {

        //There are some parameters that we can config according to our use case
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(5) //Configures how many data items in one page to be supplied to recycler view
                .setPrefetchDistance(1) // in first call how many pages to load 
                .setMaxSize(10) //Maximum PagedList size must be at least pageSize + 2*prefetchDist
                .build();                

        return new LivePagedListBuilder<>(postsDataSourceFactory, config)
                .setFetchExecutor(Executors.newFixedThreadPool(5)) // Use five threads to do the fetching operations 
                .build();

    }

    public LiveData<Integer> getLoadState() {
        return loadState;
    }

}
