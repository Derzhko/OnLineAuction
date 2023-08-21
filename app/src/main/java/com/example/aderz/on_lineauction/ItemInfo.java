package com.example.aderz.on_lineauction;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemInfo extends Activity implements View.OnClickListener {
    TextView name, sername, code, tvName, tvCost, tvOwner, tvCode, tvEmail;
    Button btnGet;
    Cursor c, cl, c1;
    ToDoDatabase database;
    SQLiteDatabase db;
    ContentValues cv;
    Intent intent;
    int id, idl, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        name = findViewById(R.id.name);
        sername = findViewById(R.id.sername);
        code = findViewById(R.id.code);
        tvName = findViewById(R.id.tvName);
        tvCost = findViewById(R.id.tvCost);
        tvOwner = findViewById(R.id.tvOwner);
        tvCode = findViewById(R.id.tvCode);
        tvEmail = findViewById(R.id.tvEmail);
        btnGet = findViewById(R.id.btnGet);
        btnGet.setOnClickListener(this);
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        idl = intent.getIntExtra("idl", 0);
        database = new ToDoDatabase(this);
        db = database.getWritableDatabase();
        c = db.query("UsersTable", null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (c.moveToFirst()) {
            do {
                idUser = c.getInt(c.getColumnIndex("_id"));
                if (idUser != id) {
                    c.moveToNext();
                }
                else
                    break;
            } while (true);
        }
        cl = db.query("LotsList", null, "_id = '" + String.valueOf(idl) + "'", null, null, null, null);
        cl.moveToFirst();
        Log.e("myLogs", "c.getInt():  " + c.getInt(c.getColumnIndex("_id")));
        Log.e("myLogs", "intent.getIntExtra(\"_id\", 0):  " + intent.getIntExtra("_id", 0));
        if (c.getInt(c.getColumnIndex("_id")) == id) {
            name.setText(c.getString(c.getColumnIndex("name")));
            sername.setText(c.getString(c.getColumnIndex("sername")));
            code.setText(String.valueOf(c.getInt(c.getColumnIndex("code"))));
        }
        c1 = db.query("UsersTable", null, "code = '" + String.valueOf(cl.getString(cl.getColumnIndex("owner"))) + "'", null, null, null, null);
        c1.moveToFirst();
        tvName.setText(cl.getString(cl.getColumnIndex("name")));
        tvCost.setText(String.valueOf(cl.getInt(cl.getColumnIndex("cost")))+"$");
        tvOwner.setText(String.valueOf(c1.getString(c1.getColumnIndex("name"))) + " " + String.valueOf(c1.getString(c1.getColumnIndex("sername"))));
        tvEmail.setText(c1.getString(c1.getColumnIndex("email")));
        tvCode.setText(cl.getString(cl.getColumnIndex("owner")));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGet:
                cv = new ContentValues();
                cv.put("buyer", c.getInt(c.getColumnIndex("code")));
                long rowId = db.update("LotsList", cv, "_id=?",new String[] {String.valueOf(cl.getInt(cl.getColumnIndex("_id")))});
        }
    }
}
