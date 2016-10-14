package me.cpele.indoorboulderingparis;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class OverviewMapFragment extends MapFragment implements OnMapReadyCallback {

    private static final String TAG = OverviewMapFragment.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "Center map on Paris area");
        LatLng paris = new LatLng(48.853861, 2.3283237);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 12));
    }
}
