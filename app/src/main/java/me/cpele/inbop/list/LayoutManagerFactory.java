package me.cpele.inbop.list;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

class LayoutManagerFactory {

    public static LinearLayoutManager create(Context context) {

        int orientation = context.getResources().getConfiguration().orientation;
        boolean landscape = orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (landscape) return new GridLayoutManager(context, 2);
        else return new LinearLayoutManager(context);
    }
}
