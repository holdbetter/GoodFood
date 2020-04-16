package com.example.goodfood;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;

import java.util.Calendar;

public class DateFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.date_fragment, container, false);

        WheelDatePicker wheelDatePicker = v.findViewById(R.id.number_picker);
        wheelDatePicker.setCurved(false);
        wheelDatePicker.setCyclic(true);
        wheelDatePicker.setAtmospheric(true);
//        wheelDatePicker.setItemSpace();
        TextView textView1 = wheelDatePicker.getTextViewDay();
        textView1.setText("");
        TextView textView2 = wheelDatePicker.getTextViewMonth();
        textView2.setText("");
        TextView textView3 = wheelDatePicker.getTextViewYear();
        textView3.setText("");
        wheelDatePicker.setItemTextColor(ContextCompat.getColor(requireContext(), R.color.dateItem));

        wheelDatePicker.setVisibleItemCount(6);

        Calendar calendar = Calendar.getInstance();

        wheelDatePicker.setYearFrame(1940, calendar.get(Calendar.YEAR));


        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat_semi_bold);
        wheelDatePicker.setTypeface(typeface);

        wheelDatePicker.setItemSpace(8);
        wheelDatePicker.setSelectedYear(calendar.get(Calendar.YEAR));
        wheelDatePicker.setSelectedMonth(calendar.get(Calendar.MONTH) + 1);
        wheelDatePicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));


        int test = wheelDatePicker.getItemSpace();

        return v;
    }
}
