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
import me.cpele.inbop.apiclient.model.PlacePosition;
import me.cpele.inbop.apiclient.model.PlacePrice;
import me.cpele.inbop.detail.fragment.useful_info.StringResource;
import me.cpele.inbop.repository.PlacesRepository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
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
    private String mActualAddress;
    private PlacePosition mActualPosition;
    private String mActualTransport;
    private StringResource mActualStarIndication;
    private Boolean mActualStarred;

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
        detailViewModel.getAddress().observeForever(value -> mActualAddress = value);
        detailViewModel.getPosition().observeForever(value -> mActualPosition = value);
        detailViewModel.getTransports().observeForever(value -> mActualTransport = value);
        detailViewModel.getStarIndicationEvent().observeForever(value -> mActualStarIndication = value);
        detailViewModel.isStarred().observeForever(value -> mActualStarred = value);
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

    @Test
    public void should_set_place_address() {

        mPlace.setPosition(new PlacePosition().setLat(0d).setLon(0d)).setAddress("address");

        mPlaceData.setValue(mPlace);

        assertThat(mActualAddress, equalTo("address"));
    }

    @Test
    public void should_set_place_transports() {

        mPlace.setPosition(new PlacePosition().setLat(0d).setLon(0d)).setTransport("transport");

        mPlaceData.setValue(mPlace);

        assertThat(mActualTransport, equalTo("transport"));
    }

    @Test
    public void should_set_place_position() {

        mPlace.setPosition(new PlacePosition().setLat(2d).setLon(1d));

        mPlaceData.setValue(mPlace);

        assertThat(mActualPosition, equalTo(new PlacePosition().setLon(1d).setLat(2d)));
        assertNull(mActualAddress);
        assertNull(mActualTransport);
    }

    @Test
    public void should_not_display_star_indication_on_first_display() {

        mPlace.setName("just-starred-place");
        mPlace.setStarred(true);

        mPlaceData.setValue(mPlace);

        assertTrue(mActualStarred);
        assertNull(mActualStarIndication);
    }

    @Test
    public void should_display_star_indication_on_next_display() {

        mPlace.setName("just-starred-place");
        mPlace.setStarred(true);
        mPlaceData.setValue(mPlace);

        mPlaceData.setValue(mPlace);

        assertTrue(mActualStarred);
        assertEquals(
                new StringResource(R.string.detail_star_indication, "just-starred-place"),
                mActualStarIndication);
    }

    @Test
    public void should_display_unstar_indication_on_next_display() {

        mPlace.setName("just-unstarred-place");
        mPlace.setStarred(false);
        mPlaceData.setValue(mPlace);

        mPlaceData.setValue(mPlace);

        assertFalse(mActualStarred);
        assertEquals(
                new StringResource(R.string.detail_unstar_indication, "just-unstarred-place"),
                mActualStarIndication);
    }
}