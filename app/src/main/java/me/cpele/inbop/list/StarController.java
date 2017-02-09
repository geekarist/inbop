package me.cpele.inbop.list;

import android.support.annotation.NonNull;

public class StarController {

    private View mView;

    public StarController(View view) {

        mView = view;
    }

    interface View {

        void starPlace(@NonNull String id);

        void moveItem(int positionBefore, int positionAfter);
    }
}
