package me.cpele.inbop.list;

import me.cpele.inbop.CustomApp;

public class ListInjection {

    public static ListContract.Presenter providePresenter() {
        return new ListPresenter();
    }

    public static ListContract.Model provideModel() {
        return new ListModel(CustomApp.getInstance().getPlacesService());
    }
}
