package me.cpele.inbop.detail.fragment.useful_info;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UsefulInfoPresenterTest {

    private UsefulInfoContract.Presenter mPresenter;

    private Place mPlace;
    private UsefulInfoContract.View mView;

    @Before
    void setUp() {
        mPresenter = new UsefulInfoPresenter();
        mView = Mockito.mock(UsefulInfoContract.View.class);
        mPresenter.onBind(mView);
        mPlace = new Place();
    }

    @After
    void tearDown() {
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
}