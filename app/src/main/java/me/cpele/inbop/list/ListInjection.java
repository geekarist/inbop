package me.cpele.inbop.list;

public class ListInjection {

    public static ListContract.Presenter providePresenter() {
        return new ListPresenter();
    }

    public static ListContract.Model provideModel() {
        return new ListModel();
    }
}
