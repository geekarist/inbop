package me.cpele.inbop.detail.fragment.itinerary;

import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlacePosition;
import me.cpele.inbop.detail.DetailViewModel;
import me.cpele.inbop.detail.fragment.DetailFragment;

import static me.cpele.inbop.utils.Asserting.notNull;

public class ItineraryFragment extends DetailFragment {

    @BindView(R.id.itinerary_tv_address)
    TextView mAddressTextView;
    @BindView(R.id.itinerary_tv_address_label)
    TextView mAddressLabelTextView;
    @BindView(R.id.itinerary_tv_transport)
    TextView mTransportsTextView;
    @BindView(R.id.itinerary_tv_transport_label)
    TextView mTransportsLabelTextView;

    private MapFragment mMapFragment;

    public static ItineraryFragment newInstance(Context context, Place place) {

        ItineraryFragment fragment = new ItineraryFragment();
        fragment.setup(context, place);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_itinerary, container, false);
        ButterKnife.bind(this, view);

        DetailViewModel viewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

        viewModel.getPosition().observe(this, (@NonNull PlacePosition position) -> {

            FragmentManager fragmentManager = getActivity().getFragmentManager();
            mMapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.itinerary_mf);

            mMapFragment.getMapAsync(googleMap -> {
                double lat = position.getLat();
                double lon = position.getLon();
                LatLng placePosition = new LatLng(lat, lon);
                googleMap.addMarker(new MarkerOptions().position(placePosition));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placePosition, 15));
                notNull(mMapFragment.getView()).setVisibility(View.VISIBLE);
            });
        });

        viewModel.getAddress().observe(this, (@NonNull String address) -> {
            mAddressTextView.setText(address);
            mAddressLabelTextView.setVisibility(View.VISIBLE);
            mAddressTextView.setVisibility(View.VISIBLE);
        });

        viewModel.getTransports().observe(this, (@NonNull String value) -> {
            mTransportsTextView.setText(value);
            mTransportsLabelTextView.setVisibility(View.VISIBLE);
            mTransportsTextView.setVisibility(View.VISIBLE);
        });

        return view;
    }

    @Override
    public String getTitle() {
        return getContext().getString(R.string.detail_title_itinerary);
    }
}
