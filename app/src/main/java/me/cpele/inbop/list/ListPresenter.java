package me.cpele.inbop.list;

import java.util.List;

import me.cpele.inbop.Consumer;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View mView;

    @Override
    public void attach(ListContract.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void loadPlaces(final Consumer<List<Place>> onSuccess, final Consumer<Throwable> onError) {

        PlacesService placesService = CustomApp.getInstance().getPlacesService();

        placesService.findAll().enqueue(new Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                onSuccess.apply(response.body().getPlaces());
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                onError.apply(t);
            }
        });
    }
}
