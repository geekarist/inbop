package me.cpele.inbop.detail.fragment.useful_info;

public class UsefulInfoPresenter implements UsefulInfoContract.Presenter {

    private UsefulInfoContract.View mView;

    @Override
    public void onBind(UsefulInfoContract.View view) {
        mView = view;
    }

    @Override
    public void onUnbind() {
        mView = null;
    }
}
