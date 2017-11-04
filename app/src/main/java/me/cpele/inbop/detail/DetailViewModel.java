package me.cpele.inbop.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.list.PlacesRepository;

public class DetailViewModel extends ViewModel {

    private LiveData<Place> mPlace;

    public DetailViewModel(PlacesRepository repository, String placeId) {
        mPlace = repository.findPlaceById(placeId);
    }

    public LiveData<Place> getPlace() {
        return mPlace;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final PlacesRepository mRepo;
        private final String mPlaceId;

        public Factory(PlacesRepository repo, String placeId) {
            mRepo = repo;
            mPlaceId = placeId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return modelClass.cast(new DetailViewModel(mRepo, mPlaceId));
        }
    }
}
