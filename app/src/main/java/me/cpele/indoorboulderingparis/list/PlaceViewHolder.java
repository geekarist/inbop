package me.cpele.indoorboulderingparis.list;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.cpele.indoorboulderingparis.BuildConfig;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.Place;
import me.cpele.indoorboulderingparis.detail.DetailActivity;

class PlaceViewHolder extends RecyclerView.ViewHolder {

    PlaceViewHolder(View itemView) {
        super(itemView);
    }

    void bind(Place place) {

        TextView nameTextView = (TextView) itemView.findViewById(R.id.place_item_tv_name);
        nameTextView.setText(place.getName());

        TextView cityTextView = (TextView) itemView.findViewById(R.id.place_item_tv_city);
        cityTextView.setText(place.getCity());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.place_item_iv_picture);
        String url = BuildConfig.PLACES_API_BASE_URL + place.getImgUrl();
        Glide.with(itemView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
