package me.cpele.inbop.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public class ListViewModel extends ViewModel {

    @NonNull
    private MutableLiveData<ListResource> mData;

    public ListViewModel(@NonNull ListModel model) {
        mData = model.findAllPlaces();
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
