package me.cpele.inbop.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.cpele.inbop.detail.fragment.DetailFragment;

class DetailPagerAdapter extends FragmentPagerAdapter {

    private List<DetailFragment> fragmentList = new ArrayList<>();

    DetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    void add(DetailFragment fragment) {
        fragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getTitle();
    }
}
