package me.cpele.inbop.detail.fragment.useful_info;

import org.junit.Test;

import me.cpele.inbop.apiclient.model.Place;

public class UsefulInfoPresenterTest {

    private UsefulInfoContract.Presenter mPresenter;
    private Place mPlace;

    @Test
    public void shouldOpenFacebookForPlace() throws Exception {

        mPresenter = new UsefulInfoPresenter();

        mPresenter.openFacebookForPlace(mPlace);

    }

    @Test
    public void shouldLoadPlace() throws Exception {

    }

}