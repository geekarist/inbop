package me.cpele.indoorboulderingparis;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class PlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private List<Place> places = new ArrayList<>();

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place_item, parent, false);

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
