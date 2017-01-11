package me.cpele.inbop.detail.fragment.useful_info;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UsefulInfoPresenterTest {

    private UsefulInfoContract.Presenter mPresenter;
    private Place mPlace;
    private UsefulInfoContract.View mView;

    @Test
    public void shouldOpenFacebookForPlace() throws Exception {

        mPresenter = new UsefulInfoPresenter();
        mView = Mockito.mock(UsefulInfoContract.View.class);
        mPresenter.onBind(mView);
        mPlace = new Place().setFacebook("fb_id");
        given(mView.buildString(eq(R.string.detail_facebook_url), anyString())).willReturn("http://full_url");

        mPresenter.openFacebookForPlace(mPlace);

        verify(mView).startFacebookForUrl("http://full_url");
    }
}