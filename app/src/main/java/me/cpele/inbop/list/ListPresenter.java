package me.cpele.inbop.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public class ListPresenter extends ViewModel {

    @NonNull
    private ListModel mModel;

    @NonNull
    private MutableLiveData<ListResource> mData;

    public ListPresenter(@NonNull ListModel model) {
        mModel = model;
        mData = mModel.findAllPlaces();
    }

    @NonNull
    public MutableLiveData<ListResource> getData() {
        return mData;
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
