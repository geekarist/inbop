package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;
    private ListContract.Model model;

    ListPresenter() {
    }

    @Override
    public void detach() {

        this.view = null;
        this.model = null;
    }

    @Override
    public void attach(ListContract.View view, ListContract.Model model) {

        this.view = view;
        this.model = model;
    }

    public void reload() {

        view.onDisplayProgressBar();

        model.findAll(new ListContract.Callback<List<Place>>() {

            @Override
            public void success(List<Place> places) {
                view.displayPlaces(places);
                view.onHideProgressBar();
            }

            @Override
            public void error() {
                view.onDisplayError();
            }
        });
    }
}
