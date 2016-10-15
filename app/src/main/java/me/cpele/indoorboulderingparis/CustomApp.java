package me.cpele.indoorboulderingparis;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
