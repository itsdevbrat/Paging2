/*
 * Created by Devbrat on 7/26/2020
 */

package com.example.pagingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public MainActivityViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class))
            return (T) new MainActivityViewModel(application);
        throw new IllegalArgumentException("Cant Find View Model Class");
    }
}
