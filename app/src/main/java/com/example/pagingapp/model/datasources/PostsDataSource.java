/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.model.datasources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.pagingapp.Constants;
import com.example.pagingapp.model.entities.Post;
import com.example.pagingapp.network.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsDataSource extends PageKeyedDataSource<Integer, Post> {

    Api api;
    MutableLiveData<Integer> loadState;

    public PostsDataSource(Api api) {
        loadState = new MutableLiveData<>();
        this.api = api;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Post> callback) {

        final int currentPage = 1;

        loadState.postValue(Constants.ONGOING);
        api.getPosts(currentPage).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), null, currentPage + 1);
                    loadState.postValue(Constants.SUCCESS);
                } else {
                    callback.onResult(new ArrayList<>(), null, currentPage);
                    loadState.postValue(Constants.FAILED);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onResult(new ArrayList<>(), null, currentPage);
                loadState.postValue(Constants.FAILED);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Post> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Post> callback) {

        final int currentPage = params.key;
        loadState.postValue(Constants.ONGOING);

        api.getPosts(currentPage).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    loadState.postValue(Constants.SUCCESS);
                    callback.onResult(response.body(), currentPage + 1);
                } else {
                    callback.onResult(new ArrayList<>(), currentPage);
                    loadState.postValue(Constants.FAILED);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onResult(new ArrayList<>(), currentPage);
                loadState.postValue(Constants.FAILED);
            }
        });
    }

    public MutableLiveData<Integer> getLoadState() {
        return loadState;
    }
}
