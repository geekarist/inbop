package me.cpele.inbop.detail.fragment.itinerary;

import me.cpele.inbop.apiclient.model.Place;

public interface ItineraryContract {

    interface Presenter {

        void onBind(View view);

        void onUnbind();

        void onCheckPosition(Place place);
    }

    interface View {

        void onBind(Presenter presenter);

        void onUnbind();

        void onGetMap();

        void onHideMap();
    }
}
