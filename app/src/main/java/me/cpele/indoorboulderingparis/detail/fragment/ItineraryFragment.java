package me.cpele.indoorboulderingparis.detail.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;

public class ItineraryFragment extends DetailFragment implements OnMapReadyCallback {

    private static final String ARG_PLACE = "ARG_PLACE";

    @BindView(R.id.itinerary_tv_address)
    TextView addressTextView;

    private MapFragment mapFragment;

    private Place place;

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

        place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        addressTextView.setText(place.getAddress());

        FragmentActivity activity = getActivity();
        FragmentManager fragmentManager = activity.getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.itinerary_mf);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public String getTitle() {
        return "Itinerary";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double lat = 48.8033786;
        double lon = 2.3659669;
        LatLng placePosition = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(placePosition));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placePosition));
    }
}
