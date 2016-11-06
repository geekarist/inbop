package me.cpele.inbop.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;

class ListAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private List<Place> places = new ArrayList<>();

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        Place place = places.get(position);
        holder.bind(place);
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
