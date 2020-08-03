package com.example.goodfood.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.goodfood.MainActivity;
import com.example.goodfood.R;
import com.example.goodfood.database.GoodFoodDatabase;
import com.example.goodfood.database.UserEntity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class YourWeightPart extends Fragment
{
    private GoodFoodDatabase database;
    private UserEntity user;
    private TextView yesterdayWeightValue;
    private TextView todayWeightValue;
    private TextView desiredWeightValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        MainActivity activity = ((MainActivity) getActivity());
        database = activity.getDatabase();
        user = activity.getUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View yourWeightView = inflater.inflate(R.layout.your_weight_part,container,false);
        Calendar currentDateShowing = Calendar.getInstance();
        currentDateShowing.setTimeInMillis(currentDateShowing.getTimeInMillis() + MainActivity.getPhoneTimezoneOffset());

        yesterdayWeightValue = yourWeightView.findViewById(R.id.yesterdayWeightValue);
        yesterdayWeightValue.setText(String.valueOf(user.yesterdayWeight));

        todayWeightValue = yourWeightView.findViewById(R.id.todayWeightValue);
        todayWeightValue.setText(String.valueOf(user.weight));

        desiredWeightValue = yourWeightView.findViewById(R.id.tomorrowWeightValue);
        desiredWeightValue.setText(String.valueOf(user.desiredWeight));

        TextView currentDate = yourWeightView.findViewById(R.id.dateHeaderText);
        String dateText = new SimpleDateFormat("dd.MM", Locale.ROOT).format(currentDateShowing.getTime());
        currentDate.setText(dateText);

        FrameLayout yesterdayWeightMinus = yourWeightView.findViewById(R.id.minusBtn1Frame);
        FrameLayout todayWeightMinus = yourWeightView.findViewById(R.id.minusBtn2Frame);
        FrameLayout tomorrowWeightMinus = yourWeightView.findViewById(R.id.minusBtn3Frame);
        yesterdayWeightMinus.setOnClickListener(new YourWeightPart.MinusOnClickListener());
        todayWeightMinus.setOnClickListener(new YourWeightPart.MinusOnClickListener());
        tomorrowWeightMinus.setOnClickListener(new YourWeightPart.MinusOnClickListener());

        FrameLayout yesterdayWeightPlus = yourWeightView.findViewById(R.id.plusBtn1Frame);
        FrameLayout todayWeightPlus = yourWeightView.findViewById(R.id.plusBtn2Frame);
        FrameLayout tomorrowWeightPlus = yourWeightView.findViewById(R.id.plusBtn3Frame);
        yesterdayWeightPlus.setOnClickListener(new YourWeightPart.PlusOnClickListener());
        todayWeightPlus.setOnClickListener(new YourWeightPart.PlusOnClickListener());
        tomorrowWeightPlus.setOnClickListener(new YourWeightPart.PlusOnClickListener());

        return yourWeightView;
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
}
