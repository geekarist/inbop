package me.cpele.indoorboulderingparis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesActivity extends AppCompatActivity {

    private static final String TAG = PlacesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_places);

        Toolbar toolbar = (Toolbar) findViewById(R.id.places_tb);
        setSupportActionBar(toolbar);

        RecyclerView placesRecyclerView = (RecyclerView) findViewById(R.id.places_rv);

        final PlacesAdapter adapter = new PlacesAdapter();
        placesRecyclerView.setAdapter(adapter);
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PlacesService placesService = CustomApp.getInstance().getPlacesService();

        placesService.findAll().enqueue(new Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                adapter.addAll(response.body().getPlaces());
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                Toast.makeText(PlacesActivity.this, "Error getting places", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error getting places", t);
            }
        });
    }

}
