package com.example.goodfood.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class UserDao
{
    @Query("SELECT * FROM users WHERE user_id = 1")
    public abstract UserEntity getUserInfo();
    @Update
    public abstract int update(UserEntity... user);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insert(UserEntity... user);
}
