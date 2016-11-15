package me.cpele.inbop.detail;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.fragment.ItineraryFragment;
import me.cpele.inbop.detail.fragment.UsefulInfoFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_PLACE = "EXTRA_PLACE";

    @BindView(R.id.detail_tb)
    Toolbar toolbar;
    @BindView(R.id.detail_vp)
    ViewPager viewPager;
    @BindView(R.id.detail_tl)
    TabLayout tabLayout;

    private AppPreferences preferences;

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

        preferences = CustomApp.getInstance().getPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean creation = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, R.string.detail_star, Menu.NONE, R.string.detail_star);
        return creation;
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

            case R.string.detail_star:
                Toast.makeText(this, "TODO: Implement starring", Toast.LENGTH_SHORT).show();
                String uuid = "TODO";
                preferences.toggleStar(uuid);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
