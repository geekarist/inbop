package me.cpele.inbop.list;

import me.cpele.inbop.CustomApp;
import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Response;

public class ListModel implements ListContract.Model {

    private ListContract.Presenter mPresenter;

    @Override
    public void attach(ListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void detach() {
        mPresenter = null;
    }

    @Override
    public void onLoadPlaces() {

        PlacesService placesService = CustomApp.getInstance().getPlacesService();

        placesService.findAll().enqueue(new retrofit2.Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                if (mPresenter != null) mPresenter.onPlacesLoaded(response.body().getPlaces());
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                if (mPresenter != null) mPresenter.onPlacesLoadFailure(t);
            }
        });
    }

}
