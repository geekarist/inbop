package me.cpele.inbop.list;

import android.support.annotation.Nullable;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

public class ListPresenter implements ListContract.Presenter {

    @Nullable
    private ListContract.View mView;
    @Nullable
    private ListContract.Model mModel;

    @Override
    public void attach(ListContract.View view, ListContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }

    @Override
    public void onLoadPlaces() {
        if (mModel != null) mModel.onLoadPlaces();
    }

    @Override
    public void onPlacesLoaded(List<Place> places) {
        if (mView != null) mView.onPlacesLoaded(places);
    }

    @Override
    public void onPlacesLoadFailure(Throwable t) {
        if (mView != null) mView.onPlacesLoadingFail(t);
    }

    @Override
    public void onCheckOrientation(boolean landscape) {
        if (landscape) {
            if (mView != null) mView.onSetupForLandscape();
        } else {
            if (mView != null) mView.onSetupForPortrait();
        }
    }
}
