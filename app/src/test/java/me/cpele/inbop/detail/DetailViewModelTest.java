package me.cpele.inbop.detail;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.list.PlacesRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings("SameParameterValue")
public class DetailViewModelTest {

    @Rule
    public InstantTaskExecutorRule mTaskExecutorRule = new InstantTaskExecutorRule();

    private StringBuffer mActualUrl;
    private MutableLiveData<Place> mPlaceData;
    private DetailViewModel mDetailViewModel;

    @Before
    public void setUp() throws Exception {
        mActualUrl = new StringBuffer();
        mPlaceData = new MutableLiveData<>();
        PlacesRepository mock = mock(PlacesRepository.class);
        given(mock.findPlaceById(anyString())).willReturn(mPlaceData);
        String placeId = "place-id";
        mDetailViewModel = new DetailViewModel(mock, placeId);
        mDetailViewModel.setup();
    }

    @Test
    public void should_set_img_url() {

        Place place = newPlace("http://yo");
        mDetailViewModel.getImgUrl().observeForever(mActualUrl::append);

        mPlaceData.setValue(place);

        assertThat(String.valueOf(mActualUrl), equalTo("http://yo"));
    }

    @NonNull
    private Place newPlace(String imgUrl) {
        Place place = new Place();
        place.setImgUrl(imgUrl);
        return place;
    }
}