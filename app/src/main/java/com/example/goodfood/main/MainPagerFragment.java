package com.example.goodfood.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.R;
import com.example.goodfood.database.UserEntity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainPagerFragment extends Fragment
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        // create ContextThemeWrapper from the original Activity Context with the custom theme
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MainTheme);
//
//        // clone the inflater using the ContextThemeWrapper
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View mainContent = inflater.inflate(R.layout.main_content_fragment, container, false);
        MainFragmentTabAdapter mainFragmentTabAdapter = new MainFragmentTabAdapter(this);
        ViewPager2 vp2 = mainContent.findViewById(R.id.viewPager);
        vp2.setAdapter(mainFragmentTabAdapter);
        vp2.setOffscreenPageLimit(2);
        vp2.setCurrentItem(1);

        TabLayout tabLayout = mainContent.findViewById(R.id.tab_layout);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, vp2, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                switch (position)
                {
                    case 0:
                    {
                        tab.setIcon(R.drawable.ic_stats);
                        break;
                    }
                    case 1:
                    {
                        tab.setIcon(R.drawable.ic_home);
                        break;
                    }
                    case 2:
                    {
                        tab.setIcon(R.drawable.ic_add_food);
                        break;
                    }
                }
            }
        });

        mediator.attach();

        return mainContent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        changeStatusBarColor(getActivity());
    }

    private void changeStatusBarColor(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
