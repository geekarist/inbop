package me.cpele.inbop.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

class ListViewModelFactory implements ViewModelProvider.Factory {

    private PlacesRepository mModel;

    public ListViewModelFactory(PlacesRepository model) {
        mModel = model;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return modelClass.cast(new ListViewModel(mModel));
    }
}
