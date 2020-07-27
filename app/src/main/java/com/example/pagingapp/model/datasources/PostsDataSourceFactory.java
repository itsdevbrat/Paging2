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

    private Api api;
    private MutableLiveData<PostsDataSource> postsDataSourceMutableLiveData;

    public PostsDataSourceFactory(Api api) {
        this.api = api;
        postsDataSourceMutableLiveData = new MutableLiveData<>();
    }

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




