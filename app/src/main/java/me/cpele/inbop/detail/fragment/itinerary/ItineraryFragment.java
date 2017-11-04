package me.cpele.inbop.detail.fragment.itinerary;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.fragment.DetailFragment;

public class ItineraryFragment
        extends DetailFragment
        implements OnMapReadyCallback, ItineraryContract.View {

    @BindView(R.id.itinerary_tv_address)
    TextView mAddressTextViewddressTextView;

    private Place mPlace;
    private ItineraryContract.Presenter mPresenter;
    private MapFragment mMapFragment;
    private FragmentManager mFragmentManager;

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

        String placeId = getArguments().getString(ARG_PLACE);
        mPlace = CustomApp.getInstance().getPlacesRepository().findPlaceByIdSync(placeId);

        mAddressTextViewddressTextView.setText(mPlace.getPosition().getAddress());

        FragmentActivity activity = getActivity();
        mFragmentManager = activity.getFragmentManager();
        mMapFragment = (MapFragment) mFragmentManager.findFragmentById(R.id.itinerary_mf);

        mPresenter.onCheckPosition(mPlace);

        return view;
    }

    @Override
    public void onHideMap() {
        mFragmentManager.beginTransaction().hide(mMapFragment).commit();
    }

    @Override
    public void onGetMap() {
        mMapFragment.getMapAsync(this);
    }

    @Override
    public String getTitle() {
        return getContext().getString(R.string.detail_title_itinerary);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double lat = mPlace.getPosition().getLat();
        double lon = mPlace.getPosition().getLon();
        LatLng placePosition = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(placePosition));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placePosition, 15));
    }

    @Override
    public void onBind(ItineraryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onUnbind() {
        mPresenter = null;
    }
}
