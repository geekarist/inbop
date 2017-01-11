package me.cpele.inbop.detail.fragment.useful_info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.fragment.DetailFragment;

public class UsefulInfoFragment extends DetailFragment implements UsefulInfoContract.View {

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

    private Place place;
    private UsefulInfoContract.Presenter mPresenter;

    public static UsefulInfoFragment newInstance(Context context, Place place) {

        UsefulInfoFragment fragment = new UsefulInfoFragment();
        fragment.setup(context, place);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_useful_info, container, false);
        ButterKnife.bind(this, view);

        place = Parcels.unwrap(getArguments().getParcelable(ARG_PLACE));

        mPresenter.loadPlace(place);

        return view;
    }

    @OnClick(R.id.useful_bt_facebook)
    void onClickFacebook() {
        Log.i(TAG, "Clicked on facebook: " + place);
        mPresenter.openFacebookForPlace(place);
    }

    @Override
    public String getTitle() {
        return getContext().getString(R.string.detail_title_useful);
    }

    // region Presentation

    @Override
    public void onBind(UsefulInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onUnbind() {
        mPresenter = null;
    }

    @Override
    public void displayEmail(String email) {
        emailTextView.setText(email);
        emailTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public String buildString(int msgId, Object... args) {
        return getString(msgId, args);
    }

    @Override
    public void displayFacebook(String fbPage) {
        facebookButton.setText(fbPage);
        facebookButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayUrl(String url) {
        urlTextView.setText(url);
        urlTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayDescription(String description) {
        specificityTextView.setText(description);
        specificityTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayPrices(CharSequence prices) {
        pricesTextView.setText(prices);
        pricesTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayHours(String hours) {
        hoursTextView.setText(hours);
        hoursTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayImage(String imgUrl) {
        Glide.with(this).load(imgUrl).centerCrop().into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void informNoFacebookForPlace() {
        Log.i(TAG, "No facebook url is defined for place");
    }

    @Override
    public void startFacebookForUrl(String url) {
        Uri uri = Uri.parse(url);
        Log.d(TAG, String.valueOf(uri));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    // endregion
}
