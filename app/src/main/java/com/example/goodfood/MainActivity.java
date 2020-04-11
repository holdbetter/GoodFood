package com.example.goodfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements KeyEvent.Callback
{
    static ArrayList<Fragment> fragmentArrayList;

    static {
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HelloFragment());
        fragmentArrayList.add(new NameFragment());
        fragmentArrayList.add(new SexFragment());
        fragmentArrayList.add(new HeightFragment());
        fragmentArrayList.add(new WeightFragment());
        fragmentArrayList.add(new DesiredWeightFragment());
        fragmentArrayList.add(new LifestyleFragment());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        EditText weightEditText = findViewById(R.id.set_weight);

        if (weightEditText != null)
        {
            switch (keyCode)
            {
                case KeyEvent.KEYCODE_ENTER:
                    int w;

                    if (weightEditText.length() == 0)
                    {
                        w = 0;
                    } else
                    {
                        w = Integer.parseInt(weightEditText.getText().toString());
                    }

                    if (w > 200)
                    {
                        weightEditText.setText("200");
                    } else if (w < 20)
                    {
                        weightEditText.setText("30");
                    }
                    return true;
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        YuliasAdapter yuliasAdapter = new YuliasAdapter(this,fragmentArrayList);
        viewPager2.setAdapter(yuliasAdapter);

        //       Button button = findViewById(R.id.btn);

//        FragmentManager fr = getSupportFragmentManager();
//        FragmentTransaction transaction = fr.beginTransaction();
//        transaction.replace(R.id.aaaaa, new YuliaCreatesFragment()).commit();
//        View.OnClickListener click = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fr = getSupportFragmentManager();
//                FragmentTransaction transaction = fr.beginTransaction();
//
//                transaction.replace(R.id.aaaaa, new EsheOdinFragment());
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        };
//        button.setOnClickListener(click);
    }
}
