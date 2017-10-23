package me.cpele.inbop.list;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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
import me.cpele.inbop.utils.ObjectUtils;

class ListAdapter
        extends RecyclerView.Adapter<PlaceViewHolder>
        implements PlaceViewHolder.StarringListener {

    private List<Place> mPlaces = new ArrayList<>();
    private final AppPreferences mPreferences;

    public ListAdapter() {
        mPreferences = CustomApp.getInstance().getPreferences();
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

    @Override
    public void toggleStar(@NonNull String id) {

        starPlace(id);
        int positionBefore = getPosition(id);
        Collections.sort(this.mPlaces, new PlaceComparator());
        int positionAfter = getPosition(id);
        moveItem(positionBefore, positionAfter);
    }

    private void starPlace(@NonNull String id) {
        mPreferences.toggleStar(id);
    }

    private void moveItem(int positionBefore, int positionAfter) {
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

    public void refresh(@NonNull List<Place> newPlaces) {

        Collections.sort(newPlaces, new PlaceComparator());

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return mPlaces.size();
            }

            @Override
            public int getNewListSize() {
                return newPlaces.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                String oldPlaceId = mPlaces.get(oldItemPosition).getId();
                String newPlaceId = newPlaces.get(newItemPosition).getId();
                return ObjectUtils.equals(oldPlaceId, newPlaceId);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return ObjectUtils.equals(mPlaces.get(oldItemPosition), newPlaces.get(newItemPosition));
            }
        });

        mPlaces = newPlaces;

        diffResult.dispatchUpdatesTo(this);
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
