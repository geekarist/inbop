package me.cpele.indoorboulderingparis.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.cpele.indoorboulderingparis.CustomApp;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.PlaceList;
import me.cpele.indoorboulderingparis.apiclient.PlacesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesActivity extends AppCompatActivity {

    private static final String TAG = PlacesActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_places);

        Toolbar toolbar = (Toolbar) findViewById(R.id.places_tb);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.places_rv);

        reload();

        adapter = new PlacesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        View reloadButton = findViewById(R.id.places_bt_reload);

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
    }

    private void reload() {

        final View loadingLayout = findViewById(R.id.places_ll_loading);
        final View errorLoadingLayout = findViewById(R.id.places_ll_error_loading);

        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorLoadingLayout.setVisibility(View.INVISIBLE);

        PlacesService placesService = CustomApp.getInstance().getPlacesService();

        placesService.findAll().enqueue(new Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                adapter.addAll(response.body().getPlaces());
                loadingLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                errorLoadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                errorLoadingLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
