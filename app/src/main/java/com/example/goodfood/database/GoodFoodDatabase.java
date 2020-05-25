package com.example.goodfood.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, FoodEntity.class, WaterEntity.class,
        WorkEntity.class, FoodCounterEntity.class}, version = 1, exportSchema = false)
public abstract class GoodFoodDatabase extends RoomDatabase
{
    public abstract UserDao userDao();
    public abstract FoodDao foodDao();
    public abstract WaterDao waterDao();
    public abstract WorkDao workDao();
}