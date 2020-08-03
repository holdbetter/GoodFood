package com.example.goodfood.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.goodfood.MainActivity;
import com.example.goodfood.R;
import com.example.goodfood.database.GoodFoodDatabase;
import com.example.goodfood.database.UserEntity;

public class EatPart extends Fragment
{
    private UserEntity user;
    private GoodFoodDatabase database;

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
        View eatView = inflater.inflate(R.layout.eat_part, container, false);
        TextView totalKkal = eatView.findViewById(R.id.totalKkal);
        totalKkal.setText(kkalStringBuilder(162));

        TextView startKkal = eatView.findViewById(R.id.startKkalProgressText);
        startKkal.setText(kkalStringBuilder(0));

        TextView endKKal = eatView.findViewById(R.id.endKkalProgressText);
        endKKal.setText(kkalStringBuilder(user.normFoodKkal));

        ProgressBar kkalProgress = eatView.findViewById(R.id.kkalProgress);
        //should be query
        kkalProgress.setProgress(progressSetupValue(162, user.normFoodKkal));

        TextView belkiCountText = eatView.findViewById(R.id.belkiCountText);
        //should be query
        belkiCountText.setText(String.valueOf(222));

        TextView zhirCountText = eatView.findViewById(R.id.zhirCountText);
        //should be query
        zhirCountText.setText(String.valueOf(4153));

        TextView uglCountText = eatView.findViewById(R.id.uglCountText);
        //should be query
        uglCountText.setText(String.valueOf(245));
        return eatView;
    }

    private String kkalStringBuilder(int kkal)
    {
        return kkal + " ккал";
    }

    private int progressSetupValue(int total, int end)
    {
        total = total == 0 ? 1 : total;
        int progressValue = 100 / (end / total);
        return Math.min(progressValue, 100);
    }
}
