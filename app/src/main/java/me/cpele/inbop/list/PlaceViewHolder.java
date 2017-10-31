package me.cpele.inbop.list;

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
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.DetailActivity;

class PlaceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.place_tv_name)
    TextView nameTextView;
    @BindView(R.id.place_tv_city)
    TextView cityTextView;
    @BindView(R.id.place_iv_picture)
    ImageView imageView;
    @BindView(R.id.place_iv_favorite)
    ImageView favoriteView;

    private Place place;
    private final StarringListener starringListener;

    PlaceViewHolder(View itemView, StarringListener starringListener) {
        super(itemView);
        this.starringListener = starringListener;
        ButterKnife.bind(this, itemView);
    }

    void bind(Place place) {

        this.place = place;

        nameTextView.setText(place.getName());
        cityTextView.setText(place.getCity());

        String url = place.getImgUrl();
        Glide.with(itemView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);

        updateFavoriteImage(place);
    }

    private void updateFavoriteImage(Place place) {
        if (place.isStarred()) {
            favoriteView.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            favoriteView.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }

    @OnClick(R.id.place_cv)
    void onClickPlace() {
        Context context = itemView.getContext();
        Intent intent = DetailActivity.newIntent(context, place);
        context.startActivity(intent);
    }

    @OnClick(R.id.place_iv_favorite)
    void onClickFavorite() {
        starringListener.toggleStar(place.getId());
    }

    public interface StarringListener {
        void toggleStar(String id);
    }
}
