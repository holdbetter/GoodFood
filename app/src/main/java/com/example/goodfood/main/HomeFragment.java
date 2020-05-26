package com.example.goodfood.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.goodfood.ButtonWithCustomBackground;
import com.example.goodfood.MainActivity;
import com.example.goodfood.R;
import com.example.goodfood.database.GoodFoodDatabase;
import com.example.goodfood.database.UserEntity;
import com.example.goodfood.database.WaterEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment
{
    private UserEntity user;
    private Calendar currentDateShowing;
    private ProgressBar mlProgress;
    private TextView yesterdayWeightValue;
    private TextView todayWeightValue;
    private TextView desiredWeightValue;
    private GoodFoodDatabase database;
    private BottomSheetDialog bottomSheetDialog;
    private AppCompatEditText waterMlEditText;
    private int totalMlCount;

    public HomeFragment(UserEntity user, GoodFoodDatabase database)
    {
        this.user = user;
        this.database = database;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        currentDateShowing = Calendar.getInstance();
        currentDateShowing.setTimeInMillis(currentDateShowing.getTimeInMillis() + MainActivity.getPhoneTimezoneOffset());

        List<WaterEntity> waterList = database.waterDao().getWaterForThisDate(0, Long.MAX_VALUE);

        View homeView = inflater.inflate(R.layout.main_home_fragment, container, false);

        TextView totalKkal = homeView.findViewById(R.id.totalKkal);
        totalKkal.setText(kkalStringBuilder(162));

        TextView startKkal = homeView.findViewById(R.id.startKkalProgressText);
        startKkal.setText(kkalStringBuilder(0));

        TextView endKKal = homeView.findViewById(R.id.endKkalProgressText);
        endKKal.setText(kkalStringBuilder(user.normFoodKkal));

        ProgressBar kkalProgress = homeView.findViewById(R.id.kkalProgress);
        //should be query
        kkalProgress.setProgress(progressSetupValue(162, user.normFoodKkal));

        TextView belkiCountText = homeView.findViewById(R.id.belkiCountText);
        //should be query
        belkiCountText.setText(String.valueOf(222));

        TextView zhirCountText = homeView.findViewById(R.id.zhirCountText);
        //should be query
        zhirCountText.setText(String.valueOf(4153));

        TextView uglCountText = homeView.findViewById(R.id.uglCountText);
        //should be query
        uglCountText.setText(String.valueOf(245));

        yesterdayWeightValue = homeView.findViewById(R.id.yesterdayWeightValue);
        yesterdayWeightValue.setText(String.valueOf(user.yesterdayWeight));

        todayWeightValue = homeView.findViewById(R.id.todayWeightValue);
        todayWeightValue.setText(String.valueOf(user.weight));

        desiredWeightValue = homeView.findViewById(R.id.tomorrowWeightValue);
        desiredWeightValue.setText(String.valueOf(user.desiredWeight));

        TextView currentDate = homeView.findViewById(R.id.dateHeaderText);
        String dateText = new SimpleDateFormat("dd.MM", Locale.ROOT).format(currentDateShowing.getTime());
        currentDate.setText(dateText);

        TextView totalMl = homeView.findViewById(R.id.totalMl);
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

        TextView startMl = homeView.findViewById(R.id.startMlProgressText);
        startMl.setText(mlToLStringBuilder(0));

        TextView endMl = homeView.findViewById(R.id.endMlProgressText);
        endMl.setText(mlToLStringBuilder(user.normWaterL));

        mlProgress = homeView.findViewById(R.id.mlProgress);
        //should be query
        mlProgress.setProgress(progressSetupValue(totalMlCount, user.normWaterL));

        TextView helloText = homeView.findViewById(R.id.hiTextView);
        helloText.setText("Привет,\n" + user.name);

        FrameLayout yesterdayWeightMinus = homeView.findViewById(R.id.minusBtn1Frame);
        FrameLayout todayWeightMinus = homeView.findViewById(R.id.minusBtn2Frame);
        FrameLayout tomorrowWeightMinus = homeView.findViewById(R.id.minusBtn3Frame);
        yesterdayWeightMinus.setOnClickListener(new MinusOnClickListener());
        todayWeightMinus.setOnClickListener(new MinusOnClickListener());
        tomorrowWeightMinus.setOnClickListener(new MinusOnClickListener());

        FrameLayout yesterdayWeightPlus = homeView.findViewById(R.id.plusBtn1Frame);
        FrameLayout todayWeightPlus = homeView.findViewById(R.id.plusBtn2Frame);
        FrameLayout tomorrowWeightPlus = homeView.findViewById(R.id.plusBtn3Frame);
        yesterdayWeightPlus.setOnClickListener(new PlusOnClickListener());
        todayWeightPlus.setOnClickListener(new PlusOnClickListener());
        tomorrowWeightPlus.setOnClickListener(new PlusOnClickListener());

        FrameLayout waterAddImage = homeView.findViewById(R.id.waterAddFrame);
        waterAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetSetup();
                bottomSheetDialog.show();
            }
        });

        View waterCard = homeView.findViewById(R.id.waterCard);
        waterCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetSetup();
                bottomSheetDialog.show();
            }
        });

        return homeView;
    }

    private void rebindData()
    {
        currentDateShowing = Calendar.getInstance();
        currentDateShowing.setTimeInMillis(currentDateShowing.getTimeInMillis() + MainActivity.getPhoneTimezoneOffset());

        Activity act = getActivity();

        TextView totalKkal = act.findViewById(R.id.totalKkal);
        totalKkal.setText(kkalStringBuilder(162));

        ProgressBar kkalProgress = act.findViewById(R.id.kkalProgress);
        //should be query
        kkalProgress.setProgress(progressSetupValue(162, user.normFoodKkal));

        TextView belkiCountText = act.findViewById(R.id.belkiCountText);
        //should be query
        belkiCountText.setText(String.valueOf(222222222));

        TextView zhirCountText = act.findViewById(R.id.zhirCountText);
        //should be query
        zhirCountText.setText(String.valueOf(4153));

        TextView uglCountText = act.findViewById(R.id.uglCountText);
        //should be query
        uglCountText.setText(String.valueOf(245));

        TextView currentDate = act.findViewById(R.id.dateHeaderText);
        final String dateText = new SimpleDateFormat("dd.MM", Locale.ROOT).format(currentDateShowing.getTime());
        currentDate.setText(dateText);

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

        mlProgress = act.findViewById(R.id.mlProgress);
        mlProgress.setProgress(progressSetupValue(totalMlCount, user.normWaterL));
    }

    private String kkalStringBuilder(int kkal)
    {
        return kkal + " ккал";
    }

    private String mlToLStringBuilder(double ml)
    {
        return ml / 1000 + " л";
    }

    private int progressSetupValue(int total, int end)
    {
        total = total == 0 ? 1 : total;
        int progressValue = 100 / (end / total);
        return Math.min(progressValue, 100);
    }

    private int progressSetupValue(double total, double end)
    {
        total = total == 0.0 ? 1.0 : total;
        int progressValue = (int) (100 / (end / total));
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
                                            rebindData();
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
        waterMlEditText.addTextChangedListener(new TextValueChangedListener());
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

    private class MinusOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getTag().toString())
            {
                case "1":
                    if (user.yesterdayWeight - 0.1 >= 30.0)
                    {
                        BigDecimal yesterdayWeight = new BigDecimal(String.valueOf(user.yesterdayWeight));
                        yesterdayWeight = yesterdayWeight.subtract(new BigDecimal("0.1"));
                        user.yesterdayWeight = yesterdayWeight.doubleValue();
                        yesterdayWeightValue.setText(String.valueOf(user.yesterdayWeight));
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                database.userDao().update(user);
                            }
                        }).start();
                    }
                    return;
                case "2":
                    if (user.weight - 0.1 >= 30.0)
                    {
                        BigDecimal todayWeight = new BigDecimal(String.valueOf(user.weight));
                        todayWeight = todayWeight.subtract(new BigDecimal("0.1"));
                        user.weight = todayWeight.doubleValue();
                        todayWeightValue.setText(String.valueOf(user.weight));
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                database.userDao().update(user);
                            }
                        }).start();
                    }
                    return;
                case "3":
                    if (user.yesterdayWeight - 0.1 >= 30.0)
                    {
                        BigDecimal tomorrowWeight = new BigDecimal(String.valueOf(user.desiredWeight));
                        tomorrowWeight = tomorrowWeight.subtract(new BigDecimal("0.1"));
                        user.desiredWeight = tomorrowWeight.doubleValue();
                        desiredWeightValue.setText(String.valueOf(user.desiredWeight));
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                database.userDao().update(user);
                            }
                        }).start();
                    }
                    return;
                default:
            }
        }
    }

    private class PlusOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getTag().toString())
            {
                case "1":
                    if (user.yesterdayWeight + 0.1 < 140.0)
                    {
                        BigDecimal yesterdayWeight = new BigDecimal(String.valueOf(user.yesterdayWeight));
                        yesterdayWeight = yesterdayWeight.add(new BigDecimal("0.1"));
                        user.yesterdayWeight = yesterdayWeight.doubleValue();
                        yesterdayWeightValue.setText(String.valueOf(user.yesterdayWeight));
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                database.userDao().update(user);
                            }
                        }).start();
                    }
                    return;
                case "2":
                    if (user.weight + 0.1 < 140.0)
                    {
                        BigDecimal todayWeight = new BigDecimal(String.valueOf(user.weight));
                        todayWeight = todayWeight.add(new BigDecimal("0.1"));
                        user.weight = todayWeight.doubleValue();
                        todayWeightValue.setText(String.valueOf(user.weight));
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                database.userDao().update(user);
                            }
                        }).start();
                    }
                    return;
                case "3":
                    if (user.yesterdayWeight + 0.1 < 140.0)
                    {
                        BigDecimal tomorrowWeight = new BigDecimal(String.valueOf(user.desiredWeight));
                        tomorrowWeight = tomorrowWeight.add(new BigDecimal("0.1"));
                        user.desiredWeight = tomorrowWeight.doubleValue();
                        desiredWeightValue.setText(String.valueOf(user.desiredWeight));
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                database.userDao().update(user);
                            }
                        }).start();
                    }
                    return;
                default:
            }
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
