package com.example.goodfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.database.UserEntity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        YuliasAdapter yuliasAdapter = new YuliasAdapter(this);
        View viewPagerView = inflater.inflate(R.layout.viewpager_fragment, container, false);
        ViewPager2 vp2 = viewPagerView.findViewById(R.id.viewPager);
        vp2.setAdapter(yuliasAdapter);

        return viewPagerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicator(R.drawable.tabs_layout_empty_background);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                tab.setCustomView(R.layout.tab_custom_view);
                tab.view.setClickable(false);
            }
        });

        mediator.attach();
    }
}
