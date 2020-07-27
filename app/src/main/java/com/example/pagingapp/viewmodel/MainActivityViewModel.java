/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.pagingapp.model.entities.Post;
import com.example.pagingapp.model.repositories.PostRepository;
import com.example.pagingapp.network.Api;
import com.example.pagingapp.network.RetrofitClient;


public class MainActivityViewModel extends AndroidViewModel {

    private final Api api;
    private final LiveData<PagedList<Post>> pagedList;
    PostRepository postRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        api = RetrofitClient.getRetrofitInstance().create(Api.class);
        postRepository = new PostRepository(api);
        pagedList = postRepository.getPagedList();
    }

    public LiveData<PagedList<Post>> getPagedList() {
        return pagedList;
    }

    public LiveData<Integer> getLoadState(){
        return postRepository.getLoadState();
    }
}
