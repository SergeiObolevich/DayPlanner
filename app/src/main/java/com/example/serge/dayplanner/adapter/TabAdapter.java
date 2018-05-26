package com.example.serge.dayplanner.adapter;

import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.serge.dayplanner.fragment.CurrentTaskFragment;
import com.example.serge.dayplanner.fragment.DoneTaskFragment;

public class TabAdapter extends FragmentStatePagerAdapter{
    private int numberOfTabs;
    public TabAdapter(android.support.v4.app.FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new CurrentTaskFragment();
            }
            case 1: {
                return new DoneTaskFragment();
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
