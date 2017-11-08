package me.cpele.inbop.detail;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlaceHoursByDays;
import me.cpele.inbop.apiclient.model.PlacePrice;
import me.cpele.inbop.detail.fragment.useful_info.StringResource;
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

    private MutableLiveData<Place> mPlaceData;
    private String mActualUrl;
    private String mActualImgUrl;
    private StringResource mActualHours;
    private List<StringResource> mActualPrice;
    private String mActualDesc;
    private StringResource mActualFacebook;
    private Place mPlace;

    @Before
    public void setUp() throws Exception {
        mPlaceData = new MutableLiveData<>();
        PlacesRepository mock = mock(PlacesRepository.class);
        given(mock.findPlaceById(anyString())).willReturn(mPlaceData);
        String placeId = "place-id";
        DetailViewModel detailViewModel = new DetailViewModel(mock, placeId);
        mPlace = new Place();
        detailViewModel.getDescription().observeForever(value -> mActualDesc = value);
        detailViewModel.getImgUrl().observeForever(value -> mActualImgUrl = value);
        detailViewModel.getUrl().observeForever(value -> mActualUrl = value);
        detailViewModel.getHours().observeForever(strRes -> mActualHours = strRes);
        detailViewModel.getPrice().observeForever(stringResources -> mActualPrice = stringResources);
        detailViewModel.getUrl().observeForever(url -> mActualUrl = url);
        detailViewModel.getFacebook().observeForever(value -> mActualFacebook = value);
    }

    @Test
    public void should_set_place_facebook() {

        mPlace.setFacebook("facebook-id");

        mPlaceData.setValue(mPlace);

        assertThat(mActualFacebook, equalTo(
                new StringResource(StringResource.RES_ID_EMPTY, "facebook-id")));
    }

    @Test
    public void should_set_place_price() {

        mPlace.setPrice(new PlacePrice().setAdult("adult").setChild("child").setStudent("student"));

        mPlaceData.setValue(mPlace);

        assertThat(mActualPrice, equalTo(Arrays.asList(
                new StringResource(R.string.detail_price_adult, "adult"),
                new StringResource(R.string.detail_price_student, "student"),
                new StringResource(R.string.detail_price_child, "child"))));
    }

    @Test
    public void should_set_place_hours() {

        mPlace.setHours(new PlaceHours()
                .setWeekdays(new PlaceHoursByDays()
                        .setOpening("opening")
                        .setClosing("closing"))
                .setWeekend(new PlaceHoursByDays()
                        .setOpening("opening-we")
                        .setClosing("closing-we")));

        mPlaceData.setValue(mPlace);

        assertThat(mActualHours, equalTo(
                new StringResource(R.string.detail_hours,
                        "opening", "closing", "opening-we", "closing-we")));
    }

    @Test
    public void should_set_place_img_url() {

        mPlace.setImgUrl("http://img-url");

        mPlaceData.setValue(mPlace);

        assertThat(mActualImgUrl, equalTo("http://img-url"));
    }

    @Test
    public void should_set_place_desc() {

        mPlace.setDescription("desc");

        mPlaceData.setValue(mPlace);

        assertThat(mActualDesc, equalTo("desc"));
    }

    @Test
    public void should_set_place_url() {

        mPlace.setUrl("url");

        mPlaceData.setValue(mPlace);

        assertThat(mActualUrl, equalTo("url"));
    }
}