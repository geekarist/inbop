package me.cpele.indoorboulderingparis.apiclient;

import me.cpele.indoorboulderingparis.BuildConfig;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlacesService {

    @GET(BuildConfig.PLACES_API_PATH)
    Call<PlaceList> findAll();
}
