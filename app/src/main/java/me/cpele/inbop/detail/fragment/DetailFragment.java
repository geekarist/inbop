package me.cpele.inbop.detail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import me.cpele.inbop.apiclient.model.Place;

public abstract class DetailFragment extends Fragment {

    private Context context;

    public abstract String getTitle();

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setup(Context context, Place place) {

        setContext(context);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARG_PLACE", Parcels.wrap(place));
        setArguments(bundle);
    }
}
