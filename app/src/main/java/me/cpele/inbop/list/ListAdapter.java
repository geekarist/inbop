package me.cpele.inbop.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.AppPreferences;

class ListAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private List<Place> places = new ArrayList<>();
    private final AppPreferences preferences;

    public ListAdapter() {
        preferences = CustomApp.getInstance().getPreferences();
    }

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
        // TODO: Make Place implement comparable to sort by name

        Comparator<Place> comparator = new Comparator<Place>() {
            @Override
            public int compare(@NonNull Place p1, @NonNull Place p2) {
                boolean star1 = preferences.isStarred(p1.getId());
                boolean star2 = preferences.isStarred(p2.getId());
                if (star1 == star2) return 0;
                if (star1) return -1;
                return 1;
            }
        };

        Collections.sort(this.places, comparator);

        notifyDataSetChanged();
    }
}
