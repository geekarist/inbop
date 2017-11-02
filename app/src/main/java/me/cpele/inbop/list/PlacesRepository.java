package me.cpele.inbop.list;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceList;
import me.cpele.inbop.db.PlacesDao;
import me.cpele.inbop.utils.Asserting;
import retrofit2.Call;
import retrofit2.Response;

public class PlacesRepository {

    @NonNull
    private PlacesService mPlacesService;
    @NonNull
    private MutableLiveData<ListResource> mData;
    @NonNull
    private PlacesDao mDao;

    public PlacesRepository(@NonNull PlacesService placesService, @NonNull PlacesDao dao) {
        mPlacesService = placesService;
        mDao = dao;
        mData = new MutableLiveData<>();
        refresh();
    }

    public void refresh() {

        mData.postValue(ListResource.loading());

        mPlacesService.findAll().enqueue(new retrofit2.Callback<PlaceList>() {

            @Override
            public void onResponse(@NonNull Call<PlaceList> call, @NonNull Response<PlaceList> response) {
                PlaceList body = response.body();
                ListResource resource;
                if (body == null) {
                    NullPointerException exception = new NullPointerException("Body should not be null");
                    resource = ListResource.error(exception);
                } else {
                    List<Place> places = body.getPlaces();
                    mDao.insert(places);
                    initStars(places);
                    resource = ListResource.success(places);
                }
                mData.postValue(resource);
            }

            @Override
            public void onFailure(@NonNull Call<PlaceList> call, @NonNull Throwable t) {
                ListResource resource = ListResource.error(t);
                mData.postValue(resource);
            }
        });
    }

    private void initStars(List<Place> places) {
        for (Place place : places) {
            boolean starred = mDao.findPlaceById(place.getId()).isStarred();
            place.setStarred(starred);
        }
    }

    @NonNull
    public MutableLiveData<ListResource> getAllPlaces() {
        return mData;
    }

    public void toggleStar(@NonNull String id) {
        ListResource resource = mData.getValue();
        resource = Asserting.notNull(resource);
        resource.places = Asserting.notNull(resource.places);
        List<Place> places = deepishCopy(resource.places);

        mDao.toggleStar(id);
        for (Place place : places) {
            if (id.equals(place.getId())) {
                place.setStarred(!place.isStarred());
                break;
            }
        }
        resource.places = places;
        mData.postValue(resource);
    }

    private List<Place> deepishCopy(@Nullable List<Place> places) {
        if (places == null) return null;

        List<Place> result = new ArrayList<>();

        for (Place place : places) {
            result.add(new Place(place));
        }

        return result;
    }
}
