package com.example.goodfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements KeyEvent.Callback
{
    static ArrayList<Fragment> fragmentArrayList;

    static {
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
        AppCompatEditText valueEditText = findViewById(R.id.set_value);

        if (valueEditText != null)
        {
            if (keyCode == KeyEvent.KEYCODE_ENTER)
            {
                //                    int w;

//                    if (valueEditText.length() == 0)
//                    {
//                        w = 0;
//                    } else
//                    {
//                        w = Integer.parseInt(valueEditText.getText().toString());
//                    }
//
//                    if (w > 200)
//                    {
//                        valueEditText.setText("200");
//                    } else if (w < 20)
//                    {
//                        valueEditText.setText("30");
//                    }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(valueEditText.getWindowToken(), 0);
                valueEditText.clearFocus();
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null && !(v instanceof  AppCompatEditText))
                {
                    AppCompatEditText valueEditText = findViewById(R.id.set_value);
                    imm.hideSoftInputFromWindow(valueEditText.getWindowToken(), 0);
                    valueEditText.clearFocus();
                    return true;
                }

                return false;
            }
        });
        viewPager2.setAdapter(yuliasAdapter);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        if (imm != null && imm.isActive())
//        {
//            AppCompatEditText valueEditText = findViewById(R.id.set_value);
//
//            imm.hideSoftInputFromWindow(valueEditText.getWindowToken(), 0);
//            valueEditText.clearFocus();
//            return true;
//        }
//
//        return false;
//    }
}
