package me.cpele.inbop.detail.fragment.itinerary;

import me.cpele.inbop.apiclient.model.Place;

public class ItineraryPresenter implements ItineraryContract.Presenter {

    private ItineraryContract.View mView;

    @Override
    public void onBind(ItineraryContract.View view) {
        mView = view;
    }

    @Override
    public void onUnbind() {
        mView = null;
    }

    @Override
    public void onCheckPosition(Place place) {

        if (mView == null) return;

        if (hasValidPosition(place)) {
            mView.onGetMap();
        } else {
            mView.onHideMap();
        }
    }

    private boolean hasValidPosition(Place place) {
        return place.getPosition() != null
                && place.getPosition().getLat() != null
                && place.getPosition().getLon() != null;
    }
}
