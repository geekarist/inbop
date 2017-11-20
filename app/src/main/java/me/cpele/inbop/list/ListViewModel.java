package me.cpele.inbop.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import me.cpele.inbop.repository.PlacesRepository;

public class ListViewModel extends ViewModel {

    private final PlacesRepository mModel;
    @NonNull
    private MutableLiveData<ListResource> mData;

    public ListViewModel(@NonNull PlacesRepository model) {
        mModel = model;
        mData = mModel.getAllPlaces();
    }

    @NonNull
    public MutableLiveData<ListResource> getData() {
        return mData;
    }

    public void toggleStar(String id) {
        mModel.toggleStar(id);
    }
}
