package me.cpele.inbop.detail.fragment.useful_info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.R;
import me.cpele.inbop.TextualUtils;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlacePrice;
import me.cpele.inbop.detail.fragment.DetailFragment;

public class UsefulInfoFragment extends DetailFragment {

    private static final String ARG_PLACE = "ARG_PLACE";
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.useful_iv)
    ImageView imageView;
    @BindView(R.id.useful_tv_hours)
    TextView hoursTextView;
    @BindView(R.id.useful_tv_prices)
    TextView pricesTextView;
    @BindView(R.id.useful_tv_specificity)
    TextView specificityTextView;
    @BindView(R.id.useful_tv_web)
    TextView urlTextView;
    @BindView(R.id.useful_bt_facebook)
    Button facebookButton;
    @BindView(R.id.useful_tv_email)
    TextView emailTextView;

    private Place place;

    public static UsefulInfoFragment newInstance(Context context, Place place) {

        UsefulInfoFragment fragment = new UsefulInfoFragment();
        fragment.setup(context, place);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_useful_info, container, false);
        ButterKnife.bind(this, view);

        place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        if (place.getImgUrl() != null) {
            String imgUrl = place.getImgUrl();
            displayImage(imgUrl);
        }

        if (place.getHours() != null) {
            String hours = toHoursString(place.getHours());
            displayHours(hours);
        }

        if (place.getPrice() != null) {
            CharSequence prices = toPricesString(place.getPrice());
            displayPrices(prices);
        }

        if (place.getDescription() != null) {
            String description = place.getDescription();
            displayDescription(description);
        }

        if (place.getUrl() != null) {
            String url = place.getUrl();
            displayUrl(url);
        }

        if (place.getFacebook() != null) {
            String fbPage = extractPageName(place.getFacebook());
            displayFacebook(fbPage);
        }

        if (place.getEmail() != null) {
            String email = place.getEmail();
            displayEmail(email);
        }

        return view;
    }

    private String extractPageName(String facebookUrl) {

        if (facebookUrl != null) {
            return facebookUrl;
        }
        return buildString(R.string.detail_facebook_unknown);
    }

    private CharSequence toPricesString(PlacePrice price) {

        List<String> result = new ArrayList<>();

        String adult = price.getAdult();
        String student = price.getStudent();
        String child = price.getChild();

        if (!TextualUtils.isEmpty(adult)) {
            result.add(buildString(R.string.detail_price_adult, adult));
        }

        if (!TextualUtils.isEmpty(student)) {
            result.add(buildString(R.string.detail_price_student, student));
        }

        if (!TextualUtils.isEmpty(child)) {
            result.add(buildString(R.string.detail_price_child, child));
        }

        return TextualUtils.join(" - ", result);
    }

    private String toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return buildString(R.string.detail_hours,
                weekdaysOpening, weekdaysClosing,
                weekendOpening, weekendClosing);
    }
    @OnClick(R.id.useful_bt_facebook)
    void onClickFacebook() {
        Log.i(TAG, "Clicked on facebook: " + place);

        if (place.getFacebook() != null) {
            String url = buildString(R.string.detail_facebook_url, place.getFacebook());
            startFacebookForUrl(url);
        } else {
            informNoFacebookForPlace();
        }
    }

    public String getTitle() {
        return getContext().getString(R.string.detail_title_useful);
    }

    public void displayEmail(String email) {
        emailTextView.setText(email);
        emailTextView.setVisibility(View.VISIBLE);
    }

    public String buildString(int msgId, Object... args) {
        return getString(msgId, args);
    }

    public void displayFacebook(String fbPage) {
        facebookButton.setText(fbPage);
        facebookButton.setVisibility(View.VISIBLE);
    }

    public void displayUrl(String url) {
        urlTextView.setText(url);
        urlTextView.setVisibility(View.VISIBLE);
    }

    public void displayDescription(String description) {
        specificityTextView.setText(description);
        specificityTextView.setVisibility(View.VISIBLE);
    }

    public void displayPrices(CharSequence prices) {
        pricesTextView.setText(prices);
        pricesTextView.setVisibility(View.VISIBLE);
    }

    public void displayHours(String hours) {
        hoursTextView.setText(hours);
        hoursTextView.setVisibility(View.VISIBLE);
    }

    public void displayImage(String imgUrl) {
        Glide.with(this).load(imgUrl).centerCrop().into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    public void informNoFacebookForPlace() {
        Log.i(TAG, "No facebook url is defined for place");
    }

    public void startFacebookForUrl(String url) {
        Uri uri = Uri.parse(url);
        Log.d(TAG, String.valueOf(uri));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
