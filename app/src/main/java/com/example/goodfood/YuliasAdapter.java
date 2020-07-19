package com.example.goodfood;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.goodfood.database.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class YuliasAdapter extends FragmentStateAdapter
{
    private List<Fragment> fragmentList = new ArrayList<>();

    public YuliasAdapter(@NonNull Fragment fragment)
    {
        super(fragment);

        fragmentList.add(new HelloFragment());
        fragmentList.add(new NameFragment());
        fragmentList.add(new SexFragment());
        fragmentList.add(new DateFragment());
        fragmentList.add(new HeightFragment());
        fragmentList.add(new WeightFragment());
        fragmentList.add(new DesiredWeightFragment());
        fragmentList.add(new LifestyleFragment());
        fragmentList.add(new PollEndFragment(new UserEntity()));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount()
    {
        return fragmentList.size();
    }

    public List<Fragment> getFragmentList()
    {
        return fragmentList;
    }
}
