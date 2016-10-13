package me.cpele.indoorboulderingparis;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(getClass().getSimpleName(), "TODO: Center map on Paris area");
    }
}
