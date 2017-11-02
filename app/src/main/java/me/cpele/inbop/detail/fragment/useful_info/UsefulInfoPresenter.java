package me.cpele.inbop.detail.fragment.useful_info;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.cpele.inbop.R;
import me.cpele.inbop.TextualUtils;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlacePrice;

public class UsefulInfoPresenter {

    private UsefulInfoFragment mView;

    public void openFacebookForPlace(@NonNull Place place) {

        if (mView == null) return;

        if (place.getFacebook() != null) {
            String url = mView.buildString(R.string.detail_facebook_url, place.getFacebook());
            mView.startFacebookForUrl(url);
        } else {
            mView.informNoFacebookForPlace();
        }
    }

    public void loadPlace(Place place) {

        if (mView == null) return;

        if (place.getImgUrl() != null) {
            String imgUrl = place.getImgUrl();
            mView.displayImage(imgUrl);
        }

        if (place.getHours() != null) {
            String hours = toHoursString(place.getHours());
            mView.displayHours(hours);
        }

        if (place.getPrice() != null) {
            CharSequence prices = toPricesString(place.getPrice());
            mView.displayPrices(prices);
        }

        if (place.getDescription() != null) {
            String description = place.getDescription();
            mView.displayDescription(description);
        }

        if (place.getUrl() != null) {
            String url = place.getUrl();
            mView.displayUrl(url);
        }

        if (place.getFacebook() != null) {
            String fbPage = extractPageName(place.getFacebook());
            mView.displayFacebook(fbPage);
        }

        if (place.getEmail() != null) {
            String email = place.getEmail();
            mView.displayEmail(email);
        }
    }

    public void onBind(UsefulInfoFragment view) {
        mView = view;
    }

    public void onUnbind() {
        mView = null;
    }

    private String extractPageName(String facebookUrl) {

        if (facebookUrl != null) {
            return facebookUrl;
        }
        return mView.buildString(R.string.detail_facebook_unknown);
    }

    private CharSequence toPricesString(PlacePrice price) {

        List<String> result = new ArrayList<>();

        String adult = price.getAdult();
        String student = price.getStudent();
        String child = price.getChild();

        if (!TextualUtils.isEmpty(adult)) {
            result.add(mView.buildString(R.string.detail_price_adult, adult));
        }

        if (!TextualUtils.isEmpty(student)) {
            result.add(mView.buildString(R.string.detail_price_student, student));
        }

        if (!TextualUtils.isEmpty(child)) {
            result.add(mView.buildString(R.string.detail_price_child, child));
        }

        return TextualUtils.join(" - ", result);
    }

    private String toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return mView.buildString(R.string.detail_hours,
                weekdaysOpening, weekdaysClosing,
                weekendOpening, weekendClosing);
    }
}
