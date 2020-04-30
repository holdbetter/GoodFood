package com.example.goodfood;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class YuliasAdapter extends FragmentStateAdapter
{
    private ArrayList<Fragment> list;

    YuliasAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> uni)
    {
        super(fragmentActivity);
        list = uni;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        return list.get(position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
