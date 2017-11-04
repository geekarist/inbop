package me.cpele.inbop.detail.fragment.useful_info;

import android.arch.lifecycle.ViewModelProviders;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.TextualUtils;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlacePrice;
import me.cpele.inbop.detail.DetailViewModel;
import me.cpele.inbop.detail.fragment.DetailFragment;
import me.cpele.inbop.list.PlacesRepository;

import static me.cpele.inbop.utils.Asserting.notNull;

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
    private DetailViewModel mDetailViewModel;

    public static UsefulInfoFragment newInstance(Context context, Place place) {

        UsefulInfoFragment fragment = new UsefulInfoFragment();
        fragment.setup(context, place);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_useful_info, container, false);
        ButterKnife.bind(this, view);

        PlacesRepository repository = CustomApp.getInstance().getPlacesRepository();
        String placeId = getArguments().getString(ARG_PLACE);
        DetailViewModel.Factory factory = new DetailViewModel.Factory(repository, placeId);

        mDetailViewModel = ViewModelProviders
                .of(getActivity(), factory)
                .get(DetailViewModel.class);

        mDetailViewModel.getPlace().observe(getActivity(), place -> {

            place = notNull(place);

            if (place.getImgUrl() != null) {
                String imgUrl = place.getImgUrl();
                Glide.with(this).load(imgUrl).centerCrop().into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }

            if (place.getHours() != null) {
                String hours = toHoursString(place.getHours());
                hoursTextView.setText(hours);
                hoursTextView.setVisibility(View.VISIBLE);
            }

            if (place.getPrice() != null) {
                CharSequence prices = toPricesString(place.getPrice());
                pricesTextView.setText(prices);
                pricesTextView.setVisibility(View.VISIBLE);
            }

            if (place.getDescription() != null) {
                String description = place.getDescription();
                specificityTextView.setText(description);
                specificityTextView.setVisibility(View.VISIBLE);
            }

            if (place.getUrl() != null) {
                String url = place.getUrl();
                urlTextView.setText(url);
                urlTextView.setVisibility(View.VISIBLE);
            }

            if (place.getFacebook() != null) {
                String fbPage = extractPageName(place.getFacebook());
                facebookButton.setText(fbPage);
                facebookButton.setVisibility(View.VISIBLE);
            }

            if (place.getEmail() != null) {
                String email = place.getEmail();
                emailTextView.setText(email);
                emailTextView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private String extractPageName(String facebookUrl) {

        if (facebookUrl != null) {
            return facebookUrl;
        }
        return getString(R.string.detail_facebook_unknown, new Object[]{});
    }

    private CharSequence toPricesString(PlacePrice price) {

        List<String> result = new ArrayList<>();

        String adult = price.getAdult();
        String student = price.getStudent();
        String child = price.getChild();

        if (!TextualUtils.isEmpty(adult)) {
            result.add(getString(R.string.detail_price_adult, adult));
        }

        if (!TextualUtils.isEmpty(student)) {
            result.add(getString(R.string.detail_price_student, student));
        }

        if (!TextualUtils.isEmpty(child)) {
            result.add(getString(R.string.detail_price_child, child));
        }

        return TextualUtils.join(" - ", result);
    }

    private String toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return getString(R.string.detail_hours, weekdaysOpening, weekdaysClosing, weekendOpening, weekendClosing);
    }

    @OnClick(R.id.useful_bt_facebook)
    void onClickFacebook() {
        Place placeValue = notNull(mDetailViewModel.getPlace().getValue());
        Log.i(TAG, "Clicked on facebook: " + placeValue);
        String url = getString(R.string.detail_facebook_url, placeValue.getFacebook());
        startFacebookForUrl(url);
    }

    public String getTitle() {
        return getContext().getString(R.string.detail_title_useful);
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
