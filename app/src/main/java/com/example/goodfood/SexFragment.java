package com.example.goodfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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

        final AppCompatImageView womanImage = sexView.findViewById(R.id.img_woman);
        final AppCompatImageView manImage = sexView.findViewById(R.id.img_man);
        final AppCompatTextView womanText = sexView.findViewById(R.id.textView_woman);
        final AppCompatTextView manText = sexView.findViewById(R.id.textView_man);

        womanImage.setAlpha(0.3f);
        manImage.setAlpha(0.3f);
        womanText.setAlpha(0.3f);
        manText.setAlpha(0.3f);

        womanLinear.setOnClickListener(new WomanPressed(womanText, manText, womanImage, manImage));

        manLinear.setOnClickListener(new ManPressed(manText, womanText, manImage, womanImage));

        final ViewPager2 vp2 = getActivity().findViewById(R.id.viewPager);

        ButtonWithCustomBackground nextBtn = sexView.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));


        return sexView;
    }

    private void sexSelected(AppCompatTextView selectedText, AppCompatTextView oldText, AppCompatImageView selectedImage, AppCompatImageView oldImage, boolean selectedForFirstTime)
    {
        int heightIncrease = selectedImage.getHeight() + 40;
        int widthIncrease = selectedImage.getWidth() + 40;
        LinearLayout.LayoutParams paramsNew = new LinearLayout.LayoutParams(widthIncrease, heightIncrease);
        paramsNew.gravity = Gravity.CENTER_HORIZONTAL;
        selectedImage.setLayoutParams(paramsNew);

        selectedImage.setAlpha(1f);
        selectedText.setAlpha(1f);

        if(!selectedForFirstTime)
        {
            int heightDecrease = oldImage.getHeight() - 40;
            int widthDecrease = oldImage.getWidth() - 40;
            LinearLayout.LayoutParams paramsOld = new LinearLayout.LayoutParams(widthDecrease, heightDecrease);
            paramsOld.gravity = Gravity.CENTER_HORIZONTAL;
            oldImage.setLayoutParams(paramsOld);
        }


        oldText.setTextSize(12);
        selectedText.setTextSize(16);

        oldImage.setAlpha(0.3f);
        oldText.setAlpha(0.3f);
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
                sexSelected(womanText, manText, womanImage, manImage, true);
                isWomanSelected = true;
                isManSelected = false;
                return;
            }

            if (!isWomanSelected)
            {
                sexSelected(womanText, manText, womanImage, manImage, false);
            }

            isWomanSelected = true;
            isManSelected = false;
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
                sexSelected(manText, womanText, manImage, womanImage, true);
                isWomanSelected = false;
                isManSelected = true;
                return;
            }

            if (!isManSelected)
            {
                sexSelected(manText, womanText, manImage, womanImage, false);
            }

            isManSelected = true;
            isWomanSelected = false;
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
        @SuppressLint("ClickableViewAccessibility")
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
