package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

class ListPresenter implements ListContract.Callback<List<Place>> {

    private ListContract.View view;
    private ListContract.Model model;

    ListPresenter(ListContract.View view, ListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    void detach() {

        this.view = null;
        this.model = null;
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
