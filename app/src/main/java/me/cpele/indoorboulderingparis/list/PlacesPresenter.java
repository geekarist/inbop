package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

class PlacesPresenter implements PlacesContract.Callback<List<Place>> {

    private PlacesContract.View view;
    private PlacesContract.Model model;

    PlacesPresenter() {
    }

    void detach() {

        this.view = null;
        this.model = null;
    }

    void attach(PlacesContract.View view, PlacesContract.Model model) {

        this.view = view;
        this.model = model;
    }

    void reload() {

        view.displayProgressBar();

        model.findAll(this);
    }

    @Override
    public void success(List<Place> data) {
        if (view != null) {
            view.displayPlaces(data);
            view.hideProgressBar();
        }
    }

    @Override
    public void error() {
        if (view != null) {
            view.displayError();
        }
    }
}
