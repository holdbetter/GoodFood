package com.example.goodfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HelloFragment extends Fragment
{
    private TabLayout tabLayout;
    private ViewPager2 vp2;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View helloView = inflater.inflate(R.layout.hello_fragment, container, false);
        ButtonWithCustomBackground nextBtn = helloView.findViewById(R.id.buttonGo);

        vp2 = getActivity().findViewById(R.id.viewPager);
        tabLayout = getActivity().findViewById(R.id.tab_layout);

        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return helloView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        tabLayout.setSelectedTabIndicator(R.drawable.tabs_layout_empty_background);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, vp2, new TabLayoutMediator.TabConfigurationStrategy()
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
