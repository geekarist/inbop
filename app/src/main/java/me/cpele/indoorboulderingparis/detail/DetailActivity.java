package me.cpele.indoorboulderingparis.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;
import me.cpele.indoorboulderingparis.detail.fragment.ItineraryFragment;
import me.cpele.indoorboulderingparis.detail.fragment.UsefulInfoFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_PLACE = "EXTRA_PLACE";

    @BindView(R.id.detail_tb)
    Toolbar toolbar;
    @BindView(R.id.detail_vp)
    ViewPager viewPager;
    @BindView(R.id.detail_tl)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Parcelable extra = getIntent().getParcelableExtra(EXTRA_PLACE);
        Place place = Parcels.unwrap(extra);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(place.getName());

        DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager());
        detailPagerAdapter.add(UsefulInfoFragment.newInstance(this, place));
        detailPagerAdapter.add(ItineraryFragment.newInstance(this, place));
        viewPager.setAdapter(detailPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    public static Intent newIntent(Context context, Place place) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_PLACE, Parcels.wrap(place));
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
