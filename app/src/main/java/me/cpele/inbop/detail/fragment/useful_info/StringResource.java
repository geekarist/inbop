package me.cpele.inbop.detail.fragment.useful_info;

import android.content.Context;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return "StringResource{" +
                "mResId=" + mResId +
                ", mArgs=" + Arrays.toString(mArgs) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringResource that = (StringResource) o;

        if (mResId != that.mResId) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(mArgs, that.mArgs);
    }

    @Override
    public int hashCode() {
        int result = mResId;
        result = 31 * result + Arrays.hashCode(mArgs);
        return result;
    }
}
