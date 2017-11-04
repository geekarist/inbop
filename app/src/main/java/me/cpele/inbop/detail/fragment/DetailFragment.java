package me.cpele.inbop.detail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import me.cpele.inbop.apiclient.model.Place;

public abstract class DetailFragment extends Fragment {

    protected static final String ARG_PLACE = "ARG_PLACE";
    private Context context;

    public abstract String getTitle();

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setup(Context context, @NonNull Place place) {

        setContext(context);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PLACE, place.getId());
        setArguments(bundle);
    }
}
