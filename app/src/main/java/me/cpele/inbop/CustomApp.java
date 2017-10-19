package me.cpele.inbop;

import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.detail.AppPreferences;
import me.cpele.inbop.list.ListModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomApp extends MultiDexApplication {

    private static CustomApp sInstance;

    private AppPreferences mPreferences;
    private ListModel mListModel;

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

        mPreferences = new AppPreferences(this, gson);

        mListModel = new ListModel(placesService);
    }

    @NonNull
    public static CustomApp getInstance() {
        return sInstance;
    }

    @NonNull
    public AppPreferences getPreferences() {
        return mPreferences;
    }

    @NonNull
    public ListModel getListModel() {
        return mListModel;
    }
}
