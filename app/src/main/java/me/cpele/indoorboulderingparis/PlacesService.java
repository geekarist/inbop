package me.cpele.indoorboulderingparis;

import retrofit2.Call;
import retrofit2.http.GET;

interface PlacesService {

    @GET(BuildConfig.PLACES_API_PATH)
    Call<PlaceList> findAll();
}
