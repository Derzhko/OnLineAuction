package com.example.aderz.on_lineauction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyAdapter extends ArrayAdapter<Item> {

    public MyAdapter(@NonNull Context context, @NonNull LinkedList<Item> arr) {
        super(context, R.layout.list_fragment, arr);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Item item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_fragment, null);
        }

        ((TextView) convertView.findViewById(R.id.tvName)).setText(item.name);
        Log.d(TAG, "item.cost: "+item.cost);
        ((TextView) convertView.findViewById(R.id.tvCost)).setText(String.valueOf(item.cost+"$"));
        return convertView;
    }
}
