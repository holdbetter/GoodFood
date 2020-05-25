package com.example.goodfood.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.goodfood.DesiredWeightFragment;
import com.example.goodfood.HeightFragment;
import com.example.goodfood.HelloFragment;
import com.example.goodfood.MainActivity;
import com.example.goodfood.NameFragment;
import com.example.goodfood.WeightFragment;
import com.example.goodfood.database.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentTabAdapter extends FragmentStateAdapter
{
    private List<Fragment> fragments = new ArrayList<>();

    public MainFragmentTabAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);

        MainActivity activity = (MainActivity) fragmentActivity;
        UserEntity user = activity.database.userDao().getUserInfo();

        fragments.add(new HomeFragment(user, activity.database));
        fragments.add(new HomeFragment(user, activity.database));
        fragments.add(new HomeFragment(user, activity.database));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getItemCount()
    {
        return fragments.size();
    }
}