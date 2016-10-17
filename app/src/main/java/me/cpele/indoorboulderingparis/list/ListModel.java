package me.cpele.indoorboulderingparis.list;

import android.util.Log;

import java.util.List;

import me.cpele.indoorboulderingparis.CustomApp;
import me.cpele.indoorboulderingparis.apiclient.PlacesService;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListModel implements ListContract.Model {

    private static final String TAG = ListModel.class.getSimpleName();

    private final PlacesService placesService;

    ListModel() {

        placesService = CustomApp.getInstance().getPlacesService();
    }

    @Override
    public void findAll(final ListContract.Callback<List<Place>> presenterCall) {

        placesService.findAll().enqueue(new Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                List<Place> places = response.body().getPlaces();
                presenterCall.success(places);
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                Log.e(TAG, "Error getting places data", t);
                presenterCall.error();
            }
        });
    }
}
