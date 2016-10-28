package me.cpele.indoorboulderingparis.detail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.indoorboulderingparis.BuildConfig;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceHours;
import me.cpele.indoorboulderingparis.apiclient.model.PlacePrice;

public class UsefulInfoFragment extends DetailFragment {

    private static final String ARG_PLACE = "ARG_PLACE";

    @BindView(R.id.useful_iv)
    ImageView imageView;
    @BindView(R.id.useful_tv_hours)
    TextView hoursTextView;
    @BindView(R.id.useful_tv_prices)
    TextView pricesTextView;

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

        Place place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        Glide.with(this).load(BuildConfig.PLACES_API_BASE_URL + place.getImgUrl()).centerCrop().into(imageView);

        String hours = toHoursString(place.getHours());
        hoursTextView.setText(hours);

        pricesTextView.setText(toPricesString(place.getPrice()));

        return view;
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
        return "Useful info";
    }
}
