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
    
    //A livedata which has a integer that holds the state of loading
    MutableLiveData<Integer> loadState;

    public PostsDataSource(Api api) {
        loadState = new MutableLiveData<>();
        this.api = api;
    }

    //This below method is called only once and the first method to be called 
    
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Post> callback) {

        final int currentPage = 1;

        //setting load state so that the UI can know that progress of data fetching
        loadState.postValue(Constants.ONGOING);
        
        //Simple retrofit call to get the first page of data
        api.getPosts(currentPage).enqueue(new Callback<List<Post>>() {
        
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
    
                    //IMPORTANT: once the first page is load we increment the page count and pass it to callback.onResult()  
                    callback.onResult(response.body(), null, currentPage + 1);
                    
                    //setting load state so that the UI can know data fetching is successful
                    loadState.postValue(Constants.SUCCESS);
                    
                } else {
                
                    //since no data was fetched we pass empty list and dont increment the page number 
                    //so that it can retry the fetching of 1st page
                    callback.onResult(new ArrayList<>(), null, currentPage);
                    
                    //setting load state so that the UI can know data fetching failed
                    loadState.postValue(Constants.FAILED);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            
                    //since no data was fetched we pass empty list and dont increment the page number 
                    //so that it can retry the fetching of 1st page
                    callback.onResult(new ArrayList<>(), null, currentPage);
                    
                    //setting load state so that the UI can know data fetching failed
                    loadState.postValue(Constants.FAILED);
            
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Post> callback) {

    }

    // this method will be called every time after the loadInitial() is been called 
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Post> callback) {

        // we get the current page from params
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

    //UI will call this method to get the livedata of loading, subscribe to it, and update the UI state accordingly 
    public MutableLiveData<Integer> getLoadState() {
        return loadState;
    }
}
