package me.cpele.inbop.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class AppPreferences {

    public static final String NAME_STARS = "STARS";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public AppPreferences(Context context, Gson gson) {
        sharedPreferences = context.getSharedPreferences(context.getClass().getSimpleName(), Context.MODE_PRIVATE);
        this.gson = gson;
    }

    public void toggleStar(String uuid) {

        Map<String, Boolean> starsMap = loadStars();

        Boolean starred = starsMap.get(uuid);
        starsMap.put(uuid, !Boolean.TRUE.equals(starred));

        saveStars(starsMap);
    }

    private void saveStars(Map<String, Boolean> starsMap) {
        String starsJson = gson.toJson(starsMap);
        sharedPreferences.edit().putString(NAME_STARS, starsJson).apply();
    }

    private @NonNull Map<String, Boolean> loadStars() {
        String starsJson = sharedPreferences.getString(NAME_STARS, "{}");
        Type type = new TypeToken<Map<String, Boolean>>() {
        }.getType();
        return gson.fromJson(starsJson, type);
    }

    public boolean isStarred(@NonNull String id) {
        Map<String, Boolean> stars = loadStars();
        if (stars != null) return stars.get(id);
        return false;
    }
}
