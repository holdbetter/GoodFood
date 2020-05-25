package com.example.goodfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.database.UserEntity;

import java.util.ArrayList;

public class LifestyleFragment extends Fragment
{
    private ArrayList<AppCompatButton> selectedButtons = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View lifestyleFragment = inflater.inflate(R.layout.lifestyle_fragment, container, false);
        ButtonWithCustomBackground nextBtn = lifestyleFragment.findViewById(R.id.buttonGo);
        final AppCompatButton lifestyleUnderneath = lifestyleFragment.findViewById(R.id.lifestyleUnderneath);
        final AppCompatButton lifestyleLow = lifestyleFragment.findViewById(R.id.lifestyleLow);
        final AppCompatButton lifestyleMedium = lifestyleFragment.findViewById(R.id.lifestyleMedium);
        final AppCompatButton lifestyleHigh = lifestyleFragment.findViewById(R.id.lifestyleHigh);

        lifestyleUnderneath.setOnTouchListener(new LifestyleSelect(lifestyleUnderneath));
        lifestyleLow.setOnTouchListener(new LifestyleSelect(lifestyleLow));
        lifestyleMedium.setOnTouchListener(new LifestyleSelect(lifestyleMedium));
        lifestyleHigh.setOnTouchListener(new LifestyleSelect(lifestyleHigh));

        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return lifestyleFragment;
    }

    public String getLifestyleString()
    {
        if (selectedButtons.size() > 0)
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
        if (selectedButtons.size() > 0)
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

    private class LifestyleSelect implements View.OnTouchListener
    {
        private final AppCompatButton lifestyleUnderneath;

        public LifestyleSelect(AppCompatButton lifestyleUnderneath)
        {
            this.lifestyleUnderneath = lifestyleUnderneath;
        }

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

                lifestyleBtn.setPressed(!lifestyleUnderneath.isPressed());

                if (lifestyleBtn.isPressed())
                {
                    selectedButtons.add(lifestyleBtn);
                }
            }
            return true;
        }
    }
}
