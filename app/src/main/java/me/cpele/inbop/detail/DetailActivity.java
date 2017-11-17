package me.cpele.inbop.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;
import me.cpele.inbop.detail.fragment.itinerary.ItineraryFragment;
import me.cpele.inbop.detail.fragment.useful_info.StringResource;
import me.cpele.inbop.detail.fragment.useful_info.UsefulInfoFragment;
import me.cpele.inbop.list.PlacesRepository;

import static me.cpele.inbop.utils.Asserting.notNull;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_PLACE = "EXTRA_PLACE";
    public static final String KEY_FRAGMENT_TAGS = "fragmentTags";

    @BindView(R.id.detail_tb)
    Toolbar toolbar;
    @BindView(R.id.detail_vp)
    ViewPager viewPager;
    @BindView(R.id.detail_tl)
    TabLayout tabLayout;

    private ItineraryFragment mItineraryFragment;

    private UsefulInfoFragment mUsefulInfoFragment;
    private PlacesRepository mRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restoreFragments(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mRepository = CustomApp.getInstance().getPlacesRepository();

        Place place = getPlace();

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(place.getName());

        DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager());
        setupUsefulInfo(place, detailPagerAdapter);
        setupItinerary(place, detailPagerAdapter);
        viewPager.setAdapter(detailPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void restoreFragments(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            ArrayList<String> fragmentTags = savedInstanceState.getStringArrayList(KEY_FRAGMENT_TAGS);

            if (fragmentTags != null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                for (String tag : fragmentTags) {
                    Fragment fragment = fragmentManager.findFragmentByTag(tag);
                    if (fragment instanceof UsefulInfoFragment) {
                        mUsefulInfoFragment = (UsefulInfoFragment) fragment;
                        mUsefulInfoFragment.setContext(this);
                    } else if (fragment instanceof ItineraryFragment) {
                        mItineraryFragment = (ItineraryFragment) fragment;
                        mItineraryFragment.setContext(this);
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        List<String> tagsList = Arrays.asList(mUsefulInfoFragment.getTag(), mItineraryFragment.getTag());
        ArrayList<String> tagsArrayList = new ArrayList<>(tagsList);
        outState.putStringArrayList(KEY_FRAGMENT_TAGS, tagsArrayList);

        super.onSaveInstanceState(outState);
    }

    private void setupItinerary(Place place, DetailPagerAdapter detailPagerAdapter) {

        mItineraryFragment = findOrCreateItineraryFragment(place);

        detailPagerAdapter.add(mItineraryFragment);
    }

    private ItineraryFragment findOrCreateItineraryFragment(Place place) {

        return mItineraryFragment != null ? mItineraryFragment : ItineraryFragment.newInstance(this, place);
    }

    private void setupUsefulInfo(Place place, DetailPagerAdapter detailPagerAdapter) {

        mUsefulInfoFragment = findOrCreateUsefulInfoFragment(place);
        detailPagerAdapter.add(mUsefulInfoFragment);
    }

    private UsefulInfoFragment findOrCreateUsefulInfoFragment(Place place) {

        return mUsefulInfoFragment != null ? mUsefulInfoFragment : UsefulInfoFragment.newInstance(this, place);
    }

    @NonNull
    private Place getPlace() {
        String placeId = getIntent().getStringExtra(EXTRA_PLACE);
        Place placeById = notNull(mRepository.findPlaceByIdSync(placeId));
        return notNull(placeById);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_options, menu);
        MenuItem starItem = menu.findItem(R.id.detail_star);

        createOrGetViewModel().isStarred().observe(this,
                (@NonNull Boolean starred) -> updateStarMenuItem(starItem, starred));

        createOrGetViewModel()
                .getStarIndicationEvent()
                .observe(this, (@NonNull StringResource starIndication) -> {
                    DetailActivity context = DetailActivity.this;
                    String strIndication = starIndication.asString(context);
                    Toast.makeText(context, strIndication, Toast.LENGTH_SHORT).show();
                });
        return true;
    }

    private DetailViewModel createOrGetViewModel() {
        String placeId = getIntent().getStringExtra(EXTRA_PLACE);
        DetailViewModel.Factory factory = new DetailViewModel.Factory(mRepository, placeId);
        return ViewModelProviders.of(this, factory).get(DetailViewModel.class);
    }

    public static Intent newIntent(Context context, Place place) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_PLACE, place.getId());
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.detail_star:
                createOrGetViewModel().toggleStar(getPlace().getId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static void updateStarMenuItem(MenuItem item, boolean starred) {

        int starLabel;
        int starIcon;

        if (starred) {
            starLabel = R.string.detail_unstar;
            starIcon = R.drawable.ic_favorite_white_24dp;
        } else {
            starLabel = R.string.detail_star;
            starIcon = R.drawable.ic_favorite_border_white_24dp;
        }

        item.setTitle(starLabel);
        item.setIcon(starIcon);
    }
}
