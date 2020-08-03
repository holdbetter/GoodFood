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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MainActivity activity = ((MainActivity) getActivity());
        GoodFoodDatabase database = activity.getDatabase();
        if (activity.getUser() == null)
        {
            activity.setUser(database.userDao().getUserInfo());
        }
        user = activity.getUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // ??
//        List<WaterEntity> waterList = database.waterDao().getWaterForThisDate(0, Long.MAX_VALUE);

        View homeView = inflater.inflate(R.layout.main_home_fragment, container, false);

        TextView helloText = homeView.findViewById(R.id.hiTextView);
        helloText.setText(String.format("Привет,%n%s", user.name));

        return homeView;
    }
}
