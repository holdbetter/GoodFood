package com.example.goodfood;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements KeyEvent.Callback
{
    static ArrayList<Fragment> fragmentArrayList;

    //dpi (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi) - плотность экрана 800х400, 1280х720, 1920х1080
    //long (16:9, 18:9, >18:9, 21:9, 19:9, 18.5:9)
    //sw-600dp - true!

    static
    {
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HelloFragment());
        fragmentArrayList.add(new NameFragment());
        fragmentArrayList.add(new SexFragment());
        fragmentArrayList.add(new DateFragment());
        fragmentArrayList.add(new HeightFragment());
        fragmentArrayList.add(new WeightFragment());
        fragmentArrayList.add(new DesiredWeightFragment());
        fragmentArrayList.add(new LifestyleFragment());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER)
        {
            return editTextClosingHandle();
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        YuliasAdapter yuliasAdapter = new YuliasAdapter(this, fragmentArrayList);

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return editTextClosingHandle();
            }
        });
        viewPager2.setAdapter(yuliasAdapter);
    }

    private boolean editTextClosingHandle()
    {
        AppCompatEditText valueEditText = findViewById(R.id.set_value);
        if (valueEditText != null)
        {
            int value;

            if (valueEditText.length() == 0)
            {
                value = 0;
            } else
            {
                value = Integer.parseInt(valueEditText.getText().toString());
            }

            if (valueEditText.getTag().toString().equals("height"))
            {
                if (value > getResources().getInteger(R.integer.max_height))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.max_height)));
                } else if (value < getResources().getInteger(R.integer.min_height))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.min_height)));
                }
            } else
            {
                if (value > getResources().getInteger(R.integer.max_weight))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.max_weight)));
                } else if (value < getResources().getInteger(R.integer.min_weight))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.min_weight)));
                }
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(valueEditText.getWindowToken(), 0);
            valueEditText.clearFocus();
            return true;
        }

        return false;
    }
}
