package com.example.goodfood.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.goodfood.MainActivity;
import com.example.goodfood.database.FoodEntity;
import com.example.goodfood.database.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentTabAdapter extends FragmentStateAdapter
{
    private List<Fragment> fragments = new ArrayList<>();

    public MainFragmentTabAdapter(Fragment fragment)
    {
        super(fragment);

        MainActivity activity = (MainActivity) fragment.getActivity();
        List<String> foodCategories = activity.getDatabase().foodDao().getFoodCategories();
        List<FoodEntity> foodInfoList = activity.getDatabase().foodDao().getFoodInfoList();

        fragments.add(new StatsFragment());
        fragments.add(new HomeFragment());
        fragments.add(new AddFoodFragment(foodCategories, foodInfoList));
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
