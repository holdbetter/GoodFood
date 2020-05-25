package com.example.goodfood.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_counter")
class FoodCounterEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_count_id")
    public long id;
    @ColumnInfo(name = "eating_date")
    public long date;
    public int count;
    @Embedded
    public FoodEntity food;
    @ColumnInfo(name = "complex_id")
    public long complexId;
}
