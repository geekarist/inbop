package me.cpele.inbop.detail.fragment.useful_info;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.TextualUtils;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.DetailViewModel;
import me.cpele.inbop.detail.fragment.DetailFragment;
import me.cpele.inbop.list.PlacesRepository;

public class UsefulInfoFragment extends DetailFragment {

    private static final String ARG_PLACE = "ARG_PLACE";
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.useful_iv)
    ImageView imageView;
    @BindView(R.id.useful_tv_hours)
    TextView hoursTextView;
    @BindView(R.id.useful_tv_prices)
    TextView pricesTextView;
    @BindView(R.id.useful_tv_specificity)
    TextView specificityTextView;
    @BindView(R.id.useful_tv_web)
    TextView urlTextView;
    @BindView(R.id.useful_bt_facebook)
    Button facebookButton;
    @BindView(R.id.useful_tv_email)
    TextView emailTextView;
    private DetailViewModel mDetailViewModel;

    public static UsefulInfoFragment newInstance(Context context, Place place) {

        UsefulInfoFragment fragment = new UsefulInfoFragment();
        fragment.setup(context, place);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_useful_info, container, false);
        ButterKnife.bind(this, view);

        PlacesRepository repository = CustomApp.getInstance().getPlacesRepository();
        String placeId = getArguments().getString(ARG_PLACE);
        DetailViewModel.Factory factory = new DetailViewModel.Factory(repository, placeId);

        mDetailViewModel = ViewModelProviders
                .of(getActivity(), factory)
                .get(DetailViewModel.class);

        mDetailViewModel.getImgUrl().observe(getActivity(), url -> {
            Glide.with(this).load(url).centerCrop().into(imageView);
            imageView.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getHours().observe(getActivity(), (@NonNull StringResource stringRes) -> {
            hoursTextView.setText(stringRes.asString(getContext()));
            hoursTextView.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getPrice().observe(getActivity(), (@NonNull List<StringResource> stringResourceList) -> {
            List<String> strPrices = new ArrayList<>();
            for (StringResource stringResource : stringResourceList) {
                String priceStr = stringResource.asString(getContext());
                strPrices.add(priceStr);
            }
            String joinedPrices = TextualUtils.join(" - ", strPrices);
            pricesTextView.setText(joinedPrices);
            pricesTextView.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getDescription().observe(getActivity(), (String strDesc) -> {
            specificityTextView.setText(strDesc);
            specificityTextView.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getUrl().observe(getActivity(), url -> {
            urlTextView.setText(url);
            urlTextView.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getFacebook().observe(getActivity(), (@NonNull StringResource stringResource) -> {
            facebookButton.setText(stringResource.asString(getContext()));
            facebookButton.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getEmail().observe(getActivity(), email -> {
            emailTextView.setText(email);
            emailTextView.setVisibility(View.VISIBLE);
        });

        mDetailViewModel.getFacebookClickEvent().observe(getActivity(),
                (@NonNull StringResource stringResource) -> {
                    String url = getString(
                            R.string.detail_facebook_url,
                            stringResource.asString(getContext()));
                    startFacebookForUrl(url);
                });

        return view;
    }

    @OnClick(R.id.useful_bt_facebook)
    void onClickFacebook() {
        mDetailViewModel.triggerFacebookClick();
    }

    public String getTitle() {
        return getContext().getString(R.string.detail_title_useful);
    }

    public void startFacebookForUrl(String url) {
        Uri uri = Uri.parse(url);
        Log.d(TAG, String.valueOf(uri));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
