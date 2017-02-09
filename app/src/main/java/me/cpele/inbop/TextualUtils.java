package me.cpele.inbop;

import android.support.annotation.Nullable;

/**
 * This class provides functions to manipulate Strings.
 */
public class TextualUtils {

    /**
     * Check if a String is empty.
     * Imported from {@link android.text.TextUtils#isEmpty(CharSequence)}
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * Join Strings in an Iterable structure.
     * Imported from {@link android.text.TextUtils#join(CharSequence, Iterable)}
     */
    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token: tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }
}
