package com.example.goodfood.main;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodfood.ButtonWithCustomBackground;
import com.example.goodfood.R;
import com.example.goodfood.database.FoodEntity;
import com.example.goodfood.database.WaterEntity;

import java.util.Calendar;
import java.util.List;

public class FoodRecycleAdapter extends RecyclerView.Adapter<FoodRecycleAdapter.FoodItemViewHolder>
{
    private List<FoodEntity> foodEntities;


    public FoodRecycleAdapter(List<FoodEntity> foodEntities)
    {
        this.foodEntities = foodEntities;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View foodItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_recycle_instance_constraint, parent, false);
        return new FoodItemViewHolder(foodItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position)
    {
        FoodEntity foodEntity = foodEntities.get(position);

        if (holder.foodName.getText().length() < 1)
        {
            String foodName = foodEntity.name;
            String foodKKal = String.valueOf(foodEntity.kkal);
            String foodBel = String.valueOf(foodEntity.bel);
            String foodZhir = String.valueOf(foodEntity.ugl);
            String foodUgl = String.valueOf(foodEntity.ugl);

            holder.foodName.setText(foodName);
            holder.foodKKalCount.setText(foodKKal);
            holder.foodBelCount.setText(foodBel);
            holder.foodZhirCount.setText(foodZhir);
            holder.foodUglCount.setText(foodUgl);
        }
    }

    @Override
    public int getItemCount()
    {
        return foodEntities.size();
    }

    static class FoodItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView foodName;
        TextView foodKKalCount;
        TextView foodBelCount;
        TextView foodZhirCount;
        TextView foodUglCount;

        public FoodItemViewHolder(@NonNull View itemView)
        {
            super(itemView);

            foodName = itemView.findViewById(R.id.nameFoodText);
            foodKKalCount = itemView.findViewById(R.id.kkalCountText);
            foodBelCount = itemView.findViewById(R.id.belkiCountText);
            foodZhirCount = itemView.findViewById(R.id.zhirCountText);
            foodUglCount = itemView.findViewById(R.id.uglCountText);

            ButtonWithCustomBackground customBackground = itemView.findViewById(R.id.buttonGo);
            customBackground.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            v.setBackground(v.getResources().getDrawable(R.drawable.add_food_button_pressed));
                            return true;
                        case MotionEvent.ACTION_UP:
                            v.setBackground(v.getResources().getDrawable(R.drawable.eda_block_gradient));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            v.setBackground(v.getResources().getDrawable(R.drawable.eda_block_gradient));
                            return true;
                    }

                    return false;
                }
            });
        }
    }
}
