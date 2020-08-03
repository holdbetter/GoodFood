package com.example.goodfood;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.goodfood.database.FoodDao;
import com.example.goodfood.database.FoodEntity;
import com.example.goodfood.database.GoodFoodDatabase;
import com.example.goodfood.database.UserEntity;
import com.example.goodfood.main.MainPagerFragment;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;

public class MainActivity extends FragmentActivity implements KeyEvent.Callback
{
    private GoodFoodDatabase database;
    public boolean userIsSignedUp = false;
    private static TimeZone phoneTimezone;
    private UserEntity user;

    public static long getPhoneTimezoneOffset()
    {
        return phoneTimezone.getRawOffset();
    }

    public static TimeZone getPhoneTimezone()
    {
        return phoneTimezone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        database = Room.databaseBuilder(getApplicationContext(), GoodFoodDatabase.class, "GoodFoodDb").allowMainThreadQueries().build();
        user = database.userDao().getUserInfo();
        userIsSignedUp = user != null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneTimezone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return editTextClosingHandle();
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (savedInstanceState == null)
        {
            if (userIsSignedUp)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        if (database.foodDao().getFoodInfoCount() < 1)
                        {
                            dataInitialize();
                        }

                        transaction.replace(R.id.main, new MainPagerFragment()).commit();
                    }
                }).start();
            } else
            {
                transaction.replace(R.id.main, new ViewPagerFragment()).commit();
            }
        }
    }

    private boolean editTextClosingHandle()
    {
        AppCompatEditText valueEditText = findViewById(R.id.set_value);
        if (valueEditText != null)
        {
            int value;

            if (valueEditText.length() == 0)
            {
                value = 0;
            } else
            {
                try
                {
                    value = Integer.parseInt(valueEditText.getText().toString());
                } catch (Exception e)
                {
                    value = 0;
                }
            }

            if (valueEditText.getTag() != null && valueEditText.getTag().toString().equals("height"))
            {
                if (value > getResources().getInteger(R.integer.max_height))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.max_height)));
                } else if (value < getResources().getInteger(R.integer.min_height))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.min_height)));
                }
            } else if (valueEditText.getTag() != null && valueEditText.getTag().toString().equals("weight"))
            {
                if (value > getResources().getInteger(R.integer.max_weight))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.max_weight)));
                } else if (value < getResources().getInteger(R.integer.min_weight))
                {
                    valueEditText.setText(String.valueOf(getResources().getInteger(R.integer.min_weight)));
                }
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(valueEditText.getWindowToken(), 0);
            valueEditText.clearFocus();
            return true;
        }

        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER)
        {
            return editTextClosingHandle();
        }

        return false;
    }

    void dataInitialize()
    {
        FoodDao foodDao = database.foodDao();

        FoodEntity milk01 = new FoodEntity(31.0, 2.0, 0.2, 4.8, "Молоко обезжиренное 0.1%", "Молоко");
        FoodEntity milk15 = new FoodEntity(44.0, 2.8, 1.5, 4.7, "Молоко 1.5%", "Молоко");
        FoodEntity milk25 = new FoodEntity(52.0, 2.8, 2.5, 4.7, "Молоко 2.5%", "Молоко");
        foodDao.insertFoodInfo(milk01, milk15, milk25);

        FoodEntity blini = new FoodEntity(233.0, 6.1, 12.3, 26.0, "Блины", "Мучное");
        FoodEntity cpagetti = new FoodEntity(344.0, 10.4, 1.1, 71.5, "Спагетти", "Мучное");
        FoodEntity becon = new FoodEntity(500.0, 23.0, 45.0, 0.0, "Бекон", "Мясо");
        foodDao.insertFoodInfo(blini, cpagetti, becon);

        FoodEntity gfarsh = new FoodEntity(254.0, 17.2, 20.0, 0.0, "Говяжий фарш", "Мясо");
        FoodEntity cfarsh = new FoodEntity(263.0, 17.0, 21.0, 0.0, "Свиной фарш", "Мясо");
        FoodEntity kyrinoefile = new FoodEntity(153, 30.4, 3.5, 0.0, "Куриное филе","Мясо");
        foodDao.insertFoodInfo(gfarsh, cfarsh, kyrinoefile);

        FoodEntity indeika = new FoodEntity(117.0, 20.18, 3.73, 4.8, "Грудка индейки", "Мясо");
        FoodEntity vsvinina = new FoodEntity(375.0, 22.6, 31.6, 4.7, "Вареная свинина", "Мясо");
        FoodEntity zsvinina = new FoodEntity(489.0, 11.4, 49.3, 4.7, "Жареная свинина", "Мясо");
        foodDao.insertFoodInfo(indeika, vsvinina, zsvinina);

        FoodEntity miaitso = new FoodEntity(157.0, 12.7, 11.5, 0.7, "Яйцо сваренное в мешочек", "Яйца");
        FoodEntity vkriaitso = new FoodEntity(160.0, 12.9, 11.6, 0.8, "Яйцо сваренное вкрутую", "Яйца");
        FoodEntity vsmiaitso = new FoodEntity(159.0, 12.8, 11.6, 0.8, "Яйцо сваренное всмятку", "Яйца");
        foodDao.insertFoodInfo(miaitso, vkriaitso, vsmiaitso);

        FoodEntity glazunia = new FoodEntity(117.0, 20.18, 3.73, 4.8, "Яичница глазунья", "Яйца");
        FoodEntity omlet = new FoodEntity(375.0, 22.6, 31.6, 4.7, "Омлет", "Яйца");
        FoodEntity perlovaiavoda = new FoodEntity(135.0, 2.9, 3.5, 22.9, "Каша перловая сваренная на воде", "Каши");
        foodDao.insertFoodInfo(glazunia, omlet, perlovaiavoda);

        FoodEntity iachnevaiavoda = new FoodEntity(96.0, 2.1, 2.9, 15.3, "Каша ячневая сваренная на воде", "Каши");
        FoodEntity risovaiavoda = new FoodEntity(144.0, 2.4, 3.5, 25.8, "Каша рисовая сваренная на воде", "Каши");
        FoodEntity ris = new FoodEntity(116.0, 2.2, 0.5, 24.9, "Рис белый вареный", "Каши");
        foodDao.insertFoodInfo(iachnevaiavoda, risovaiavoda, ris);

        FoodEntity mannaiamoloko = new FoodEntity(98.0, 3.0, 3.2, 15.3, "Каша манная сваренная на молоке", "Каши");
        FoodEntity mannaiavoda = new FoodEntity(80.0, 2.5, 0.2, 16.8, "Каша манная сваренная на воде", "Каши");
        FoodEntity grechnevaiavoda = new FoodEntity(90.0, 3.2, 0.8, 17.1, "Каша гречневая сваренная на воде", "Каши");
        foodDao.insertFoodInfo(mannaiamoloko, mannaiavoda, grechnevaiavoda);

        FoodEntity grechnevaiamoloko = new FoodEntity(98.0, 3.0, 3.2, 15.3, "Каша гречневая сваренная на молоке", "Каши");
        FoodEntity ovsianaiavoda = new FoodEntity(80.0, 2.5, 0.2, 16.8, "Каша овсяная сваренная на воде", "Каши");
        FoodEntity ovsianaiamoloko = new FoodEntity(90.0, 3.2, 0.8, 17.1, "Каша овсяная сваренная на молоке", "Каши");
        foodDao.insertFoodInfo(grechnevaiamoloko, ovsianaiamoloko, ovsianaiavoda);

        FoodEntity sveklav = new FoodEntity(98.0, 3.0, 3.2, 15.3, "Вареная свёкла", "Овощи");
        FoodEntity tikva = new FoodEntity(28.0, 1.3, 0.3, 7.7, "Тыква", "Овощи");
        FoodEntity kartofel = new FoodEntity(76.0, 2.0, 0.4, 16.1, "Картофель", "Овощи");
        foodDao.insertFoodInfo(sveklav, tikva, kartofel);

        FoodEntity ogurets = new FoodEntity(15.0, 0.8, 0.1, 2.8, "Огурец", "Овощи");
        FoodEntity morkov = new FoodEntity(32.0, 1.3, 0.1, 6.9, "Морковь", "Овощи");
        FoodEntity morkovvar = new FoodEntity(25.0, 0.8, 0.3, 5.0, "Морковь варёная", "Овощи");
        foodDao.insertFoodInfo(ogurets, morkov, morkovvar);

        FoodEntity lykzel = new FoodEntity(41.0, 1.4, 0.2, 8.2, "Лук зелёный", "Овощи");
        FoodEntity lykrep = new FoodEntity(47.0, 1.4, 0.0, 7.7, "Лук репчатый", "Овощи");
        FoodEntity lykbel = new FoodEntity(42.0, 1.4, 0.2, 10.4, "Лук белый", "Овощи");
        foodDao.insertFoodInfo(lykbel, lykrep, lykzel);

        FoodEntity kykyryzavar = new FoodEntity(123.0, 4.1, 2.3, 22.5, "Варёная кукуруза", "Овощи");
        FoodEntity avokado = new FoodEntity(212.0, 2.0, 20.0, 6.0, "Авокадо", "Фрукты");
        FoodEntity banan = new FoodEntity(95.0, 1.5, 0.2, 21.8, "Банан", "Фрукты");
        foodDao.insertFoodInfo(kykyryzavar, avokado, banan);

        FoodEntity vishnia = new FoodEntity(52.0, 0.8, 0.5, 11.3, "Вишня", "Фрукты");
        FoodEntity mango = new FoodEntity(67.0, 0.5, 0.3, 11.5, "Манго", "Фрукты");
        FoodEntity arbuz = new FoodEntity(25.0, 0.6, 0.1, 5.8, "Арбуз", "Фрукты");
        foodDao.insertFoodInfo(vishnia, mango, arbuz);

        FoodEntity niktarin = new FoodEntity(48.0, 0.9, 0.2, 11.8, "Никтарин", "Фрукты");
        FoodEntity persik = new FoodEntity(46.0, 0.9, 0.1, 11.3, "Персик", "Фрукты");
        FoodEntity mandarin = new FoodEntity(33.0, 0.8, 0.2, 7.5, "Мандарин", "Фрукты");
        foodDao.insertFoodInfo(niktarin, persik, mandarin);

        FoodEntity kivi = new FoodEntity(48.0, 1.0, 0.6, 10.3, "Киви", "Фрукты");
        FoodEntity limon = new FoodEntity(16.0, 0.9, 3.0, 11.3, "Лимон", "Фрукты");
        FoodEntity ananas = new FoodEntity(49.0, 0.4, 0.2, 10.6, "Ананас", "Фрукты");
        foodDao.insertFoodInfo(kivi, limon, ananas);

        FoodEntity apelsin = new FoodEntity(36.0, 0.9, 0.2, 8.1, "Никтарин", "Фрукты");
        FoodEntity granat = new FoodEntity(52.0, 0.9, 0.0, 13.9, "Персик", "Фрукты");
        FoodEntity greipfrut = new FoodEntity(29.0, 0.7, 0.2, 6.5, "Мандарин", "Фрукты");
        foodDao.insertFoodInfo(apelsin, granat, greipfrut);

        FoodEntity sliva = new FoodEntity(42.0, 0.8, 0.3, 9.6, "Слива", "Фрукты");
        FoodEntity hurma = new FoodEntity(67.0, 0.5, 0.4, 15.3, "Хурма", "Фрукты");
        FoodEntity chereshnia = new FoodEntity(50.0, 1.1, 0.4, 11.5, "Черешня", "Фрукты");
        FoodEntity iabloko = new FoodEntity(47.0, 0.4, 0.4, 9.8, "Яблоко", "Фрукты");
        foodDao.insertFoodInfo(sliva, hurma, iabloko, chereshnia);
    }

    @Override
    public Resources.Theme getTheme()
    {
        Resources.Theme theme = super.getTheme();
        if (userIsSignedUp)
        {
            theme.applyStyle(R.style.MainTheme, true);
        } else
        {
            theme.applyStyle(R.style.RegistrationTheme, true);
        }
        return theme;
    }

    public GoodFoodDatabase getDatabase()
    {
        return database;
    }

    public UserEntity getUser()
    {
        return user;
    }

    public void setUser(UserEntity userInfo)
    {
        this.user = userInfo;
    }
}
