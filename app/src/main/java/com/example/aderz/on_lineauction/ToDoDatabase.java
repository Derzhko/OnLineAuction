package com.example.aderz.on_lineauction;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aderz on 13.03.2018.
 */

public class ToDoDatabase extends SQLiteOpenHelper {

    public ToDoDatabase(Context context){
        super(context, "myDB", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("myLogs", "--- onCreate database ---");
        Log.d("myLogs", "--- onCreate database ---");
        db.execSQL("create table if not exists LotsList (" +
                "_id integer primary key autoincrement," +
                "name text," +
                "cost text," +
                "owner integer," +
                "buyer integer"+");");

        db.execSQL("create table if not exists UsersTable ("
                + "_id integer primary key autoincrement,"
                + "name text,"
                + "sername text,"
                + "login text,"
                + "password text,"
                + "email text,"
                + "code integer"+");");

                /*db.execSQL("insert into UsersTable ("
                        + "name ,"
                        + "sername ,"
                        + "login ,"
                        + "password ," +
                        "email," + "code "+") VALUES ('Vasya','Pupkin','vpu','pass','vp@mail.ru',456);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("myLogs", "--- onCreate database ---");
        db.execSQL("create table if not exists LotsList (" +
                "_id integer primary key autoincrement," +
                "name text," +
                "cost text," +
                "owner integer," +
                "buyer integer"+");");

        db.execSQL("create table if not exists UsersTable ("
                + "_id integer primary key autoincrement,"
                + "name text,"
                + "sername text,"
                + "login text,"
                + "password text,"
                + "email text,"
                + "code integer"+");");


  /*      db.execSQL("create table MyLots (" +
                "_id integer primary key autoincrement," +
                "name text," +
                "cost text," +
                "owner integer," +
                "buyer integer"+");");
*/
                db.execSQL("insert into UsersTable ("
                        + "name ,"
            + "sername ,"
            + "login ,"
            + "password ," +
            "email," + "code "+") VALUES ('Vasya','Pupkin','vpu','pass','vp@mail.ru',456);");
}
}
