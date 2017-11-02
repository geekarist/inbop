package me.cpele.inbop.detail;

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
import me.cpele.inbop.detail.fragment.itinerary.ItineraryContract;
import me.cpele.inbop.detail.fragment.itinerary.ItineraryFragment;
import me.cpele.inbop.detail.fragment.itinerary.ItineraryPresenter;
import me.cpele.inbop.detail.fragment.useful_info.UsefulInfoFragment;
import me.cpele.inbop.detail.fragment.useful_info.UsefulInfoPresenter;
import me.cpele.inbop.list.PlacesRepository;

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
    private ItineraryContract.Presenter mItineraryPresenter;

    private UsefulInfoFragment mUsefulInfoFragment;
    private UsefulInfoPresenter mUsefulInfoPresenter;
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


    @Override
    protected void onDestroy() {
        tearDownItinerary();
        tearDownUsefulInfo();
        super.onDestroy();
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
        mItineraryPresenter = new ItineraryPresenter();

        mItineraryFragment.onBind(mItineraryPresenter);
        mItineraryPresenter.onBind(mItineraryFragment);

        detailPagerAdapter.add(mItineraryFragment);
    }

    private ItineraryFragment findOrCreateItineraryFragment(Place place) {

        return mItineraryFragment != null ? mItineraryFragment : ItineraryFragment.newInstance(this, place);
    }

    private void tearDownItinerary() {
        mItineraryFragment.onUnbind();
        mItineraryPresenter.onUnbind();
    }

    private void setupUsefulInfo(Place place, DetailPagerAdapter detailPagerAdapter) {

        mUsefulInfoFragment = findOrCreateUsefulInfoFragment(place);
        mUsefulInfoPresenter = new UsefulInfoPresenter();

        mUsefulInfoFragment.onBind(mUsefulInfoPresenter);
        mUsefulInfoPresenter.onBind(mUsefulInfoFragment);

        detailPagerAdapter.add(mUsefulInfoFragment);
    }

    private UsefulInfoFragment findOrCreateUsefulInfoFragment(Place place) {

        return mUsefulInfoFragment != null ? mUsefulInfoFragment : UsefulInfoFragment.newInstance(this, place);
    }

    private void tearDownUsefulInfo() {
        mUsefulInfoFragment.onUnbind();
        mUsefulInfoPresenter.onUnbind();
    }

    @NonNull
    private Place getPlace() {
        String placeId = getIntent().getStringExtra(EXTRA_PLACE);
        return mRepository.findPlaceById(placeId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_options, menu);
        MenuItem starItem = menu.findItem(R.id.detail_star);
        updateStarMenuItem(starItem);
        return true;
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

                mRepository.toggleStar(getPlace().getId());
                indicateStarringChange();
                updateStarMenuItem(item);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void indicateStarringChange() {

        Place place = getPlace();

        int indicationId;
        if (place.isStarred()) indicationId = R.string.detail_star_indication;
        else indicationId = R.string.detail_unstar_indication;

        String indication = getString(indicationId, getPlace().getName());

        Toast.makeText(this, indication, Toast.LENGTH_SHORT).show();
    }

    private void updateStarMenuItem(MenuItem item) {

        int starLabel;
        int starIcon;

        if (getPlace().isStarred()) {
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
