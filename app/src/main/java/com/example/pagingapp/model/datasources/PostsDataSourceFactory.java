/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.model.datasources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.pagingapp.model.entities.Post;
import com.example.pagingapp.network.Api;

public class PostsDataSourceFactory extends DataSource.Factory<Integer, Post> {

    //To perform network calls
    private Api api;
    
    //A livedata to hold the data source instance
    private MutableLiveData<PostsDataSource> postsDataSourceMutableLiveData;

    public PostsDataSourceFactory(Api api) {
        this.api = api;
        postsDataSourceMutableLiveData = new MutableLiveData<>();
    }

    //Factory method pattern implemented below
    //Where a create method does the job of initializing the objects for client 
    
    @NonNull
    @Override
    public DataSource<Integer, Post> create() {

        PostsDataSource postsDataSource = new PostsDataSource(api);
        postsDataSourceMutableLiveData.postValue(postsDataSource);
        return postsDataSource;

    }

    public MutableLiveData<PostsDataSource> getPostsDataSourceMutableLiveData() {
        return postsDataSourceMutableLiveData;
    }
}
