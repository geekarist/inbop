package me.cpele.indoorboulderingparis.detail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.indoorboulderingparis.BuildConfig;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;

public class AddressFragment extends Fragment {

    private static final String ARG_PLACE = "ARG_PLACE";

    @BindView(R.id.fragment_tv_address)
    TextView addressTextView;
    @BindView(R.id.fragment_address_iv)
    ImageView imageView;

    public static Fragment newInstance(Place place) {

        AddressFragment fragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARG_PLACE", Parcels.wrap(place));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, view);

        Place place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));
        addressTextView.setText(place.getAddress());
        Glide.with(this).load(BuildConfig.PLACES_API_BASE_URL + place.getImgUrl()).centerCrop().into(imageView);
        return view;
    }
}
