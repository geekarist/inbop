package me.cpele.inbop.utils;

import android.support.annotation.NonNull;

import me.cpele.inbop.BuildConfig;

public class Asserting {

    @NonNull
    public static <T> T notNull(T object) {
        if (BuildConfig.DEBUG && object == null) throw new AssertionError();
        return object;
    }
}
