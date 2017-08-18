package me.cpele.inbop.detail.fragment.useful_info;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.apiclient.model.PlaceHours;
import me.cpele.inbop.apiclient.model.PlaceHoursByDays;
import me.cpele.inbop.apiclient.model.PlacePrice;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class UsefulInfoPresenterTest {

    private UsefulInfoContract.Presenter mPresenter;

    private Place mPlace;
    private UsefulInfoContract.View mView;

    @Before
    public void setUp() {
        mPresenter = new UsefulInfoPresenter();
        mView = Mockito.mock(UsefulInfoContract.View.class);
        mPresenter.onBind(mView);
        mPlace = new Place();
    }

    @After
    public void tearDown() {
        mPresenter.onUnbind();
    }

    @Test
    public void shouldOpenFacebookForUrl() throws Exception {

        mPlace.setFacebook("fb_id");
        given(mView.buildString(eq(R.string.detail_facebook_url), anyString())).willReturn("http://full_url");

        mPresenter.openFacebookForPlace(mPlace);

        verify(mView).buildString(anyInt(), eq("fb_id"));
        verify(mView).startFacebookForUrl("http://full_url");
    }

    @Test
    public void shouldInformNoFacebookForPlace() throws Exception {

        mPlace.setFacebook(null);

        mPresenter.openFacebookForPlace(mPlace);

        verify(mView).informNoFacebookForPlace();
    }

    @Test
    public void shouldDisplayPlace() {

        mPlace.setDescription("desc")
                .setEmail("email")
                .setFacebook("facebook")
                .setHours(new PlaceHours()
                        .setWeekdays(new PlaceHoursByDays().setOpening("week-opening").setClosing("week-closing"))
                        .setWeekend(new PlaceHoursByDays().setOpening("wknd-opening").setClosing("wknd-closing")))
                .setImgUrl("img-url")
                .setPrice(new PlacePrice()
                        .setAdult("adult")
                        .setChild("child")
                        .setStudent("student"))
                .setUrl("web-url");
        given(mView.buildString(eq(R.string.detail_hours), anyString(), anyString(), anyString(), anyString())).willReturn("hours");
        given(mView.buildString(eq(R.string.detail_price_adult), anyString())).willReturn("adult 3 €");
        given(mView.buildString(eq(R.string.detail_price_student), anyString())).willReturn("student 2 €");
        given(mView.buildString(eq(R.string.detail_price_child), anyString())).willReturn("child 1 €");

        mPresenter.loadPlace(mPlace);

        verify(mView).displayDescription("desc");
        verify(mView).displayEmail("email");
        verify(mView).displayFacebook("facebook");
        verify(mView).displayHours("hours");
        verify(mView).displayImage("img-url");
        verify(mView).displayPrices("adult 3 € - student 2 € - child 1 €");
        verify(mView).displayUrl("web-url");
    }

    @Test
    public void shouldNotDisplayEmptyPlace() {

        mPresenter.loadPlace(mPlace);

        verifyZeroInteractions(mView);
    }
}