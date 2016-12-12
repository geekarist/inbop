package me.cpele.inbop;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.detail.AppPreferences;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomApp extends MultiDexApplication {

    private static CustomApp instance;

    private PlacesService placesService;
    private AppPreferences preferences;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;
        }

        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        placesService = retrofit.create(PlacesService.class);

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }

        preferences = new AppPreferences(this, gson);
    }

    public static CustomApp getInstance() {
        return instance;
    }

    public PlacesService getPlacesService() {
        return placesService;
    }

    public AppPreferences getPreferences() {
        return preferences;
    }
}
