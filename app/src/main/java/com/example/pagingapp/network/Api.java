package com.example.pagingapp.network;

import com.example.pagingapp.model.entities.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("posts/")
    Call<List<Post>> getPosts(@Query("page") int pageNumber);

}
