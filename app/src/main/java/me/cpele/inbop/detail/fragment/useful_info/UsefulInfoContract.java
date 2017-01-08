package me.cpele.inbop.detail.fragment.useful_info;

import me.cpele.inbop.apiclient.model.Place;

public interface UsefulInfoContract {

    interface Presenter {

        void onBind(View view);

        void onUnbind();

        void loadPlace(Place place);
    }

    interface View {

        void onBind(Presenter presenter);

        void onUnbind();

        void displayImage(String imgUrl);

        void displayHours(String hours);

        void displayPrices(CharSequence prices);

        void displayDescription(String description);

        void displayUrl(String url);

        void displayFacebook(String fbPage);

        void displayEmail(String email);

        String buildMessage(int msgId, Object... args);
    }
}
