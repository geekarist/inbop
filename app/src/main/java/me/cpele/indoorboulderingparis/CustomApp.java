package me.cpele.indoorboulderingparis;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomApp extends Application {

    private static CustomApp instance;

    private PlacesService placesService;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placesService = retrofit.create(PlacesService.class);
    }

    public static CustomApp getInstance() {
        return instance;
    }

    public PlacesService getPlacesService() {
        return placesService;
    }
}
