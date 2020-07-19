package com.example.goodfood;

import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class GoNextButton implements View.OnTouchListener
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
                v.setBackground(v.getResources().getDrawable(R.drawable.next_button_pressed));
                return true;
            case MotionEvent.ACTION_UP:
                if (vp2.getCurrentItem() < vp2.getAdapter().getItemCount() - 1)
                {
                    vp2.setCurrentItem(vp2.getCurrentItem() + 1);
                }
                v.setBackground(v.getResources().getDrawable(R.drawable.next_button_def));
                return true;
            case MotionEvent.ACTION_MOVE:
                v.setBackground(v.getResources().getDrawable(R.drawable.next_button_def));
                return true;
        }

        return false;
    }
}
