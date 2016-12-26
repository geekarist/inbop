package me.cpele.inbop.list;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

public class ListContract {

    public interface Model {

        void attach(Presenter presenter);

        void detach();

        void onLoadPlaces();
    }

    interface View {

        void onPlacesLoaded(List<Place> places);

        void onPlacesLoadingFail(Throwable t);
    }

    public interface Presenter {

        void attach(View view, Model model);

        void detach();

        void onLoadPlaces();

        void onPlacesLoaded(List<Place> places);

        void onPlacesLoadFailure(Throwable t);
    }
}
