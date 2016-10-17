package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

interface ListContract {

    interface Model {

        void findAll(Callback<List<Place>> callback);
    }

    interface View {

        void onDisplayProgressBar();

        void onHideProgressBar();

        void onDisplayError();

        void displayPlaces(List<Place> places);
    }

    interface Presenter {

        void attach(View view, Model model);

        void reload();

        void detach();
    }

    interface Callback<T> {

        void success(T data);

        void error();
    }
}
