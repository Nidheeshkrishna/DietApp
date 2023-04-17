package com.example.dietapp.ui.Adapter;

import com.example.dietapp.ui.Fragment.ActiveOrdersFragment;
import com.example.dietapp.ui.Fragment.DeliveredOrdersFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DeliveryBoyAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    public DeliveryBoyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return ActiveOrdersFragment.newInstance("","");
            case 1:
                return DeliveredOrdersFragment.newInstance("","");
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    public void addFragment2(Fragment fragment){
        mFragmentList.add(fragment);
    }
}