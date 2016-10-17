package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

class PlacesPresenter implements PlacesContract.Presenter {

    private PlacesContract.View view;
    private PlacesContract.Model model;

    PlacesPresenter() {
    }

    @Override
    public void detach() {

        this.view = null;
        this.model = null;
    }

    @Override
    public void attach(PlacesContract.View view, PlacesContract.Model model) {

        this.view = view;
        this.model = model;
    }

    public void reload() {

        view.displayProgressBar();

        model.findAll(new PlacesContract.Callback<List<Place>>() {

            @Override
            public void success(List<Place> places) {
                if (view != null) {
                    view.displayPlaces(places);
                    view.hideProgressBar();
                }
            }

            @Override
            public void error() {
                if (view != null) {
                    view.displayError();
                }
            }
        });
    }
}
