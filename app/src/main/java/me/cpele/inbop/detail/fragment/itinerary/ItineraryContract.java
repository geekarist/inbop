package me.cpele.inbop.detail.fragment.itinerary;

import me.cpele.inbop.apiclient.model.Place;

public interface ItineraryContract {

    interface Presenter {

        void attach(View view);

        void detach();

        void onCheckPosition(Place place);
    }

    interface View {

        void attach(Presenter presenter);

        void detach();

        void onGetMap();

        void onHideMap();
    }
}
