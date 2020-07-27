/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingapp.databinding.ItemLoadingBinding;

public class LoadingViewHolder extends RecyclerView.ViewHolder {

    private final ItemLoadingBinding itemLoadingBinding;

    public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
        super(itemLoadingBinding.getRoot());
        this.itemLoadingBinding = itemLoadingBinding;
    }
}
