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

import com.example.goodfood.database.UserEntity;

public class DesiredWeightFragment extends Fragment
{
    private int timeout = 600;
    private AppCompatEditText weightEditText;
    private int weightInteger;
    private Handler handler = new Handler();
    private Runnable changeValueTask;
    private int minWeight;
    private int maxWeight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        minWeight = getResources().getInteger(R.integer.min_weight);
        maxWeight = getResources().getInteger(R.integer.max_weight);

        final View weightView = inflater.inflate(R.layout.desired_weight_fragment, container, false);
        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        final FrameLayout plus_btn = weightView.findViewById(R.id.plus_layout);
        final FrameLayout minus_btn = weightView.findViewById(R.id.minus_layout);

        weightEditText = weightView.findViewById(R.id.set_value);
        weightEditText.addTextChangedListener(new TextValueChangedListener());

        changeValueTask = new Runnable()
        {
            @Override
            public void run()
            {
                if (plus_btn.isPressed())
                {
                    weightInteger++;
                    if (weightInteger > maxWeight)
                    {
                        weightInteger = minWeight;
                    }
                }

                if (minus_btn.isPressed())
                {
                    weightInteger--;
                    if (weightInteger < minWeight)
                    {
                        weightInteger = maxWeight;
                    }
                }

                weightEditText.setText(String.valueOf(weightInteger));

                if (timeout > 200)
                {
                    timeout -= 65;
                }

                handler.postDelayed(changeValueTask, timeout);
            }
        };

        weightInteger = getWeightValue();

        plus_btn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN && !minus_btn.isPressed())
                {
                    weightInteger = getWeightValue();
                    plus_btn.setPressed(true);
                    changeValueRunner();
                    return true;
                } else if (event.getAction() != MotionEvent.ACTION_MOVE)
                {
                    handler.removeCallbacks(changeValueTask);
                    timeout = 600;
                    plus_btn.setPressed(false);
                    return true;
                }

                return false;
            }
        });

        minus_btn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN && !plus_btn.isPressed())
                {
                    minus_btn.setPressed(true);
                    changeValueRunner();
                    return true;
                } else if (event.getAction() != MotionEvent.ACTION_MOVE)
                {
                    handler.removeCallbacks(changeValueTask);
                    timeout = 600;
                    minus_btn.setPressed(false);
                    return true;
                }

                return false;
            }
        });

        weightView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (!(v instanceof EditText) && weightEditText.hasFocus())
                {
                    int value = 0;

                    try
                    {
                        value = Integer.parseInt(weightEditText.getText().toString());
                    } catch (NumberFormatException ignored)
                    {
                        value = 0;
                    }

                    if (value > getResources().getInteger(R.integer.max_weight))
                    {
                        weightEditText.setText(String.valueOf(getResources().getInteger(R.integer.max_weight)));
                    } else if (value < getResources().getInteger(R.integer.min_weight))
                    {
                        weightEditText.setText(String.valueOf(getResources().getInteger(R.integer.min_weight)));
                    }

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View focucedView = getActivity().getCurrentFocus();
                    if (focucedView == null)
                    {
                        focucedView = new View(getActivity());
                    }
                    imm.hideSoftInputFromWindow(focucedView.getWindowToken(), 0);
                    weightEditText.clearFocus();
                    v.performClick();
                    return true;
                }

                return false;
            }
        });

        ButtonWithCustomBackground nextBtn = weightView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return weightView;
    }

    private void changeValueRunner()
    {
        changeValueTask.run();
    }

    public int getWeightValue()
    {
        if (weightEditText != null)
        {
            if (weightEditText.getText() == null)
            {
                return minWeight;
            } else if (weightEditText.getText().length() == 0)
            {
                return minWeight;
            } else
            {
                int result = minWeight;

                try
                {
                    result = Integer.parseInt(weightEditText.getText().toString());
                } catch (NumberFormatException ignored)
                {
                    return result;
                }

                if (result < minWeight || result > maxWeight)
                {
                    result = minWeight;
                }

                return result;
            }
        }
        else
        {
            return 0;
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
            if (s.length() > 3)
            {
                s.delete(s.length() - 1, s.length());
            }

            if (s.length() == 0)
            {
                weightEditText.setText(String.valueOf(minWeight));
            }
        }
    }
}