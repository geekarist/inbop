package me.cpele.inbop.detail.fragment.useful_info;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import me.cpele.inbop.BuildConfig;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlacePrice;

public class UsefulInfoPresenter implements UsefulInfoContract.Presenter {

    private UsefulInfoContract.View mView;

    public void loadPlace(Place place) {

        if (place.getImgUrl() != null) {
            String imgUrl = BuildConfig.PLACES_API_BASE_URL + BuildConfig.PLACES_API_PATH + "/../" + place.getImgUrl();
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

    @Override
    public void onBind(UsefulInfoContract.View view) {
        mView = view;
    }

    @Override
    public void onUnbind() {
        mView = null;
    }

    private String extractPageName(String facebookUrl) {

        if (facebookUrl != null) {
            return facebookUrl;
        }
        return mView.buildMessage(R.string.detail_facebook_unknown);
    }

    private CharSequence toPricesString(PlacePrice price) {

        List<String> result = new ArrayList<>();

        String adult = price.getAdult();
        String student = price.getStudent();
        String child = price.getChild();

        if (!TextUtils.isEmpty(adult)) {
            result.add(mView.buildMessage(R.string.detail_price_adult) + adult);
        }

        if (!TextUtils.isEmpty(student)) {
            result.add(mView.buildMessage(R.string.detail_price_student) + student);
        }

        if (!TextUtils.isEmpty(child)) {
            result.add(mView.buildMessage(R.string.detail_price_child) + child);
        }

        return TextUtils.join(" - ", result);
    }

    private String toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return mView.buildMessage(R.string.detail_hours,
                weekdaysOpening, weekdaysClosing,
                weekendOpening, weekendClosing);
    }
}
