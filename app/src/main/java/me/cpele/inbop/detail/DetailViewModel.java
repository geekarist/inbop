package me.cpele.inbop.detail;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
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
import me.cpele.inbop.apiclient.model.PlacePrice;
import me.cpele.inbop.detail.fragment.useful_info.StringResource;
import me.cpele.inbop.list.PlacesRepository;

import static me.cpele.inbop.utils.Asserting.notNull;

public class DetailViewModel extends ViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    @NonNull
    private LiveData<Place> mPlace;
    private MutableLiveData<String> mImgUrl = new MutableLiveData<>();
    private MutableLiveData<StringResource> mHours = new MutableLiveData<>();
    private MutableLiveData<List<StringResource>> mPrice = new MutableLiveData<>();
    private MutableLiveData<String> mDescription = new MutableLiveData<>();
    private MutableLiveData<String> mUrl = new MutableLiveData<>();
    private MutableLiveData<StringResource> mFacebook = new MutableLiveData<>();
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private SingleLiveEvent<StringResource> mFacebookClickEvent = new SingleLiveEvent<>();

    public DetailViewModel(PlacesRepository repository, String placeId) {
        mPlace = repository.findPlaceById(placeId);
    }

    public void setup(LifecycleOwner owner) {
        mPlace.observe(owner, this::onPlaceChanged);
    }

    private void onPlaceChanged(Place place) {
        place = notNull(place);

        if (place.getHours() != null) mHours.setValue(toHoursString(place.getHours()));
        if (place.getImgUrl() != null) mImgUrl.setValue(place.getImgUrl());
        if (place.getPrice() != null) mPrice.setValue(toPricesString(place.getPrice()));
        if (place.getDescription() != null) mDescription.setValue(place.getDescription());
        if (place.getUrl() != null) mUrl.setValue(place.getUrl());
        if (place.getFacebook() != null) mFacebook.setValue(extractPageName(place.getFacebook()));
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

    private StringResource toHoursString(PlaceHours hours) {

        String weekdaysOpening = hours.getWeekdays().getOpening();
        String weekdaysClosing = hours.getWeekdays().getClosing();
        String weekendOpening = hours.getWeekend().getOpening();
        String weekendClosing = hours.getWeekend().getClosing();

        return new StringResource(R.string.detail_hours, weekdaysOpening, weekdaysClosing, weekendOpening, weekendClosing);
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
