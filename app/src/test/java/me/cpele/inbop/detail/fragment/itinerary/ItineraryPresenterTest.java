package me.cpele.inbop.detail.fragment.itinerary;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlacePosition;

import static org.mockito.Mockito.verify;

public class ItineraryPresenterTest {

    private ItineraryPresenter mPresenter;

    private Place mPlace;
    private ItineraryContract.View mView;

    @Before
    public void setUp() throws Exception {

        mPresenter = new ItineraryPresenter();
        mPlace = new Place();
        mView = Mockito.mock(ItineraryContract.View.class);
        mPresenter.onBind(mView);
    }

    @Test
    public void shouldGetMap() throws Exception {

        mPlace.setPosition(new PlacePosition().setLon(2.331433).setLat(48.791855));

        mPresenter.onCheckPosition(mPlace);

        verify(mView).onGetMap();
    }

    @Test
    public void shouldHideMap() throws Exception {

        mPlace.setPosition(new PlacePosition().setLon(null).setLat(null));

        mPresenter.onCheckPosition(mPlace);

        verify(mView).onHideMap();
    }
}