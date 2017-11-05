package me.cpele.inbop.detail.fragment.useful_info;

import android.content.Context;

public class StringResource {

    private final int mResId;
    private final Object[] mArgs;

    public StringResource(int resId, Object... args) {
        mResId = resId;
        mArgs = args;
    }

    public StringResource(String value) {
        this(-1, value);
    }

    public String asString(Context context) {
        if (mResId != -1) return context.getString(mResId, mArgs);
        else if (mArgs != null && mArgs.length > 0) return String.valueOf(mArgs[0]);
        else throw new AssertionError();
    }
}
