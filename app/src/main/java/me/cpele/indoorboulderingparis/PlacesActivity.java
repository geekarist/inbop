package me.cpele.indoorboulderingparis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class PlacesActivity extends Activity {

    private static final String TAG = PlacesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_places);

        RecyclerView placesRecyclerView = (RecyclerView) findViewById(R.id.places_rv);

        final PlacesAdapter adapter = new PlacesAdapter();
        placesRecyclerView.setAdapter(adapter);
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlacesService placesService = retrofit.create(PlacesService.class);

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

    private class PlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

        private List<Place> places = new ArrayList<>();

        @Override
        public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(PlacesActivity.this).inflate(R.layout.layout_place_item, parent, false);

            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaceViewHolder holder, int position) {

            Place place = places.get(position);

            TextView nameTextView = (TextView) holder.itemView.findViewById(R.id.place_item_tv_name);
            nameTextView.setText(place.getName());

            TextView cityTextView = (TextView) holder.itemView.findViewById(R.id.place_item_tv_city);
            cityTextView.setText(place.getCity());
        }

        @Override
        public int getItemCount() {
            return places.size();
        }

        void addAll(List<Place> places) {
            this.places.addAll(places);
            notifyDataSetChanged();
        }
    }

    private class PlaceViewHolder extends RecyclerView.ViewHolder {

        PlaceViewHolder(View itemView) {
            super(itemView);
        }
    }

    private interface PlacesService {

        @GET(BuildConfig.PLACES_API_PATH)
        Call<PlaceList> findAll();
    }
}
