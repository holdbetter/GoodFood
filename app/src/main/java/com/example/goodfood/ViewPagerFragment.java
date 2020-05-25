package com.example.goodfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.database.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment
{
    private List<Fragment> fragmentList;
    private UserEntity user;
    private FragmentActivity fragmentActivity;

    public ViewPagerFragment(FragmentActivity activity)
    {
        user = new UserEntity();
        fragmentList = new ArrayList<>();
        fragmentList.add(new HelloFragment());
        fragmentList.add(new NameFragment());
        fragmentList.add(new SexFragment());
        fragmentList.add(new DateFragment());
        fragmentList.add(new HeightFragment());
        fragmentList.add(new WeightFragment());
        fragmentList.add(new DesiredWeightFragment());
        fragmentList.add(new LifestyleFragment());
        fragmentList.add(new PollEndFragment(user));

        this.fragmentActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        YuliasAdapter yuliasAdapter = new YuliasAdapter(fragmentActivity, fragmentList);
        View viewPagerView = inflater.inflate(R.layout.viewpager_fragment, container, false);
        ViewPager2 vp2 = viewPagerView.findViewById(R.id.viewPager);
        vp2.setAdapter(yuliasAdapter);
        return viewPagerView;
    }
}
