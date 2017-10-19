package me.cpele.inbop.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

class ListPresenterFactory implements ViewModelProvider.Factory {

    private ListModel mModel;

    public ListPresenterFactory(ListModel model) {
        mModel = model;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return modelClass.cast(new ListPresenter(mModel));
    }
}
