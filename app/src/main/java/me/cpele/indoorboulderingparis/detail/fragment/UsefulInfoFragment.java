package me.cpele.indoorboulderingparis.detail.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import me.cpele.indoorboulderingparis.BuildConfig;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceHours;
import me.cpele.indoorboulderingparis.apiclient.model.PlacePrice;

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

    public static DetailFragment newInstance(Place place) {

        UsefulInfoFragment fragment = new UsefulInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARG_PLACE", Parcels.wrap(place));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_useful_info, container, false);
        ButterKnife.bind(this, view);

        place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        if (place.getImgUrl() != null) {
            Glide.with(this).load(BuildConfig.PLACES_API_BASE_URL + place.getImgUrl()).centerCrop().into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        if (place.getHours() != null) {
            String hours = toHoursString(place.getHours());
            hoursTextView.setText(hours);
            hoursTextView.setVisibility(View.VISIBLE);
        }

        if (place.getPrice() != null) {
            pricesTextView.setText(toPricesString(place.getPrice()));
            pricesTextView.setVisibility(View.VISIBLE);
        }

        if (place.getDescription() != null) {
            specificityTextView.setText(place.getDescription());
            specificityTextView.setVisibility(View.VISIBLE);
        }

        if (place.getUrl() != null) {
            urlTextView.setText(place.getUrl());
            urlTextView.setVisibility(View.VISIBLE);
        }

        if (place.getFacebook() != null) {
            String fbPage = extractPageName(place.getFacebook());
            facebookButton.setText(fbPage);
            facebookButton.setVisibility(View.VISIBLE);
        }

        if (place.getEmail() != null) {
            emailTextView.setText(place.getEmail());
            emailTextView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @OnClick(R.id.useful_bt_facebook)
    void onClickFacebook() {

        Log.i(TAG, "Clicked on facebook: " + place);

        if (place.getFacebook() != null) {
            String url = getString(R.string.detail_facebook_url, place.getFacebook());
            Uri uri = Uri.parse(url);
            Log.d(TAG, String.valueOf(uri));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            Log.i(TAG, "No facebook url is defined for place");
        }
    }

    private String extractPageName(String facebookUrl) {

        if (facebookUrl != null) {
            return facebookUrl;
        }
        return getString(R.string.detail_facebook_unknown);
    }

    private CharSequence toPricesString(PlacePrice price) {

        List<String> result = new ArrayList<>();

        String adult = price.getAdult();
        String student = price.getStudent();
        String child = price.getChild();

        if (!TextUtils.isEmpty(adult)) {
            result.add(getString(R.string.detail_price_adult) + adult);
        }

        if (!TextUtils.isEmpty(student)) {
            result.add(getString(R.string.detail_price_student) + student);
        }

        if (!TextUtils.isEmpty(child)) {
            result.add(getString(R.string.detail_price_child) + child);
        }

        return TextUtils.join(" - ", result);
    }

    private String toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return getString(R.string.detail_hours,
                weekdaysOpening, weekdaysClosing,
                weekendOpening, weekendClosing);
    }

    @Override
    public String getTitle() {
        return getString(R.string.detail_title_useful);
    }
}
