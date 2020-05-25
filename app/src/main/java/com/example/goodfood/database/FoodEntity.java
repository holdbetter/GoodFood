package com.example.goodfood.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_info")
public class FoodEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    public long id;
    @ColumnInfo(name = "food_kkal")
    public double kkal;
    @ColumnInfo(name = "food_bel")
    public double bel;
    @ColumnInfo(name = "food_zhir")
    public double zhir;
    @ColumnInfo(name = "food_ugl")
    public double ugl;
    @ColumnInfo(name = "food_name")
    public String name;
    @ColumnInfo(name = "category_name")
    public String category;


    public FoodEntity()
    {
    }

    @Ignore
    public FoodEntity(double kkal, double bel, double zhir, double ugl, String name, String category)
    {
        this.kkal = kkal;
        this.bel = bel;
        this.zhir = zhir;
        this.ugl = ugl;
        this.name = name;
        this.category = category;
    }

    @Ignore
    public FoodEntity(long id, double kkal, double bel, double zhir, double ugl, String name, String category)
    {
        this.id = id;
        this.kkal = kkal;
        this.bel = bel;
        this.zhir = zhir;
        this.ugl = ugl;
        this.name = name;
        this.category = category;
    }
}