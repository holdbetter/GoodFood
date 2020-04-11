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
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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
