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

class ListAdapter
        extends RecyclerView.Adapter<PlaceViewHolder>
        implements PlaceViewHolder.StarringListener, StarController.View {

    private final StarController mStarController;
    private List<Place> mPlaces = new ArrayList<>();
    private final AppPreferences mPreferences;

    public ListAdapter() {
        mPreferences = CustomApp.getInstance().getPreferences();
        mStarController = new StarController(this);
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);

        return new PlaceViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        Place place = mPlaces.get(position);
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    void addAll(List<Place> places) {
        this.mPlaces.addAll(places);
        // TODO: Make Place implement comparable to sort by name

        Collections.sort(this.mPlaces, new PlaceComparator());
        notifyDataSetChanged();
    }

    @Override
    public void toggleStar(@NonNull String id) {

        starPlace(id);
        int positionBefore = getPosition(id);
        Collections.sort(this.mPlaces, new PlaceComparator());
        int positionAfter = getPosition(id);
        moveItem(positionBefore, positionAfter);
    }

    @Override
    public void starPlace(@NonNull String id) {
        mPreferences.toggleStar(id);
    }

    @Override
    public void moveItem(int positionBefore, int positionAfter) {
        notifyItemMoved(positionBefore, positionAfter);
    }

    private int getPosition(@NonNull String searchId) {

        for (int i = 0; i < mPlaces.size(); i++) {
            Place place = mPlaces.get(i);
            String otherId = place.getId();
            if (searchId.equals(otherId)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean isStarred(@NonNull String id) {
        return mPreferences.isStarred(id);
    }

    public void clear() {
        mPlaces.clear();
    }

    private class PlaceComparator implements Comparator<Place> {
        @Override
        public int compare(@NonNull Place p1, @NonNull Place p2) {
            boolean star1 = mPreferences.isStarred(p1.getId());
            boolean star2 = mPreferences.isStarred(p2.getId());
            if (star1 == star2) return 0;
            if (star1) return -1;
            return 1;
        }
    }
}
