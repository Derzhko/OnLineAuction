package com.example.aderz.on_lineauction;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class RegistActivity extends Activity implements View.OnClickListener {
    final String TAG = "myLog";
    EditText etName, etSername, etLogin, etPass, etEmail;
    Button btnBack, btnAcces;
    Intent intent;
    ToDoDatabase database;
    SQLiteDatabase db;
    Cursor c;
    ContentValues cv;
    String sName, sSername, sLogin, sPass, sEmail;
    Random rnd = new Random();
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        intent = getIntent();
        etName = findViewById(R.id.etName);
        etSername = findViewById(R.id.etSername);
        etLogin = findViewById(R.id.etLogin);
        etPass = findViewById(R.id.etPass);
        etEmail = findViewById(R.id.etEmail);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnAcces = findViewById(R.id.btnAcces);
        btnAcces.setOnClickListener(this);
        //etLogin.setText(intent.getStringExtra("Login").toString());
        ///etPass.setText(intent.getStringExtra("Pass").toString());
        database = new ToDoDatabase(this);
        db = database.getWritableDatabase();
    }

    private boolean checkRandom(int rnd) {
        c = db.query("UsersTable", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(c.getColumnIndex("code")) == rnd)
                    return false;
            } while (c.moveToNext());
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        cv = new ContentValues();
        sName = etName.getText().toString();
        sSername = etSername.getText().toString();
        sLogin = etLogin.getText().toString();
        sPass = etPass.getText().toString();
        sEmail = etEmail.getText().toString();
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnAcces:
                do{
                    code = rnd.nextInt(1000);
                    Log.d("MyLogs", "index "+code);
                }while(!checkRandom(code));

                if (!sName.isEmpty() && !sSername.isEmpty() && !sLogin.isEmpty() && !sPass.isEmpty() && !sEmail.isEmpty()) {
                    cv.put("name", sName);
                    cv.put("sername", sSername);
                    cv.put("login", sLogin);
                    cv.put("password", sPass);
                    cv.put("email", sEmail);
                    cv.put("code", code);
                    new Thread(new Client()).start();

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Private Policy").setMessage(R.string.polite).setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int id) {
                            dialogInterface.cancel();
                        }
                    }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int id) {

                            //Client client = new Client();
                            Log.e("MyLogs", "Создание клиента");
                            Log.d("MyLogs", "index "+code);
                            long rowId = db.insert("UsersTable", null, cv);
                            Client.insertUser("insert user", sName, sSername, sLogin, sPass, sEmail, code);
                            Log.e("MyLogs", "Добавлено в бд");
                            Log.d(TAG, "Id users" + rowId);
                            database.close();
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();//Добавляет пользователя в БД
                } else {
                    Toast.makeText(this, "Введите полностью данные", Toast.LENGTH_SHORT).show();
                }
        }

    }
}

