package me.cpele.indoorboulderingparis.detail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceHours;

public class GetReadyFragment extends DetailFragment {

    private static final String ARG_PLACE = "ARG_PLACE";

    @BindView(R.id.fragment_transport_tv_address)
    TextView addressTextView;
    @BindView(R.id.fragment_transport_tv_hours)
    TextView hoursTextView;

    private Place place;

    public static DetailFragment newInstance(Place place) {

        GetReadyFragment fragment = new GetReadyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PLACE, Parcels.wrap(place));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_getready, container, false);
        ButterKnife.bind(this, view);

        place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        addressTextView.setText(place.getAddress());

        String hours = buildHoursLabel(place.getHours());
        hoursTextView.setText(hours);

        return view;
    }

    private String buildHoursLabel(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return getString(R.string.transport_hours,
                weekdaysOpening, weekdaysClosing,
                weekendOpening, weekendClosing);
    }

    @Override
    public String getTitle() {
        return "Get ready";
    }
}
