package me.cpele.indoorboulderingparis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class PlacesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_places);

        RecyclerView placesRecyclerView = (RecyclerView) findViewById(R.id.places_rv);

        List<Place> places = Arrays.asList(
                new Place("Antrebloc", "Villejuif"),
                new Place("Arkose", "Montreuil"),
                new Place("Hardbloc", "Alfortville"));

        PlacesAdapter adapter = new PlacesAdapter(places);
        placesRecyclerView.setAdapter(adapter);
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class PlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

        private final List<Place> mPlaces;

        PlacesAdapter(List<Place> places) {

            mPlaces = places;
        }

        @Override
        public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(PlacesActivity.this).inflate(R.layout.layout_place_item, parent, false);

            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaceViewHolder holder, int position) {

            Place place = mPlaces.get(position);

            TextView nameTextView = (TextView) holder.itemView.findViewById(R.id.place_item_tv_name);
            nameTextView.setText(place.getName());

            TextView cityTextView = (TextView) holder.itemView.findViewById(R.id.place_item_tv_city);
            cityTextView.setText(place.getCity());
        }

        @Override
        public int getItemCount() {
            return mPlaces.size();
        }
    }

    private class PlaceViewHolder extends RecyclerView.ViewHolder {

        PlaceViewHolder(View itemView) {
            super(itemView);
        }
    }
}
