package me.cpele.indoorboulderingparis.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.indoorboulderingparis.BuildConfig;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.detail.DetailActivity;

class PlaceViewHolder extends RecyclerView.ViewHolder {

    private Place place;

    @BindView(R.id.place_item_tv_name)
    TextView nameTextView;
    @BindView(R.id.place_item_tv_city)
    TextView cityTextView;
    @BindView(R.id.place_item_iv_picture)
    ImageView imageView;

    PlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Place place) {

        this.place = place;

        nameTextView.setText(place.getName());
        cityTextView.setText(place.getCity());

        String url = BuildConfig.PLACES_API_BASE_URL + place.getImgUrl();
        Glide.with(itemView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    @OnClick(R.id.place_item_cv)
    void onClick() {

        Context context = itemView.getContext();
        Intent intent = DetailActivity.newIntent(context, place);
        context.startActivity(intent);
    }
}
