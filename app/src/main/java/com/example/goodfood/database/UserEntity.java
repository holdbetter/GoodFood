package com.example.goodfood.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long id;
    @ColumnInfo(name = "user_name")
    public String name;
    @ColumnInfo(name = "user_sex")
    public String sex;
    @ColumnInfo(name = "user_date")
    public long date;
    @ColumnInfo(name = "user_height")
    public int height;
    @ColumnInfo(name = "user_weight")
    public double weight;
    @ColumnInfo(name = "user_yesterday_weight")
    public double yesterdayWeight;
    @ColumnInfo(name = "user_desired_weight")
    public double desiredWeight;
    @ColumnInfo(name = "user_lifestyle")
    public double lifestyle;
    @ColumnInfo(name = "index_of_mass")
    public double IMT;
    @ColumnInfo(name = "norm_water_ml")
    public double normWaterL;
    @ColumnInfo(name = "norm_food_kkal")
    public int normFoodKkal;

    public UserEntity()
    {

    }

    public UserEntity(long id, String name, String sex, long date, int height, double weight, double yesterdayWeight, double desiredWeight, double lifestyle, double IMT)
    {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.yesterdayWeight = yesterdayWeight;
        this.desiredWeight = desiredWeight;
        this.lifestyle = lifestyle;
        this.IMT = IMT;
    }

    public UserEntity(String name, String sex, long date, int height, double weight, double yesterdayWeight, double desiredWeight, double lifestyle, double IMT)
    {
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.yesterdayWeight = yesterdayWeight;
        this.desiredWeight = desiredWeight;
        this.lifestyle = lifestyle;
        this.IMT = IMT;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeLong(date);
        dest.writeInt(height);
        dest.writeDouble(weight);
        dest.writeDouble(yesterdayWeight);
        dest.writeDouble(desiredWeight);
        dest.writeDouble(lifestyle);
        dest.writeDouble(IMT);
    }

    public static Parcelable.Creator<UserEntity> CREATOR = new Creator<UserEntity>()
    {
        @Override
        public UserEntity createFromParcel(Parcel source)
        {
            long id = source.readLong();
            String name = source.readString();
            String sex = source.readString();
            long date = source.readLong();
            int height = source.readInt();
            double weight = source.readDouble();
            double yesterdayWeight = source.readDouble();
            double desiredWeight = source.readDouble();
            double lifestyle = source.readDouble();
            double IMT = source.readDouble();

            return new UserEntity(id, name, sex, date, height, weight, yesterdayWeight,
                    desiredWeight, lifestyle, IMT);
        }

        @Override
        public UserEntity[] newArray(int size)
        {
            return new UserEntity[size];
        }
    };
}
