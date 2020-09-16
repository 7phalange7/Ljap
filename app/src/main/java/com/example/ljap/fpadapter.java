package com.example.ljap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class fpadapter extends FragmentPagerAdapter {

    public fpadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FamilyFragment();
            case 1: return new NumbersFragment();
            case 2: return new PhrasesFragment();
            case 3: return new ColorsFragment();
            default: return null;

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Family";
            case 1: return "Numbers";
            case 2: return "Phrases";
            case 3: return "Colours";
            default: return null;

        }
    }
}
