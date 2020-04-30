package com.example.goodfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

public class HeightFragment extends Fragment
{
    private int timeout = 600;
    private AppCompatEditText heightEditText;
    private int heightInteger;
    private Handler handler = new Handler();
    private Runnable changeValueTask;
    private int minHeight;
    private int maxHeight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        minHeight = getResources().getInteger(R.integer.min_height);
        maxHeight = getResources().getInteger(R.integer.max_height);

        final View heightView = inflater.inflate(R.layout.height_fragment, container, false);
        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);
        final FrameLayout plus_btn = heightView.findViewById(R.id.plus_layout);
        final FrameLayout minus_btn = heightView.findViewById(R.id.minus_layout);

        heightEditText = heightView.findViewById(R.id.set_value);
        heightEditText.addTextChangedListener(new TextValueChangedListener());

        heightInteger = getHeightValue();

        changeValueTask = new Runnable()
        {
            @Override
            public void run()
            {
                if (plus_btn.isPressed())
                {
                    heightInteger++;
                    if (heightInteger > maxHeight)
                    {
                        heightInteger = minHeight;
                    }
                }

                if (minus_btn.isPressed())
                {
                    heightInteger--;
                    if (heightInteger < minHeight)
                    {
                        heightInteger = maxHeight;
                    }
                }

                heightEditText.setText(String.valueOf(heightInteger));

                if (timeout > 200)
                {
                    timeout -= 65;
                }

                handler.postDelayed(changeValueTask, timeout);
            }
        };

        plus_btn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN && !minus_btn.isPressed())
                {
                    heightInteger = getHeightValue();
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

        heightView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (!(v instanceof EditText) && heightEditText.hasFocus())
                {
                    int value = 0;

                    try
                    {
                        value = Integer.parseInt(heightEditText.getText().toString());
                    } catch (NumberFormatException ignored)
                    {
                        value = 0;
                    }

                    if (value > getResources().getInteger(R.integer.max_height))
                    {
                        heightEditText.setText(String.valueOf(getResources().getInteger(R.integer.max_height)));
                    } else if (value < getResources().getInteger(R.integer.min_height))
                    {
                        heightEditText.setText(String.valueOf(getResources().getInteger(R.integer.min_height)));
                    }

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View focucedView = getActivity().getCurrentFocus();
                    if (focucedView == null)
                    {
                        focucedView = new View(getActivity());
                    }
                    imm.hideSoftInputFromWindow(focucedView.getWindowToken(), 0);
                    heightEditText.clearFocus();
                    return true;
                }

                return false;
            }
        });


        ButtonWithCustomBackground nextBtn = heightView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        return heightView;

        //simpleIncresing
//        plus_btn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                heightInteger++;
//                heightEditText.setText(String.valueOf(heightInteger));
//            }
//        });

        //        minus_btn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                heightInteger--;
//                heightEditText.setText(String.valueOf(heightInteger));
//            }
//        });
    }

    private void changeValueRunner()
    {
        changeValueTask.run();
    }

    private int getHeightValue()
    {
        if (heightEditText.getText() == null)
        {
            return minHeight;
        } else if (heightEditText.getText().length() == 0)
        {
            return minHeight;
        } else
        {
            int result = minHeight;

            try
            {
                result = Integer.parseInt(heightEditText.getText().toString());
            } catch (NumberFormatException ignored)
            {
                return result;
            }

            if (result < minHeight || result > maxHeight)
            {
                result = minHeight;
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
            if (s.length() > 3)
            {
                s.delete(s.length() - 1, s.length());
            }

            if (s.length() == 0)
            {
                heightEditText.setText(String.valueOf(minHeight));
            }
        }
    }
}
