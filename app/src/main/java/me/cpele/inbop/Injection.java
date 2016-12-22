package me.cpele.inbop;

import me.cpele.inbop.list.ListContract;
import me.cpele.inbop.list.ListPresenter;

public class Injection {

    public static class ListModule {

        public static ListContract.Presenter providePresenter() {
            return new ListPresenter();
        }
    }
}
