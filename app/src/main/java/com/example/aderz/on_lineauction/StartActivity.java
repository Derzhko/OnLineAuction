package com.example.aderz.on_lineauction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity implements View.OnClickListener {
    EditText et1, et2;
    Button btn1, btn2;
    Intent intent;
    ToDoDatabase database;
    Cursor c;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        database = new ToDoDatabase(this);
        db = database.getWritableDatabase();

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (NullPointerException e) {
            return false;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:// Открываем Activity регистрации
                intent = new Intent(this, RegistActivity.class);
                intent.putExtra("Login", et1.getText().toString());
                intent.putExtra("Pass", et2.getText().toString());
                startActivity(intent);
                break;
            case R.id.btn2:
                c = db.query("UsersTable", new String[]{"_id", "password", "email"}, null, null, null, null, null);
                if (c.moveToFirst()) {
                    int idColEmail = c.getColumnIndex("email");
                    int idColPass = c.getColumnIndex("password");
                    int idColId = c.getColumnIndex("_id");
                    Log.e("myLogs", "idColEmail" + idColEmail);
                    Log.e("myLogs", "idColPass" + idColPass);
                    Log.e("myLogs", "idColId" + idColId);
                    int i = 0;
                    do {
                        i++;
                        Log.e("myLogs", "i: " + i);
                        String Email = c.getString(idColEmail).toString();
                        String Pass = c.getString(idColPass).toString();
                        int Id = c.getInt(idColId);
                        if (isOnline()) {
                            //new Thread(new Client()).start();
                            //Client.checkUser("checkUser", et1.getText().toString(), et2.getText().toString());
                            if (/*Integer.parseInt(Client.obj1[0].toString()) !=-1*/et1.getText().toString().equals(Email) && et2.getText().toString().equals(Pass)) {  // Ищем совпадения введённых данных с БД
                                //Id = Integer.parseInt(String.valueOf(Client.obj1[0]));
                                intent = new Intent(this, FirstActivity.class);
                                intent.putExtra("_id", Id);
                                startActivity(intent);
                            }
                        } else
                            Toast.makeText(this, "Нет подключения к сети Интернет", Toast.LENGTH_SHORT).show();
                    } while (c.moveToNext());
                }
        }
    }
}
