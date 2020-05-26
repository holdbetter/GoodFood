package com.example.goodfood.database;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class FoodDao
{
    @Query("SELECT * FROM food_info ORDER BY food_name")
    public abstract List<FoodEntity> getFoodInfoList();
    @Query("SELECT COUNT(*) FROM food_info")
    public abstract int getFoodInfoCount();
    @Query("SELECT DISTINCT category_name FROM food_info ORDER BY category_name")
    public abstract List<String> getFoodCategories();
    @Update
    public abstract int updateFoodInfo(FoodEntity... foodInfo);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertFoodInfo(FoodEntity... foodInfo);

    @Insert
    abstract void insertFoodCount(FoodCounterEntity... foodCount);
    @Update
    abstract void updateFoodCount(FoodCounterEntity... foodCount);

    @Query("SELECT * FROM food_counter WHERE eating_date >= :searchingCleanedDate AND eating_date < :endDate")
    public abstract List<FoodCounterEntity> getAllFoodForThisDate(long searchingCleanedDate, long endDate);

    @Query("SELECT * FROM food_counter WHERE eating_date >= :searchingCleanedDate " +
            "AND eating_date < :endDate " +
            "AND complex_id = :complexId")
    public abstract List<FoodCounterEntity> getAllFoodForThisDateWithComplexId(long searchingCleanedDate,
                                                                               long endDate, int complexId);

    @Query("SELECT SUM(food_bel) as bel, " +
            "SUM(food_zhir) as zhir, " +
            "SUM(food_ugl) as ugl, " +
            "SUM(food_kkal) as kkal FROM FOOD_COUNTER " +
            "WHERE eating_date >= :searchingCleanedDate " +
            "AND eating_date < :endDate ")
    public abstract FoodSum getSumForThisDate(long searchingCleanedDate,
                                              long endDate);

    public ComplexFood getComplexData(long searchingCleanedDate,
                                  long endDate, int complexId)
    {
        ComplexFood complexFood = new ComplexFood();

        List<FoodCounterEntity> foodList = getAllFoodForThisDateWithComplexId(searchingCleanedDate, endDate, complexId);

        for (FoodCounterEntity foodCounterEntity : foodList)
        {
            complexFood.bel += foodCounterEntity.food.bel;
            complexFood.zhir += foodCounterEntity.food.zhir;
            complexFood.ugl += foodCounterEntity.food.ugl;
            complexFood.kkal += foodCounterEntity.food.kkal;
        }

        return complexFood;
    }

    static class FoodSum
    {
        public int kkal;
        public int bel;
        public int zhir;
        public int ugl;
    }
}
