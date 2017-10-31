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

import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.utils.ObjectUtils;

class ListAdapter
        extends RecyclerView.Adapter<PlaceViewHolder>
        implements PlaceViewHolder.StarringListener {

    private final List<Place> mPlaces = new ArrayList<>();
    private Listener mListener;

    ListAdapter(Listener listener) {
        mListener = listener;
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
        mListener.toggleStar(id);
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

        mPlaces.clear();
        mPlaces.addAll(newPlaces);

        diffResult.dispatchUpdatesTo(this);
    }

    private class PlaceComparator implements Comparator<Place> {
        @Override
        public int compare(@NonNull Place p1, @NonNull Place p2) {
            boolean star1 = p1.isStarred();
            boolean star2 = p2.isStarred();
            if (star1 == star2) return 0;
            if (star1) return -1;
            return 1;
        }
    }

    interface Listener {
        void toggleStar(String id);
    }
}
