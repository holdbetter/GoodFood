//package com.example.goodfood.old;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//
//import java.util.GregorianCalendar;
//
//public class DbHelper extends SQLiteOpenHelper
//{
//    private static final int DB_VERSION = 1;
//    private static final String DB_NAME = "GoodFood.db";
//    private Context context;
//    private SQLiteDatabase database;
//
//    public DbHelper(@Nullable Context context)
//    {
//        super(context, DB_NAME, null, DB_VERSION);
//
//        this.context = context;
//
//        database = this.getReadableDatabase();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.P)
//    public DbHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams)
//    {
//        super(context, name, version, openParams);
//    }
//
//    //бд создается
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//        final String SQL_CREATE_TABLE = "CREATE TABLE " + GoodFoodContract.UserEntry.TABLE_NAME + " (" +
//                GoodFoodContract.UserEntry._ID + " INTEGER PRIMARY KEY, " +
//                GoodFoodContract.UserEntry.Name + " TEXT NOT NULL," +
//                GoodFoodContract.UserEntry.Sex + " TEXT NOT NULL," +
//                GoodFoodContract.UserEntry.Birthday + " INTEGER, " +
//                GoodFoodContract.UserEntry.Height + " INTEGER, " +
//                GoodFoodContract.UserEntry.Weight + " INTEGER, " +
//                GoodFoodContract.UserEntry.DesiredWeight + " INTEGER, " +
//                GoodFoodContract.UserEntry.Lifestyle + " TEXT)";
//
//        db.execSQL(SQL_CREATE_TABLE);
//
//        GregorianCalendar gregorianCalendar = new GregorianCalendar();
//        long date = gregorianCalendar.getTimeInMillis();
//
//        User user = new User("Vilen", "male", date, 180, 89, 99, "Амебка");
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(GoodFoodContract.UserEntry.Name, user.name);
//        contentValues.put(GoodFoodContract.UserEntry.Sex, user.sex);
//        contentValues.put(GoodFoodContract.UserEntry.Birthday, user.date);
//        contentValues.put(GoodFoodContract.UserEntry.Height, user.height);
//        contentValues.put(GoodFoodContract.UserEntry.Weight, user.weight);
//        contentValues.put(GoodFoodContract.UserEntry.DesiredWeight, user.desiredWeight);
//        contentValues.put(GoodFoodContract.UserEntry.Lifestyle, user.lifestyle);
//
//        long row_id = db.insert(GoodFoodContract.UserEntry.TABLE_NAME,
//                null,
//                contentValues);
//
//        if (row_id > 0)
//        {
//            Log.i("NO_ERRORS", "ошибок при добавлении не было");
//        }
//
//        //название таблицы
//        //стобцы которые мы хотим получить
//        //условия - null
//        //группировка - null
//        //сортировка - null
//
//        String[] columns = {
//                GoodFoodContract.UserEntry._ID,
//                GoodFoodContract.UserEntry.Name,
//                GoodFoodContract.UserEntry.Sex,
//                GoodFoodContract.UserEntry.Birthday,
//                GoodFoodContract.UserEntry.Height,
//                GoodFoodContract.UserEntry.Weight,
//                GoodFoodContract.UserEntry.DesiredWeight,
//                GoodFoodContract.UserEntry.Lifestyle
//        };
//
//        Cursor cursor = db.query(GoodFoodContract.UserEntry.TABLE_NAME,
//                columns,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        while (cursor.moveToNext())
//        {
//            String name = cursor.getString(cursor.getColumnIndex(GoodFoodContract.UserEntry.Name));
//        }
//
//        cursor.close();
//    }
//
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//
//    }
//}
