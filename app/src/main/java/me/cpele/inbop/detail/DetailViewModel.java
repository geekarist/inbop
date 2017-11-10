package me.cpele.inbop.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.cpele.inbop.R;
import me.cpele.inbop.TextualUtils;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlacePosition;
import me.cpele.inbop.apiclient.model.PlacePrice;
import me.cpele.inbop.detail.fragment.useful_info.StringResource;
import me.cpele.inbop.list.PlacesRepository;

import static me.cpele.inbop.utils.Asserting.notNull;

public class DetailViewModel extends ViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    @NonNull
    private LiveData<Place> mPlace;
    private MediatorLiveData<String> mImgUrl = new MediatorLiveData<>();
    private MediatorLiveData<StringResource> mHours = new MediatorLiveData<>();
    private MediatorLiveData<List<StringResource>> mPrice = new MediatorLiveData<>();
    private MediatorLiveData<String> mDescription = new MediatorLiveData<>();
    private MediatorLiveData<String> mUrl = new MediatorLiveData<>();
    private MediatorLiveData<StringResource> mFacebook = new MediatorLiveData<>();
    private MediatorLiveData<String> mEmail = new MediatorLiveData<>();
    private MediatorLiveData<PlacePosition> mPosition = new MediatorLiveData<>();
    private MediatorLiveData<String> mAddress = new MediatorLiveData<>();
    private MediatorLiveData<String> mTransports = new MediatorLiveData<>();
    private SingleLiveEvent<StringResource> mFacebookClickEvent = new SingleLiveEvent<>();

    public DetailViewModel(PlacesRepository repository, String placeId) {
        mPlace = repository.findPlaceById(placeId);

        mImgUrl.addSource(mPlace, (@NonNull Place place) -> {
            if (place.imgUrl != null) mImgUrl.setValue(place.getImgUrl());
        });

        mHours.addSource(mPlace, (@NonNull Place place) -> {
            if (place.getHours() != null) mHours.setValue(toHoursString(place.getHours()));
        });

        mPrice.addSource(mPlace, (@NonNull Place place) -> {
            if (place.getPrice() != null) mPrice.setValue(toPricesString(place.getPrice()));
        });

        mDescription.addSource(mPlace, (@NonNull Place place) -> {
            if (place.getDescription() != null) mDescription.setValue(place.getDescription());
        });

        mUrl.addSource(mPlace, (@NonNull Place place) -> {
            if (place.getUrl() != null) mUrl.setValue(place.getUrl());
        });

        mFacebook.addSource(mPlace, (@NonNull Place place) -> {
            if (place.getFacebook() != null) {
                mFacebook.setValue(extractPageName(place.getFacebook()));
            }
        });

        mPosition.addSource(mPlace, (@NonNull Place place) -> {
            PlacePosition position = place.getPosition();
            if (position != null && position.getLat() != null && position.getLon() != null) {
                mPosition.setValue(position);
            }
        });

        mAddress.addSource(mPosition, (@NonNull PlacePosition position) -> {
            if (position.getAddress() != null) mAddress.setValue(position.getAddress());
        });

        mTransports.addSource(mPosition, (@NonNull PlacePosition position) -> {
            if (position.getTransport() != null) mTransports.setValue(position.getTransport());
        });
    }

    private StringResource toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return new StringResource(R.string.detail_hours, weekdaysOpening, weekdaysClosing, weekendOpening, weekendClosing);
    }

    private List<StringResource> toPricesString(PlacePrice price) {

        List<StringResource> result = new ArrayList<>();

        String adult = price.getAdult();
        String student = price.getStudent();
        String child = price.getChild();

        if (!TextualUtils.isEmpty(adult)) {
            result.add(new StringResource(R.string.detail_price_adult, adult));
        }

        if (!TextualUtils.isEmpty(student)) {
            result.add(new StringResource(R.string.detail_price_student, student));
        }

        if (!TextualUtils.isEmpty(child)) {
            result.add(new StringResource(R.string.detail_price_child, child));
        }

        return result;
    }

    private StringResource extractPageName(String facebookUrl) {

        if (facebookUrl != null) {
            return new StringResource(facebookUrl);
        }
        return new StringResource(R.string.detail_facebook_unknown);
    }

    @NonNull
    public LiveData<Place> getPlace() {
        return mPlace;
    }

    public LiveData<String> getImgUrl() {
        return mImgUrl;
    }

    public LiveData<StringResource> getHours() {
        return mHours;
    }

    public MutableLiveData<List<StringResource>> getPrice() {
        return mPrice;
    }

    public LiveData<String> getDescription() {
        return mDescription;
    }

    public LiveData<String> getUrl() {
        return mUrl;
    }

    public LiveData<StringResource> getFacebook() {
        return mFacebook;
    }

    public LiveData<String> getEmail() {
        return mEmail;
    }

    public void triggerFacebookClick() {
        notNull(mFacebook.getValue());
        mFacebookClickEvent.setValue(mFacebook.getValue());
    }

    public LiveData<StringResource> getFacebookClickEvent() {
        return mFacebookClickEvent;
    }

    public LiveData<PlacePosition> getPosition() {
        return mPosition;
    }

    public LiveData<String> getAddress() {
        return mAddress;
    }

    public LiveData<String> getTransports() {
        return mTransports;
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
