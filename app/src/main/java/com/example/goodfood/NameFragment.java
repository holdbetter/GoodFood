package com.example.goodfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class NameFragment extends Fragment
{
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View nameView = inflater.inflate(R.layout.name_fragment, container, false);
        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);


        ButtonWithCustomBackground nextBtn = nameView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));


        final AppCompatEditText nameSet = nameView.findViewById(R.id.setup_name);
        nameSet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    nameSet.setHint("");
                    return;
                }

                nameSet.setHint(R.string.set_name_hint);
            }
        });

        nameView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (!(v instanceof EditText) && nameSet.hasFocus())
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View focucedView = getActivity().getCurrentFocus();
                    if (focucedView == null)
                    {
                        focucedView = new View(getActivity());
                    }
                    imm.hideSoftInputFromWindow(focucedView.getWindowToken(), 0);
                    nameSet.clearFocus();
                    return true;
                }

                return false;
            }
        });

        return nameView;
    }

    private class GoNextButton implements View.OnTouchListener
    {
        private final ViewPager2 vp2;

        public GoNextButton(ViewPager2 vp2)
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
}
