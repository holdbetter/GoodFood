package com.example.goodfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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

import com.example.goodfood.services.GoNextButton;

public class NameFragment extends Fragment
{
    private ViewPager2 vp2;
    private AppCompatEditText nameSet;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View nameView = inflater.inflate(R.layout.name_fragment, container, false);
        vp2 = getActivity().findViewById(R.id.viewPager);

        ButtonWithCustomBackground nextBtn = nameView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        nameSet = nameView.findViewById(R.id.set_value);

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

    public String getNameValue()
    {
        if (nameSet != null )
        {
            if (nameSet.getText() != null && nameSet.getText().length() > 0)
            {
                return nameSet.getText().toString();
            }
            return "Не указано";
        }
        {
            return "Не указано";
        }
    }
}
