package me.cpele.inbop.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public class ListViewModel extends ViewModel {

    private final ListModel mModel;
    @NonNull
    private MutableLiveData<ListResource> mData;

    public ListViewModel(@NonNull ListModel model) {
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

//    @Override
//    public void checkOrientation(boolean landscape) {
//        if (landscape) {
//            mView.onSetupForLandscape();
//        } else {
//            mView.onSetupForPortrait();
//        }
//    }
}
