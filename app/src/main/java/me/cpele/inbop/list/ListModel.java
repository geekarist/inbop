package me.cpele.inbop.list;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Response;

public class ListModel {

    @NonNull
    private PlacesService mPlacesService;

    public ListModel(@NonNull PlacesService placesService) {
        mPlacesService = placesService;
    }

    @NonNull
    public MutableLiveData<ListResource> findAllPlaces() {

        MutableLiveData<ListResource> data = new MutableLiveData<>();

        data.postValue(ListResource.loading());

        mPlacesService.findAll().enqueue(new retrofit2.Callback<PlaceList>() {

            @Override
            public void onResponse(@NonNull Call<PlaceList> call, @NonNull Response<PlaceList> response) {
                PlaceList body = response.body();
                ListResource resource;
                if (body == null) {
                    NullPointerException exception = new NullPointerException("Body should not be null");
                    resource = ListResource.error(exception);
                } else {
                    resource = ListResource.success(body.getPlaces());
                }
                data.postValue(resource);
            }

            @Override
            public void onFailure(@NonNull Call<PlaceList> call, @NonNull Throwable t) {
                ListResource resource = ListResource.error(t);
                data.postValue(resource);
            }
        });

        return data;
    }

}
