package me.cpele.inbop.list;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("ConstantConditions")
public class ListPresenterTest {

    private ListPresenter mListPresenter;
    private ListContract.View mView;

    @Before
    public void setUp() throws Exception {
        mListPresenter = new ListPresenter();
        mView = mock(ListContract.View.class);
        mListPresenter.attach(mView, null);
    }

    @Test
    public void shouldSetupLandscape() throws Exception {

        boolean landscape = true;

        mListPresenter.onCheckOrientation(landscape);

        verify(mView).onSetupForLandscape();
    }

    @Test
    public void shouldSetupPortrait() throws Exception {

        boolean landscape = false;

        mListPresenter.onCheckOrientation(landscape);

        verify(mView).onSetupForPortrait();
    }

}