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
    private DetailViewModel mDetailViewModel;
    private String mActualUrl;
    private String mActualImgUrl;
    private StringResource mActualHours;
    private List<StringResource> mActualPrice;
    private String mActualDesc;

    @Before
    public void setUp() throws Exception {
        mPlaceData = new MutableLiveData<>();
        PlacesRepository mock = mock(PlacesRepository.class);
        given(mock.findPlaceById(anyString())).willReturn(mPlaceData);
        String placeId = "place-id";
        mDetailViewModel = new DetailViewModel(mock, placeId);
        mDetailViewModel.setup();
    }

    @Test
    public void should_set_place_value() {

        Place place = new Place()
                .setImgUrl("http://img-url")
                .setHours(new PlaceHours()
                        .setWeekdays(new PlaceHoursByDays()
                                .setOpening("opening")
                                .setClosing("closing"))
                        .setWeekend(new PlaceHoursByDays()
                                .setOpening("opening-we")
                                .setClosing("closing-we")))
                .setPrice(new PlacePrice().setAdult("adult").setChild("child").setStudent("student"))
                .setDescription("desc")
                .setUrl("http://place-url.com");
        mDetailViewModel.getImgUrl().observeForever(value -> mActualImgUrl = value);
        mDetailViewModel.getHours().observeForever(strRes -> mActualHours = strRes);
        mDetailViewModel.getPrice().observeForever(stringResources -> mActualPrice = stringResources);
        mDetailViewModel.getDescription().observeForever(desc -> mActualDesc = desc);
        mDetailViewModel.getUrl().observeForever(url -> mActualUrl = url);

        mPlaceData.setValue(place);

        assertThat(mActualImgUrl, equalTo("http://img-url"));
        assertThat(mActualHours, equalTo(
                new StringResource(R.string.detail_hours,
                        "opening", "closing", "opening-we", "closing-we")));
        assertThat(mActualPrice, equalTo(Arrays.asList(
                new StringResource(R.string.detail_price_adult, "adult"),
                new StringResource(R.string.detail_price_student, "student"),
                new StringResource(R.string.detail_price_child, "child"))));
        assertThat(mActualDesc, equalTo("desc"));
        assertThat(mActualUrl, equalTo("http://place-url.com"));
    }
}