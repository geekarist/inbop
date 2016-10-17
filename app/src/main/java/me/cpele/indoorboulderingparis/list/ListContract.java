package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.apiclient.model.Place;

public interface ListContract {

    interface Model {

    }

    interface View {

        void onDisplayProgressBar();

        void onHideProgressBar();

        void onDisplayError();

        void displayPlaces(List<Place> places);
    }

    interface Presenter {

    }
}
