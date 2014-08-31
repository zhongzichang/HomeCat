package com.osgsquare.homecat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.osgsquare.homecat.fragment.GroupsFragment;
import com.osgsquare.homecat.fragment.MoviesFragment;
import com.osgsquare.homecat.fragment.TopRatedFragment;

/**
 * Created by zzc on 8/31/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Games fragment activity
                return new GroupsFragment();
            case 1:
                // Top Rated fragment activity
                return new TopRatedFragment();
            case 2:
                // Movies fragment activity
                return new MoviesFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
}
