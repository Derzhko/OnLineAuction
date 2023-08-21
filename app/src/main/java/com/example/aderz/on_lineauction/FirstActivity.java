package com.example.aderz.on_lineauction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FirstActivity extends FragmentActivity {
    public ViewPager pager;
    PagerAdapter pagerAdapter;
    ToDoDatabase database;
    SQLiteDatabase db;
    Cursor c;
    Intent intent;
    String[] title = {"Мои лоты", "Активные лоты", "Добавить свой лот"};
    int pageCount = 3;
    final String TAG = "myLogs";
    TextView tvName, tvSername, tvCode;
    public static int id;
    public static int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        intent = getIntent();
        id = intent.getIntExtra("_id", 0);
        database = new ToDoDatabase(this);
        db = database.getWritableDatabase();
        tvName = findViewById(R.id.name);
        tvSername = findViewById(R.id.sername);
        tvCode = findViewById(R.id.code);
        c = db.query("UsersTable", null, null, null, null, null, null);
        if(c.moveToFirst()){
            do{
                Log.e("myLogs", "c.getInt():  "+c.getInt(c.getColumnIndex("_id")));
                Log.e("myLogs", "intent.getIntExtra(\"_id\", 0):  "+intent.getIntExtra("_id", 0));
                if(c.getInt(c.getColumnIndex("_id")) == intent.getIntExtra("_id", 0)){
                    tvName.setText(c.getString(c.getColumnIndex("name")));
                    tvSername.setText(c.getString(c.getColumnIndex("sername")));
                    code = c.getInt(c.getColumnIndex("code"));
                    tvCode.setText(String.valueOf(code));
                }
            }while(c.moveToNext());
        }


        pager = findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);
        Log.d(TAG, " Activity is onCreate");
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                PageFragment pageFragment = new PageFragment();
                Log.d(TAG, String.valueOf(pageFragment.arrList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }



    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return pageCount;
        }
    }
}
