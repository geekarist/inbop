package me.cpele.inbop.list;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

public class ListContract {

    interface Model {

    }

    interface View {

        void onPlacesLoaded(List<Place> places);

        void onPlacesLoadingFail(Throwable t);
    }

    public interface Presenter {

        void attach(View view);

        void detach();

        void onLoadPlaces();
    }
}
