package com.example.goodfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class WeightFragment extends Fragment
{
    private boolean isLongPress;
    private int timeout = 50;
    private AppCompatEditText weightEditText;
    private int weightInteger;
    private int stopPlus = -1;
    private int stopMinus = -1;
    private Handler handler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View weightView = inflater.inflate(R.layout.weight_fragment, container, false);
        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        FrameLayout plus_btn = weightView.findViewById(R.id.plus_layout);
        FrameLayout minus_btn = weightView.findViewById(R.id.minus_layout);

        weightEditText = weightView.findViewById(R.id.set_value);
        weightEditText.addTextChangedListener(new TextValueChangedListener());
//        weightEditText.setOnClickListener();

        weightInteger = getWeightValue();

        //simpleIncresing
        plus_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                weightInteger++;
                weightEditText.setText(String.valueOf(weightInteger));
            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                weightInteger--;
                weightEditText.setText(String.valueOf(weightInteger));
            }
        });

        weightEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                weightEditText.requestFocus();
            }
        });

        weightView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (!(v instanceof EditText) && imm.isActive())
                {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.performClick();
                    return true;
                }
                return false;
            }
        });

        ButtonWithCustomBackground nextBtn = weightView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return weightView;

        //        handler = new Handler();
//        final Runnable r = new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                minusWeight(1000);
//            }
//        };

        //timer ???
//        plus_btn.setOnTouchListener(new PlusButtonListenerSetup());
//        minus_btn.setOnTouchListener(new MinusButtonListenerSetup());
    }

    private int getWeightValue()
    {
        if (weightEditText.getText() == null)
        {
            return 30;
        } else if (weightEditText.getText().length() == 0)
        {
            return 30;
        } else
        {
            int result = 30;

            try
            {
                result = Integer.parseInt(weightEditText.getText().toString());
            } catch (NumberFormatException ignored)
            {
                return result;
            }

            if (result < 31 || result > 150)
            {
                result = 30;
            }

            return result;
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


    private class TextValueChangedListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if(s.length() > 3)
            {
                s.delete(s.length() - 1, s.length());
            }

            if (s.length() == 0)
            {
                weightEditText.setText("0");
            }
        }
    }

    //    private void plusWeight(int magnifier)
//    {
//        if (magnifier > 200)
//        {
//            magnifier -= 100;
//        }
//
//        final int magn = magnifier;
//
//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                if (stopPlus != 0)
//                {
//                    weightInteger = getWeightValue();
//                    weightInteger += 1;
//                    weightEditText.setText(weightInteger + "");
//
//                    plusWeight(magn);
//                }
//
//            }
//        }, magnifier);
//    }

//    private void minusWeight(int magnifier)
//    {
//        if (magnifier > 200)
//        {
//            magnifier -= 100;
//        }
//
//        final int magn = magnifier;
//
//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                if (stopMinus != 0)
//                {
//                    weightInteger = getWeightValue();
//                    weightInteger -= 1;
//                    weightEditText.setText(weightInteger + "");
//
//                    minusWeight(magn);
//                }
//
//            }
//        }, magnifier);
//    }

//    private class MinusButtonListenerSetup implements View.OnTouchListener
//    {
//        @Override
//        public boolean onTouch(View v, MotionEvent event)
//        {
//            switch (event.getAction())
//            {
//                case MotionEvent.ACTION_DOWN:
//                    stopMinus = 0;
//                    stopPlus = 0;
//                    weightInteger = getWeightValue();
//                    weightInteger -= 1;
//                    weightEditText.setText(weightInteger + "");
//
//                    stopMinus = -1;
//                    minusWeight(1000);
//                    return true;
//                case MotionEvent.ACTION_UP:
//                    stopMinus = 0;
//                    return true;
//                case MotionEvent.ACTION_MOVE:
//                    return false;
//                default:
//                    stopMinus = 0;
//                    return false;
//            }
//        }
//    }

//    private class PlusButtonListenerSetup implements View.OnTouchListener
//    {
//        @Override
//        public boolean onTouch(View v, MotionEvent event)
//        {
//            switch (event.getAction())
//            {
//                case MotionEvent.ACTION_DOWN:
//                    stopPlus = 0;
//                    stopMinus = 0;
//                    weightInteger = getWeightValue();
//                    weightInteger += 1;
//                    weightEditText.setText(weightInteger + "");
//
//                    stopPlus = -1;
//                    plusWeight(1000);
//                    return true;
//                case MotionEvent.ACTION_UP:
//                    stopPlus = 0;
//                    return true;
//                case MotionEvent.ACTION_MOVE:
//                    return false;
//                default:
//                    stopMinus = 0;
//                    return false;
//            }
//        }
//    }
}
