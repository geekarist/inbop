package me.cpele.inbop.detail.fragment.useful_info;

import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import me.cpele.inbop.apiclient.model.Place;

import static org.mockito.ArgumentMatchers.any;
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
        mPlace = new Place().setFacebook("http://facebookurl");

        mPresenter.openFacebookForPlace(mPlace);

        verify(mView).startFacebookForUri(any(Uri.class));
    }
}