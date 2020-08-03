package com.example.goodfood.main;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.goodfood.ButtonWithCustomBackground;
import com.example.goodfood.MainActivity;
import com.example.goodfood.R;
import com.example.goodfood.database.GoodFoodDatabase;
import com.example.goodfood.database.UserEntity;
import com.example.goodfood.database.WaterEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WaterPart extends Fragment
{
    private Calendar currentDateShowing;
    private BottomSheetDialog bottomSheetDialog;
    private int totalMlCount;
    private EditText waterMlEditText;
    private GoodFoodDatabase database;
    private UserEntity user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        database = ((MainActivity) getActivity()).getDatabase();
        user = ((MainActivity) getActivity()).getUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View waterView = inflater.inflate(R.layout.water_part, container, false);
        currentDateShowing = Calendar.getInstance();
        currentDateShowing.setTimeInMillis(currentDateShowing.getTimeInMillis() + MainActivity.getPhoneTimezoneOffset());

        TextView totalMl = waterView.findViewById(R.id.totalMl);
        Calendar calendarOffset = (Calendar) currentDateShowing.clone();
        calendarOffset.set(Calendar.HOUR_OF_DAY, 0);
        calendarOffset.set(Calendar.MINUTE, 0);
        calendarOffset.set(Calendar.SECOND, 0);
        calendarOffset.set(Calendar.MILLISECOND, 0);
        calendarOffset.setTimeInMillis(calendarOffset.getTimeInMillis() - MainActivity.getPhoneTimezoneOffset());
        Calendar nextDay = (Calendar) calendarOffset.clone();
        nextDay.roll(Calendar.DAY_OF_YEAR, 1);
        totalMlCount = database.waterDao().getMlCountForThisDate(calendarOffset.getTimeInMillis(),
                nextDay.getTimeInMillis()).getMl();
        totalMl.setText(mlToLStringBuilder(totalMlCount));

        TextView startMl = waterView.findViewById(R.id.startMlProgressText);
        startMl.setText(mlToLStringBuilder(0));

        TextView endMl = waterView.findViewById(R.id.endMlProgressText);
        endMl.setText(mlToLStringBuilder(user.normWaterL));

        ProgressBar mlProgress = waterView.findViewById(R.id.mlProgress);
        //should be query
        mlProgress.setProgress(progressSetupValue(totalMlCount, user.normWaterL));

        FrameLayout waterAddImage = waterView.findViewById(R.id.waterAddFrame);
        waterAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetSetup();
                bottomSheetDialog.show();
            }
        });

        View waterCard = waterView.findViewById(R.id.waterCard);
        waterCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetSetup();
                bottomSheetDialog.show();
            }
        });

        return waterView;
    }

    private void drinkAndUpdateData()
    {
        currentDateShowing = Calendar.getInstance();
        currentDateShowing.setTimeInMillis(currentDateShowing.getTimeInMillis() + MainActivity.getPhoneTimezoneOffset());

        Activity act = getActivity();

        TextView totalMl = act.findViewById(R.id.totalMl);
        Calendar calendarOffset = (Calendar) currentDateShowing.clone();
        calendarOffset.set(Calendar.HOUR_OF_DAY, 0);
        calendarOffset.set(Calendar.MINUTE, 0);
        calendarOffset.set(Calendar.SECOND, 0);
        calendarOffset.set(Calendar.MILLISECOND, 0);
        calendarOffset.setTimeInMillis(calendarOffset.getTimeInMillis() - MainActivity.getPhoneTimezoneOffset());
        Calendar nextDay = (Calendar) calendarOffset.clone();
        nextDay.roll(Calendar.DAY_OF_YEAR, 1);
        totalMlCount = database.waterDao().getMlCountForThisDate(calendarOffset.getTimeInMillis(),
                nextDay.getTimeInMillis()).getMl();
        totalMl.setText(mlToLStringBuilder(totalMlCount));

        ProgressBar mlProgress = act.findViewById(R.id.mlProgress);
        mlProgress.setProgress(progressSetupValue(totalMlCount, user.normWaterL));
    }

    private String mlToLStringBuilder(double ml)
    {
        return ml / 1000 + " л";
    }

    private int progressSetupValue(double total, double end)
    {
        total = total == 0.0 ? 1.0 : total;
        int progressValue = (int) (100 / (end / total));
        return Math.min(progressValue, 100);
    }

    private int progressSetupValue(int total, int end)
    {
        total = total == 0 ? 1 : total;
        int progressValue = 100 / (end / total);
        return Math.min(progressValue, 100);
    }

    private void bottomSheetSetup()
    {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.water_bottom_sheet);
        bottomSheetDialog.setDismissWithAnimation(true);

        ButtonWithCustomBackground addWaterBtn = bottomSheetDialog.findViewById(R.id.buttonGo);
        addWaterBtn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackground(getResources().getDrawable(R.drawable.add_water_pressed));
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
                        {
                            final Handler handler = new Handler();
                            new Thread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Calendar cal = Calendar.getInstance();
                                    int ml = Integer.parseInt(waterMlEditText.getText().toString());
                                    WaterEntity water = new WaterEntity(cal.getTimeInMillis(), ml);
                                    List<Long> result = database.waterDao().insert(water);
                                    handler.post(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            drinkAndUpdateData();
                                        }
                                    });
                                    bottomSheetDialog.dismiss();
                                }
                            }).start();
                        }
                        v.setBackground(getResources().getDrawable(R.drawable.water_block_gradient));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setBackground(getResources().getDrawable(R.drawable.water_block_gradient));
                        return true;
                }

                return false;
            }
        });

        waterMlEditText = bottomSheetDialog.findViewById(R.id.set_value);
        waterMlEditText.addTextChangedListener(new WaterPart.TextValueChangedListener());
        FrameLayout plusMl = bottomSheetDialog.findViewById(R.id.plus_layout);
        FrameLayout minusMl = bottomSheetDialog.findViewById(R.id.minus_layout);

        plusMl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int value = Integer.parseInt(waterMlEditText.getText().toString());

                if (value + 1 < 10000)
                {
                    value++;
                    waterMlEditText.setText(String.valueOf(value));
                }
            }
        });

        minusMl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int value = Integer.parseInt(waterMlEditText.getText().toString());

                if (value - 1 > 10)
                {
                    value--;
                    waterMlEditText.setText(String.valueOf(value));
                }
            }
        });

        Calendar calendarOffset = (Calendar) currentDateShowing.clone();
        calendarOffset.set(Calendar.HOUR_OF_DAY, 0);
        calendarOffset.set(Calendar.MINUTE, 0);
        calendarOffset.set(Calendar.SECOND, 0);
        calendarOffset.set(Calendar.MILLISECOND, 0);
        calendarOffset.setTimeInMillis(calendarOffset.getTimeInMillis() - MainActivity.getPhoneTimezoneOffset());
        Calendar nextDay = (Calendar) calendarOffset.clone();
        nextDay.roll(Calendar.DAY_OF_YEAR, 1);
        WaterEntity waterLastData = database.waterDao().getLastWater(calendarOffset.getTimeInMillis(), nextDay.getTimeInMillis());
        TextView lastWaterUpdateTime = bottomSheetDialog.findViewById(R.id.lastWaterUpdateTime);
        TextView lastWaterUpdateCount = bottomSheetDialog.findViewById(R.id.lastWaterUpdateCount);
        TextView totalMl = bottomSheetDialog.findViewById(R.id.totalMl);

        if (waterLastData != null)
        {
            Calendar lastWaterTime = Calendar.getInstance();
            lastWaterTime.setTimeInMillis(waterLastData.date + MainActivity.getPhoneTimezoneOffset());
            String lastWaterTimeString = new SimpleDateFormat("HH:mm", Locale.ROOT).format(lastWaterTime.getTime());
            lastWaterUpdateTime.setText(lastWaterTimeString);

            String lastWaterUpdateCountText = waterLastData.ml + " мл";
            lastWaterUpdateCount.setText(lastWaterUpdateCountText);

            totalMl.setText(mlToLStringBuilder(totalMlCount));
        } else
        {
            lastWaterUpdateTime.setText("Еще не пили сегодня");
            lastWaterUpdateCount.setText("0 мл");
            totalMl.setText(mlToLStringBuilder(totalMlCount));
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
            if (s.length() > 4)
            {
                s.delete(s.length() - 1, s.length());
            }

            if (s.length() == 0)
            {
                int minMl = 10;
                waterMlEditText.setText(String.valueOf(minMl));
            }
        }
    }
}
