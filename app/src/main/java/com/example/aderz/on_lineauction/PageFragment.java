
package com.example.aderz.on_lineauction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by aderz on 08.03.2018.
 */

public class PageFragment extends Fragment implements View.OnClickListener {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    String[] names = {"Дима", "Наташа"};
    ArrayList<String> arrList = new ArrayList<String>();
    int pageNumber;
    int backColor;
    EditText etname, etcost;
    Button btnadd;
    View view;
    ListView lv, lv0;
    MyAdapter adapter, adapter0;
    ToDoDatabase database;
    SQLiteDatabase db;
    LinkedList<Item> MyItem = new LinkedList<Item>();
    LinkedList<Item> MyItem0 = new LinkedList<Item>();
    Cursor c, c0, c1;
    ContentValues cv;
    Intent intent;
    List<String> list;
    static private Socket connection;
    static private ObjectInputStream input;
    static private ObjectOutputStream output;

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        Log.d(TAG, "pageFragment onCreate");
        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        arrList.add("Саша");
        arrList.add("Миша");
        intent = new Intent(getContext(), FirstActivity.class);
        addList();
        addList0();
        Log.d("myLog", "MyItem.size(): " + MyItem.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        Log.d(TAG, "PageFragment onCreateView");
        switch (pageNumber) {
            case 2:
                view = inflater.inflate(R.layout.set_lot, null);
                etname = view.findViewById(R.id.etname);
                etcost = view.findViewById(R.id.etcost);
                btnadd = view.findViewById(R.id.btnadd);
                btnadd.setOnClickListener(this);
                break;
            case 1:
                view = inflater.inflate(R.layout.fragment, null);
                lv = view.findViewById(R.id.lv);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), ItemInfo.class);
                        Log.d(TAG, "adapter.getItem(position).toString(): " + adapter.getItem(position).id);
                        intent.putExtra("id", FirstActivity.id);
                        intent.putExtra("idl", adapter.getItem(position).id);
                        startActivity(intent);
                    }
                });
                break;
            case 0:
                view = inflater.inflate(R.layout.fragment, null);
                lv0 = view.findViewById(R.id.lv);
                lv0.setAdapter(adapter0);
                lv0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), ItemBuyerInfo.class);
                        Log.d(TAG, "adapter.getItem(position).toString(): " + adapter0.getItem(position).id);
                        intent.putExtra("id", FirstActivity.id);
                        intent.putExtra("idl", adapter0.getItem(position).id);
                        startActivity(intent);
                    }
                });
        }
        return view;
    }

    public void addList0() {
        database = new ToDoDatabase(getContext());
        db = database.getWritableDatabase();
        adapter0 = new MyAdapter(getContext(), MyItem0);
        adapter0.setNotifyOnChange(true);
        c0 = db.query("LotsList", null, null, null, null, null, null);
        if (c0.moveToFirst()) {
            int idColIndex = c0.getColumnIndex("_id");
            int idColName = c0.getColumnIndex("name");
            int idColCost = c0.getColumnIndex("cost");
            int udColTime = c0.getColumnIndex("time");
            adapter0.clear();
            MyItem0.clear();
            do {
                if (c0.getInt(c0.getColumnIndex("buyer")) == FirstActivity.code)
                    MyItem0.addFirst(new Item(c0.getInt(idColCost), c0.getString(idColName), c0.getInt(idColIndex)));
            } while (c0.moveToNext());
        }
    }

    public void addList() {
        int i = 0;
        database = new ToDoDatabase(getContext());
        db = database.getWritableDatabase();
        adapter = new MyAdapter(getContext(), MyItem);
        adapter.setNotifyOnChange(true);
        c = db.query("LotsList", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int idColName = c.getColumnIndex("name");
            int idColCost = c.getColumnIndex("cost");
            int udColTime = c.getColumnIndex("time");
            Log.d(TAG, "idColName: " + idColName + "\\n idColCost: " + idColCost + "\\n Integer.parseInt(c.getString(idColCost).toString()): " + Integer.parseInt(c.getString(idColCost).toString())
                    + "\\n c.getString(idColName).toString(): " + c.getString(idColName));
            adapter.clear();
            MyItem.clear();
            do {
                i++;
                if (c.getInt(c.getColumnIndex("buyer"))== 0)
                    MyItem.addFirst(new Item(c.getInt(idColCost), c.getString(idColName), c.getInt(idColIndex)));
            } while (c.moveToNext());
            Log.d("myLog", "MyItem.size(): " + MyItem.size());
            Log.d("myLog", "iiii: " + i);
        }
    }

    @Override
    public void onClick(View v) {
        cv = new ContentValues();
        c1 = db.query("UsersTable", null, "_id = '" + FirstActivity.id + "'", null, null, null, null);
        c1.moveToFirst();
        switch (v.getId()) {
            case R.id.btnadd:
                arrList.add(etname.getText().toString());
                cv.put("name", etname.getText().toString());
                cv.put("cost", etcost.getText().toString());
                cv.put("owner", c1.getInt(c1.getColumnIndex("code")));
                long RowId = db.insert("LotsList", null, cv);
                database.close();
                addList();
                // Client.sentData("add to Lots", etname.getText().toString(), Double.parseDouble(etcost.getText().toString()), FirstActivity.code, Integer.parseInt(null));
               /* startActivity(intent);
                new FirstActivity().finish();*/

        }
    }
}