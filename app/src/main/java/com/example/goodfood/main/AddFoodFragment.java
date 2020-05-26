package com.example.goodfood.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodfood.R;
import com.example.goodfood.database.FoodEntity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class AddFoodFragment extends Fragment
{
    private List<String> foodCategories;
    private List<FoodEntity> foodList;
    private RecyclerView foodRecycler;

    public AddFoodFragment(List<String> foodCategories, List<FoodEntity> foodList)
    {
        this.foodCategories = foodCategories;
        this.foodList = foodList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View addFoodFragment = inflater.inflate(R.layout.add_food_fragment, container, false);

        ChipGroup chipGroup = addFoodFragment.findViewById(R.id.chipsCategoriesGroup);

        initChips(chipGroup, foodCategories);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId)
            {
                if (checkedId == View.NO_ID)
                {
                    FoodRecycleAdapter foodAdapter = new FoodRecycleAdapter(foodList);
                    foodRecycler.setAdapter(foodAdapter);
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                        }
                    }).start();
                    return;
                }


                Chip chip = group.findViewById(checkedId);
                List<FoodEntity> foodByCategoryList = new ArrayList<>();
                for (int i = 0; i < foodList.size(); i++)
                {
                    FoodEntity food = foodList.get(i);
                    if (chip.getText().toString().equals(food.category))
                    {
                        foodByCategoryList.add(food);
                    }
                }

                FoodRecycleAdapter foodAdapter = new FoodRecycleAdapter(foodByCategoryList);
                foodRecycler.setAdapter(foodAdapter);

            }
        });

        foodRecycler = addFoodFragment.findViewById(R.id.foodRecyclerList);
        foodRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FoodRecycleAdapter foodAdapter = new FoodRecycleAdapter(foodList);
        foodRecycler.setAdapter(foodAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(foodRecycler.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_drawable_with_main_solid));
        foodRecycler.addItemDecoration(dividerItemDecoration);

        return addFoodFragment;
    }

    private void initChips(ChipGroup chipGroup, List<String> categories)
    {
        for (String category : categories)
        {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout_filter, chipGroup, false);
            chip.setText(category);
            chipGroup.addView(chip);
        }
    }
}
