package com.example.goodfood.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class WaterDao
{
    @Query("SELECT * FROM water WHERE water_date >= :searchingCleanedDate AND water_date < :nextDate")
    public abstract List<WaterEntity> getWaterForThisDate(long searchingCleanedDate, long nextDate);
    @Query("SELECT SUM(water_ml) as ml FROM water WHERE water_date >= :searchingCleanedDate AND water_date < :nextDate")
    public abstract TotalWater getMlCountForThisDate(long searchingCleanedDate, long nextDate);
    @Query("SELECT * FROM water ORDER BY water_id DESC LIMIT 1")
    public abstract WaterEntity getLastWater();
    @Update
    public abstract int update(WaterEntity... water);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insert(WaterEntity... water);

    public static class TotalWater
    {
        int ml;

        public int getMl()
        {
            return ml;
        }
    }
}
