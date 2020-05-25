package com.example.goodfood;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goodfood.database.UserEntity;
import com.example.goodfood.main.MainPagerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PollEndFragment extends Fragment
{
    private LinearLayout checkName;
    private LinearLayout checkSex;
    private LinearLayout checkDate;
    private LinearLayout checkHeight;
    private LinearLayout checkWeight;
    private LinearLayout checkDesiredWeight;
    private LinearLayout checkLifestyle;
    private AppCompatImageView nameReadyImage;
    private AppCompatImageView sexReadyImage;
    private AppCompatImageView dateReadyImage;
    private AppCompatImageView heightReadyImage;
    private AppCompatImageView weightReadyImage;
    private AppCompatImageView desiredWeightReadyImage;
    private AppCompatImageView lifestyleReadyImage;
    private TextView nameCheckText;
    private TextView sexCheckText;
    private TextView dateCheckText;
    private TextView heightCheckText;
    private TextView weightCheckText;
    private TextView desiredWeightCheckText;
    private TextView lifestyleCheckText;
    private List<AppCompatImageView> images = new ArrayList<>();
    private List<LinearLayout> linears = new ArrayList<>();
    private int positionCheck;
    private NestedScrollView scrollView;
    private Handler handler;
    private Runnable task;
    private ViewPager2 vp2;
    private UserEntity user;

    public PollEndFragment(UserEntity user)
    {
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        handler = new Handler();
        View pollEndFragment = inflater.inflate(R.layout.poll_end_fragment, container, false);

        vp2 = getActivity().findViewById(R.id.viewPager);
        ButtonWithCustomBackground nextBtn = pollEndFragment.findViewById(R.id.buttonGo);
        nextBtn.setOnTouchListener(new GoNextButton(vp2));

        linears.clear();
        images.clear();
        positionCheck = 0;

        scrollView = pollEndFragment.findViewById(R.id.scrollView);

        checkName = pollEndFragment.findViewById(R.id.check_name);
        checkSex = pollEndFragment.findViewById(R.id.check_sex);
        checkDate = pollEndFragment.findViewById(R.id.check_date);
        checkHeight = pollEndFragment.findViewById(R.id.check_height);
        checkWeight = pollEndFragment.findViewById(R.id.check_weight);
        checkDesiredWeight = pollEndFragment.findViewById(R.id.check_desired_weight);
        checkLifestyle = pollEndFragment.findViewById(R.id.check_lifestyle);

        linears.add(checkName);
        linears.add(checkSex);
        linears.add(checkDate);
        linears.add(checkHeight);
        linears.add(checkWeight);
        linears.add(checkDesiredWeight);
        linears.add(checkLifestyle);

        nameReadyImage = pollEndFragment.findViewById(R.id.name_checked_image);
        sexReadyImage = pollEndFragment.findViewById(R.id.sex_checked_image);
        dateReadyImage = pollEndFragment.findViewById(R.id.date_checked_image);
        heightReadyImage = pollEndFragment.findViewById(R.id.height_checked_image);
        weightReadyImage = pollEndFragment.findViewById(R.id.weight_checked_image);
        desiredWeightReadyImage = pollEndFragment.findViewById(R.id.desired_weight_checked_image);
        lifestyleReadyImage = pollEndFragment.findViewById(R.id.lifecycle_checked_image);

        images.add(nameReadyImage);
        images.add(sexReadyImage);
        images.add(dateReadyImage);
        images.add(heightReadyImage);
        images.add(weightReadyImage);
        images.add(desiredWeightReadyImage);
        images.add(lifestyleReadyImage);

        nameCheckText = pollEndFragment.findViewById(R.id.name_check_text);
        sexCheckText = pollEndFragment.findViewById(R.id.sex_check_text);
        dateCheckText = pollEndFragment.findViewById(R.id.date_check_text);
        heightCheckText = pollEndFragment.findViewById(R.id.height_check_text);
        weightCheckText = pollEndFragment.findViewById(R.id.weight_check_text);
        desiredWeightCheckText = pollEndFragment.findViewById(R.id.desired_weight_check_text);
        lifestyleCheckText = pollEndFragment.findViewById(R.id.lifestyle_check_text);

        for (AppCompatImageView image : images)
        {
            image.setVisibility(View.GONE);
        }

        for (LinearLayout linear : linears)
        {
            linear.setVisibility(View.GONE);
        }

        linears.get(0).setVisibility(View.VISIBLE);

        return pollEndFragment;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        YuliasAdapter adapter = (YuliasAdapter) vp2.getAdapter();
        List<Fragment> fragmentList = adapter.getFragmentList();

        NameFragment nameFragment = (NameFragment) fragmentList.get(1);
        user.name = nameFragment.getNameValue();
        nameCheckText.setText(user.name);

        SexFragment sexFragment = (SexFragment) fragmentList.get(2);
        user.sex = sexFragment.getSexValue();
        sexCheckText.setText(user.sex);

        DateFragment dateFragment = (DateFragment) adapter.getFragmentList().get(3);
        user.date = dateFragment.getDateInMillis();
        String time = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(new Date(user.date));
        dateCheckText.setText(time);

        HeightFragment heightFragment = (HeightFragment) adapter.getFragmentList().get(4);
        user.height = heightFragment.getHeightValue();
        heightCheckText.setText(String.valueOf(user.height));

        WeightFragment weightFragment = (WeightFragment) adapter.getFragmentList().get(5);
        user.weight = weightFragment.getWeightValue();
        weightCheckText.setText(String.valueOf(user.weight));

        DesiredWeightFragment desiredWeightFragment = (DesiredWeightFragment) adapter.getFragmentList().get(6);
        user.desiredWeight = desiredWeightFragment.getWeightValue();
        desiredWeightCheckText.setText(String.valueOf(user.desiredWeight));

        LifestyleFragment lifestyleFragment = (LifestyleFragment) adapter.getFragmentList().get(7);
        user.lifestyle = lifestyleFragment.getLifestyleValue();
        lifestyleCheckText.setText(lifestyleFragment.getLifestyleString());
    }

    @Override
    public void onPause()
    {
        super.onPause();

        checkRestart();
    }

    private void checkRestart()
    {
        positionCheck = 0;

        for (AppCompatImageView image : images)
        {
            image.setVisibility(View.GONE);
        }

        for (LinearLayout linear : linears)
        {
            linear.setVisibility(View.GONE);
        }

        linears.get(0).setVisibility(View.VISIBLE);
    }

    private boolean checkNext()
    {
        for (AppCompatImageView image : images)
        {
            if (image.getVisibility() == View.GONE)
            {
                return false;
            }
        }

        return true;
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
            Button btn = (Button) v;
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_pressed));
                    return true;
                case MotionEvent.ACTION_UP:

                    Log.i("CHECK_INFO", "ID текущего элемента: " + vp2.getCurrentItem());
                    Log.i("CHECK_INFO", "ID cледующего элемента: " + (vp2.getCurrentItem() + 1));
                    Log.i("CHECK_INFO", "всего элементов: " + vp2.getAdapter().getItemCount());

                    if (checkNext())
                    {
                        registerUser(user);
                        v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                    } else
                    {
                        images.get(positionCheck).setVisibility(View.VISIBLE);

                        if (positionCheck + 1 < linears.size())
                        {
                            task = new Runnable()
                            {
                                int positionSafety = positionCheck + 1;

                                @Override
                                public void run()
                                {
                                    LinearLayout linear = linears.get(positionSafety);
                                    linear.setVisibility(View.VISIBLE);
                                }
                            };

                            handler.postDelayed(task, 200);
                        } else
                        {
                            btn.setText("УСПЕХ");
                        }

                        v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                        positionCheck++;
                    }

                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setBackground(getResources().getDrawable(R.drawable.next_button_def));
                    return true;
            }

            return false;
        }
    }

    private void registerUser(final UserEntity user)
    {
        final MainActivity activity = (MainActivity) getActivity();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                user.IMT = user.weight / (Math.pow(user.height / 100.0, 2));
                user.normWaterL = user.sex.equals("Мужской") ?
                        35 * user.weight : 31 * user.weight;

                user.normWaterL = Math.round(user.normWaterL);

                Calendar yearSolve = Calendar.getInstance();
                yearSolve.clear();
                yearSolve.setTimeInMillis(user.date);
                int year = Calendar.getInstance().get(Calendar.YEAR) - yearSolve.get(Calendar.YEAR);

                double normFoodKkal = user.sex.equals("Мужской") ?
                        (10 * user.weight) + (6.25 * user.height) - (5.0 * year) + 5 :
                        (10 * user.weight) + (6.25 * user.height) - (5.0 * year) - 161;

                user.normFoodKkal = (int) (normFoodKkal * user.lifestyle);
                user.yesterdayWeight = user.weight;

                List<Long> result = activity.database.userDao().insert(user);

                if (result.size() > 0)
                {
                    activity.userIsSignedUp = true;
                }

                if (activity.database.foodDao().getFoodInfoCount() < 1)
                {
                    activity.dataInitialize();
                }

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, new MainPagerFragment()).commit();

                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        changeStatusBarColor(activity);
                    }
                });
            }
        }).start();
    }

    private void changeStatusBarColor(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
