package me.cpele.inbop.detail.fragment.useful_info;

public interface UsefulInfoContract {

    interface Presenter {

        void onBind(View view);

        void onUnbind();
    }

    interface View {

        void onBind(Presenter presenter);

        void onUnbind();
    }
}
