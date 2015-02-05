package com.draw_lessons.app.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

// Clase adaptador para los fragments del minitutorial.

public class adapter_tutorial extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public adapter_tutorial(FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
//a
        return fragments.get(position);
    }

    @Override
    public int getCount() {

        return fragments.size();
    }

}
