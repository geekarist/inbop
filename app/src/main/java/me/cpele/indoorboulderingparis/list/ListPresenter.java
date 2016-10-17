package me.cpele.indoorboulderingparis.list;

import java.util.List;

import me.cpele.indoorboulderingparis.CustomApp;
import me.cpele.indoorboulderingparis.apiclient.PlacesService;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPresenter {

    private ListContract.View view;
    private ListContract.Model model;

    public ListPresenter() {
    }

    public void detach() {

        this.view = null;
        this.model = null;
    }

    public void attach(ListContract.View view, ListContract.Model model) {

        this.view = view;
        this.model = model;
    }

    public void reload() {

        view.onDisplayProgressBar();

        PlacesService placesService = CustomApp.getInstance().getPlacesService();

        placesService.findAll().enqueue(new Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                List<Place> places = response.body().getPlaces();
                view.displayPlaces(places);
                view.onHideProgressBar();
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                view.onDisplayError();
            }
        });
    }
}
