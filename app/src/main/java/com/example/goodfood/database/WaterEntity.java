package com.example.goodfood.database;

import androidx.annotation.ColorLong;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "water")
public class WaterEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "water_id")
    public long id;
    @ColumnInfo(name = "water_date")
    public long date;
    @ColumnInfo(name = "water_ml")
    public int ml;

    public WaterEntity()
    {
    }

    @Ignore
    public WaterEntity(long id, long date, int ml)
    {
        this.id = id;
        this.date = date;
        this.ml = ml;
    }

    @Ignore
    public WaterEntity(long date, int ml)
    {
        this.date = date;
        this.ml = ml;
    }
}
