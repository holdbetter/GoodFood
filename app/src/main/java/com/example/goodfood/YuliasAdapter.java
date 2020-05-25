package com.example.goodfood;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class YuliasAdapter extends FragmentStateAdapter
{
    private List<Fragment> list;

    YuliasAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> uni)
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

    public List<Fragment> getFragmentList()
    {
        return list;
    }
}
