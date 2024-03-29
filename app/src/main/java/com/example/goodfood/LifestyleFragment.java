package com.example.goodfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.services.GoNextButton;

import java.util.ArrayList;

public class LifestyleFragment extends Fragment
{
    private ArrayList<AppCompatButton> selectedButtons;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        selectedButtons = new ArrayList<>();

        View lifestyleFragment = inflater.inflate(R.layout.lifestyle_fragment, container, false);
        ButtonWithCustomBackground nextBtn = lifestyleFragment.findViewById(R.id.buttonGo);
        AppCompatButton lifestyleUnderneath = lifestyleFragment.findViewById(R.id.lifestyleUnderneath);
        AppCompatButton lifestyleLow = lifestyleFragment.findViewById(R.id.lifestyleLow);
        AppCompatButton lifestyleMedium = lifestyleFragment.findViewById(R.id.lifestyleMedium);
        AppCompatButton lifestyleHigh = lifestyleFragment.findViewById(R.id.lifestyleHigh);

        lifestyleUnderneath.setOnTouchListener(new LifestyleSelect());
        lifestyleLow.setOnTouchListener(new LifestyleSelect());
        lifestyleMedium.setOnTouchListener(new LifestyleSelect());
        lifestyleHigh.setOnTouchListener(new LifestyleSelect());

        ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return lifestyleFragment;
    }

    public String getLifestyleString()
    {
        if (selectedButtons != null && selectedButtons.size() > 0)
        {
           return selectedButtons.get(0).getText().toString();
        }
        else
        {
            return "Не выбран";
        }
    }

    public double getLifestyleValue()
    {
        if (selectedButtons != null && selectedButtons.size() > 0)
        {
            switch (selectedButtons.get(0).getText().toString())
            {
                case "Я - амембка и не делаю лишних движений":
                    return 1.2;
                case "Мало двигаюсь":
                    return 1.375;
                case "Люблю гулять или просто часто передвигаюсь":
                    return 1.6;
                case "Я тренируюсь в спортзале":
                    return 1.8;
                default:
                    return 1.0;
            }
        }
        else
        {
            return 1.0;
        }
    }

    private class LifestyleSelect implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            AppCompatButton lifestyleBtn = (AppCompatButton) v;

            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                for (AppCompatButton selectedButton : selectedButtons)
                {
                    selectedButton.setPressed(false);
                }
                selectedButtons.clear();

                lifestyleBtn.setPressed(true);

                if (lifestyleBtn.isPressed())
                {
                    selectedButtons.add(lifestyleBtn);
                }
            }
            return true;
        }
    }
}
