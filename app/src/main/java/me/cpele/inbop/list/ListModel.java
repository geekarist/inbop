package me.cpele.inbop.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Response;

public class ListModel implements ListContract.Model {

    @Nullable
    private ListContract.Presenter mPresenter;
    @NonNull
    private PlacesService mPlacesService;

    public ListModel(@NonNull PlacesService placesService) {
        mPlacesService = placesService;
    }

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

        mPlacesService.findAll().enqueue(new retrofit2.Callback<PlaceList>() {

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
