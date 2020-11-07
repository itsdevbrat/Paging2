/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingapp.Constants;
import com.example.pagingapp.R;
import com.example.pagingapp.databinding.ItemLoadingBinding;
import com.example.pagingapp.databinding.ItemPostBinding;
import com.example.pagingapp.model.entities.Post;

public class PostsPagedListAdapter extends PagedListAdapter<Post, RecyclerView.ViewHolder> {

    // Two view types 1st to show a loading spinner and the other to show a post
    private static final int TYPE_LOAD = 1;
    private static final int TYPE_POST = 2;
    
    //Loading state : ONGOING, FAILED, SUCCESS defined in the constants file
    private Integer state;
    
    //Diff callback for efficient handling of updates in recycler view 
    public PostsPagedListAdapter(@NonNull DiffUtil.ItemCallback<Post> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_POST) {
            ItemPostBinding itemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_post,
                    parent,
                    false);
            return new PostViewHolder(itemPostBinding);
        } else {
            ItemLoadingBinding itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_loading,
                    parent,
                    false);
            return new LoadingViewHolder(itemLoadingBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder)
            ((PostViewHolder) holder).bindTo(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
    
        //IMPORTANT: When to show a loading spinner
        //if((we are at the last position of previous page)  &&  (the loading state is either ONGOING or FAILED))
        if (position == getItemCount()-1  && state != null && !state.equals(Constants.SUCCESS))
            return TYPE_LOAD;
        else
            return TYPE_POST;
    }

    public void setLoadState(Integer state) {
        this.state = state;
    }
}
