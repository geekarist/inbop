package me.cpele.inbop.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

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
    @NonNull
    private final Executor mDiskExecutor;

    public PlacesRepository(@NonNull PlacesService placesService, @NonNull PlacesDao dao, @NonNull Executor diskExecutor) {
        mPlacesService = placesService;
        mDao = dao;
        mDiskExecutor = diskExecutor;
        mData = new MutableLiveData<>();
        refresh();
    }

    public void refresh() {

        mData.postValue(ListResource.loading());

        mPlacesService.findAll().enqueue(new retrofit2.Callback<PlaceList>() {

            @Override
            public void onResponse(@NonNull Call<PlaceList> call, @NonNull Response<PlaceList> response) {
                PlaceList body = response.body();
                if (body == null) {
                    NullPointerException exception = new NullPointerException("Body should not be null");
                    mData.postValue(ListResource.error(exception));
                } else {
                    List<Place> places = body.getPlaces();
                    mDiskExecutor.execute(() -> {
                        mDao.insert(places);
                        initStars(places);
                        mData.postValue(ListResource.success(places));
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceList> call, @NonNull Throwable t) {
                ListResource resource = ListResource.error(t);
                mData.postValue(resource);
            }
        });
    }

    @WorkerThread
    private void initStars(List<Place> places) {
        for (Place place : places) {
            boolean starred = mDao.findPlaceByIdSync(place.getId()).isStarred();
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

    public LiveData<Place> findPlaceById(String placeId) {
        return mDao.findPlaceById(placeId);
    }

    public Place findPlaceByIdSync(String placeId) {
        return mDao.findPlaceByIdSync(placeId);
    }
}
