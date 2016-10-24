package com.example.ifigm.mavideotheque.view.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by ifigm on 24/10/2010.
 */
public class SectionPageAdapter extends FragmentPagerAdapter {

    private FragmentToutFilm toutFilm = new FragmentToutFilm();
    private FragmentFilmEmprunt toutFilm2 = new FragmentFilmEmprunt();


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
                return "Dilm Emprunte";
        }
    }

}
