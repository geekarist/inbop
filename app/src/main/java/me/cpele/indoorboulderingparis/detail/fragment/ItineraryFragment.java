package me.cpele.indoorboulderingparis.detail.fragment;

import android.app.FragmentManager;
import android.content.Context;
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

    public static DetailFragment newInstance(Context context, Place place) {

        ItineraryFragment fragment = new ItineraryFragment();
        fragment.setup(context, place);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_itinerary, container, false);
        ButterKnife.bind(this, view);

        place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        addressTextView.setText(place.getPosition().getAddress());

        FragmentActivity activity = getActivity();
        FragmentManager fragmentManager = activity.getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.itinerary_mf);

        if (hasValidPosition(place)) {
            mapFragment.getMapAsync(this);
        } else {
            fragmentManager.beginTransaction().hide(mapFragment).commit();
        }

        return view;
    }

    private boolean hasValidPosition(Place place) {
        return place.getPosition() != null
                && place.getPosition().getLat() != null
                && place.getPosition().getLon() != null;
    }

    @Override
    public String getTitle() {
        return getContext().getString(R.string.detail_title_itinerary);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double lat = place.getPosition().getLat();
        double lon = place.getPosition().getLon();
        LatLng placePosition = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(placePosition));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placePosition, 15));
    }
}
