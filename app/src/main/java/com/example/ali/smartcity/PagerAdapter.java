package com.example.ali.smartcity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int nbTabs;

    public PagerAdapter(FragmentManager fm, int nbTabs) {
        super(fm);
        this.nbTabs = nbTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                NewsFragment newsFragment = new NewsFragment();
                return newsFragment;
            case 1:
                SocialFragment socialFragment = new SocialFragment();
                return socialFragment;
            case 2:
                CommerceFragment commerceFragment = new CommerceFragment();
                return commerceFragment;
            case 3:
                AdsFragment adsFragment = new AdsFragment();
                return adsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nbTabs;
    }
}
