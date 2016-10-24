package com.example.ifigm.mavideotheque.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by ifigm on 24/10/2010.
 */
public class SectionPageAdapter extends FragmentPagerAdapter {

    private FragmentFilm toutFilm = new FragmentFilm();
    private FragmentFilm toutFilm2 = new FragmentFilm();


    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return toutFilm;
            case 1:
            default:
                return toutFilm2;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tout Film";

            default:
                return "Tout Film";
        }
    }

}
