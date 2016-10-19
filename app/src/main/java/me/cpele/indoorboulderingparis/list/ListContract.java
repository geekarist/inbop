package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

interface ListContract {

    interface Model {

        void findAll(Callback<List<Place>> callback);
    }

    interface View {

        void displayProgressBar();

        void hideProgressBar();

        void displayError();

        void displayPlaces(List<Place> places);
    }

    interface Callback<T> {

        void success(T data);

        void error();
    }
}
