package me.cpele.inbop;

import android.arch.persistence.room.Room;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.db.AppDatabase;
import me.cpele.inbop.list.PlacesRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomApp extends MultiDexApplication {

    private static CustomApp sInstance;

    private PlacesRepository mPlacesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        if (sInstance == null) {
            sInstance = this;
        }

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PlacesService placesService = retrofit.create(PlacesService.class);

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }

        ExecutorService diskExecutor = Executors.newSingleThreadExecutor();

        AppDatabase database = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        mPlacesRepository = new PlacesRepository(placesService, database.getPlacesDao(), diskExecutor);
    }

    @NonNull
    public static CustomApp getInstance() {
        return sInstance;
    }

    @NonNull
    public PlacesRepository getPlacesRepository() {
        return mPlacesRepository;
    }
}
