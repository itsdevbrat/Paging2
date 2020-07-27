/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingapp.databinding.ItemPostBinding;
import com.example.pagingapp.model.entities.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    ItemPostBinding itemPostBinding;

    public PostViewHolder(@NonNull ItemPostBinding itemPostBinding) {
        super(itemPostBinding.getRoot());
        this.itemPostBinding = itemPostBinding;
    }

    public void bindTo(Post item) {
        itemPostBinding.setPost(item);
        itemPostBinding.executePendingBindings();
    }
}
