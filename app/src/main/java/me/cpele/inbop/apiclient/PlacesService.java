package me.cpele.inbop.apiclient;

import me.cpele.inbop.BuildConfig;
import me.cpele.inbop.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlacesService {

    @GET(BuildConfig.PLACES_API_PATH)
    Call<PlaceList> findAll();
}
