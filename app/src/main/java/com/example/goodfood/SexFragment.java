package com.example.goodfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.services.GoNextButton;

public class SexFragment extends Fragment
{
    private boolean isWomanSelected = false;
    private boolean isManSelected = false;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View sexView = inflater.inflate(R.layout.sex_fragment, container, false);

        LinearLayout womanLinear = sexView.findViewById(R.id.woman);
        LinearLayout manLinear = sexView.findViewById(R.id.man);

        AppCompatImageView womanImage = sexView.findViewById(R.id.img_woman);
        AppCompatImageView manImage = sexView.findViewById(R.id.img_man);
        AppCompatTextView womanText = sexView.findViewById(R.id.textView_woman);
        AppCompatTextView manText = sexView.findViewById(R.id.textView_man);

        womanLinear.setOnClickListener(new WomanPressed(womanText, manText, womanImage, manImage));
        manLinear.setOnClickListener(new ManPressed(manText, womanText, manImage, womanImage));

        ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);

        ButtonWithCustomBackground nextBtn = sexView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        isManSelected = false;
        isWomanSelected = false;

        return sexView;
    }

    private void sexSelected(AppCompatTextView selectedText, AppCompatTextView oldText, AppCompatImageView selectedImage, AppCompatImageView oldImage, boolean decreaseElement)
    {
        int heightIncrease = selectedImage.getHeight() + 40;
        int widthIncrease = selectedImage.getWidth() + 40;
        LinearLayout.LayoutParams paramsNew = new LinearLayout.LayoutParams(widthIncrease, heightIncrease);
        paramsNew.gravity = Gravity.CENTER_HORIZONTAL;
        selectedImage.setLayoutParams(paramsNew);

        selectedImage.setAlpha(1f);
        selectedText.setAlpha(1f);

        if (decreaseElement)
        {
            int heightDecrease = oldImage.getHeight() - 40;
            int widthDecrease = oldImage.getWidth() - 40;
            LinearLayout.LayoutParams paramsOld = new LinearLayout.LayoutParams(widthDecrease, heightDecrease);
            paramsOld.gravity = Gravity.CENTER_HORIZONTAL;
            oldImage.setLayoutParams(paramsOld);
            oldText.setTextSize(12);
        }

        selectedText.setTextSize(16);

        oldImage.setAlpha(0.3f);
        oldText.setAlpha(0.3f);
    }

    public String getSexValue()
    {
        if (isWomanSelected)
        {
            return "Женский";
        } else if (isManSelected)
        {
            return "Мужской";
        }
        else
        {
            return "Пол не указан";
        }
    }

    private class WomanPressed implements View.OnClickListener
    {
        private final AppCompatTextView womanText;
        private final AppCompatTextView manText;
        private final AppCompatImageView womanImage;
        private final AppCompatImageView manImage;

        WomanPressed(AppCompatTextView womanText, AppCompatTextView manText, AppCompatImageView womanImage, AppCompatImageView manImage)
        {
            this.womanText = womanText;
            this.manText = manText;
            this.womanImage = womanImage;
            this.manImage = manImage;
        }

        @Override
        public void onClick(View view)
        {
            if (!isManSelected && !isWomanSelected)
            {
                sexSelected(womanText, manText, womanImage, manImage, false);
                isWomanSelected = true;
                isManSelected = false;
                return;
            }

            if (!isWomanSelected)
            {
                sexSelected(womanText, manText, womanImage, manImage, true);
                isWomanSelected = true;
                isManSelected = false;
            }
        }
    }

    private class ManPressed implements View.OnClickListener
    {
        private final AppCompatTextView manText;
        private final AppCompatTextView womanText;
        private final AppCompatImageView manImage;
        private final AppCompatImageView womanImage;

        ManPressed(AppCompatTextView manText, AppCompatTextView womanText, AppCompatImageView manImage, AppCompatImageView womanImage)
        {
            this.manText = manText;
            this.womanText = womanText;
            this.manImage = manImage;
            this.womanImage = womanImage;
        }

        @Override
        public void onClick(View view)
        {
            if (!isManSelected && !isWomanSelected)
            {
                sexSelected(manText, womanText, manImage, womanImage, false);
                isWomanSelected = false;
                isManSelected = true;
                return;
            }

            if (!isManSelected)
            {
                sexSelected(manText, womanText, manImage, womanImage, true);
                isManSelected = true;
                isWomanSelected = false;
            }
        }
    }
}
