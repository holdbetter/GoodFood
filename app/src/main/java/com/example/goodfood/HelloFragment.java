package com.example.goodfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.services.GoNextButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HelloFragment extends Fragment
{
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View helloView = inflater.inflate(R.layout.hello_fragment, container, false);
        ButtonWithCustomBackground nextBtn = helloView.findViewById(R.id.buttonGo);

        ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return helloView;
    }
}
