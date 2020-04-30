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

    private class GoNextButton implements View.OnTouchListener
    {
        private final ViewPager2 vp2;

        GoNextButton(ViewPager2 vp2)
        {
            this.vp2 = vp2;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_pressed));
                    return true;
                case MotionEvent.ACTION_UP:
                    Log.i("CHECK_INFO", "ID текущего элемента: " + vp2.getCurrentItem());
                    Log.i("CHECK_INFO", "ID cледующего элемента: " + (vp2.getCurrentItem() + 1));
                    Log.i("CHECK_INFO", "всего элементов: " + vp2.getAdapter().getItemCount());
                    if (vp2.getCurrentItem() < vp2.getAdapter().getItemCount() - 1)
                    {
                        vp2.setCurrentItem(vp2.getCurrentItem() + 1);
                    }
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                    return true;
            }

            return false;
        }
    }
}
