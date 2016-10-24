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

public class ItineraryFragment extends DetailFragment {

    private static final String ARG_PLACE = "ARG_PLACE";

    @BindView(R.id.itinerary_tv_address)
    TextView addressTextView;

    public static DetailFragment newInstance(Place place) {

        ItineraryFragment fragment = new ItineraryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PLACE, Parcels.wrap(place));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_itinerary, container, false);
        ButterKnife.bind(this, view);

        Place place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        addressTextView.setText(place.getAddress());

        return view;
    }

    @Override
    public String getTitle() {
        return "Itinerary";
    }
}
