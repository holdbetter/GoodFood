package com.example.goodfood;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.aigestudio.wheelpicker.IWheelPicker;
import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDayPicker;
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;
import com.example.goodfood.database.UserEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateFragment extends Fragment
{
    private WheelDayPicker wheelDayPicker;
    private WheelMonthPicker wheelMonthPicker;
    private WheelYearPicker wheelYearPicker;
    private int currentYear;
    private int currentMonth;
    private int currentDay;

    public DateFragment()
    {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View dateView = inflater.inflate(R.layout.date_fragment, container, false);

        wheelDayPicker = dateView.findViewById(R.id.dayPicker);
        wheelMonthPicker = dateView.findViewById(R.id.monthPicker);
        wheelYearPicker = dateView.findViewById(R.id.yearPicker);

        wheelSetup(wheelDayPicker, wheelMonthPicker, wheelYearPicker);

        wheelDayPicker.setItemAlign(WheelPicker.ALIGN_RIGHT);

        wheelYearPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position)
            {
                wheelDayPicker.setYear((int) data);
                currentYear = (int) data;
            }
        });

        wheelMonthPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position)
            {
                wheelDayPicker.setYearAndMonth(currentYear, (int) data);
            }
        });

        wheelYearPicker.setYearFrame(1900, currentYear);
        wheelMonthPicker.setSelectedMonth(currentMonth);
        wheelDayPicker.setSelectedDay(currentDay);

        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        ButtonWithCustomBackground nextBtn = dateView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return dateView;
    }

    private void wheelSetup(IWheelPicker... wheelPickers)
    {
        for (IWheelPicker wheelPicker : wheelPickers)
        {
            wheelPicker.setCurved(false);
            wheelPicker.setCyclic(true);
            wheelPicker.setAtmospheric(true);
            wheelPicker.setVisibleItemCount(6);

            wheelPicker.setItemTextColor(ContextCompat.getColor(requireContext(), R.color.dateItem));

            Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat_semi_bold);
            wheelPicker.setTypeface(typeface);
        }
    }

    public long getDateInMillis()
    {
        if (wheelYearPicker != null)
        {
            int day = wheelDayPicker.getCurrentDay();
            int month = wheelMonthPicker.getCurrentMonth();
            int year = wheelYearPicker.getCurrentYear();

            return new GregorianCalendar(year, month - 1, day).getTimeInMillis();
        }
        else {
            return new GregorianCalendar().getTimeInMillis();
        }
    }

    private class GoNextButton implements View.OnTouchListener
    {
        private final ViewPager2 vp2;

        GoNextButton(ViewPager2 vp2)
        {
            this.vp2 = vp2;
        }

        //ctrl + i
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_pressed));
                    v.performClick();
                    return true;
                case MotionEvent.ACTION_UP:
                    if (vp2.getCurrentItem() < vp2.getAdapter().getItemCount() - 1)
                    {
                        vp2.setCurrentItem(vp2.getCurrentItem() + 1);
                    }
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                    v.performClick();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                    return true;
            }

            return false;
        }
    }
}
