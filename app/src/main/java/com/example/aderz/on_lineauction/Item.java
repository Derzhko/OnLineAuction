package com.example.aderz.on_lineauction;

public class Item {
    String name = new String();
    int cost;
    int id;
    Item(int cost, String name, int id){
        this.cost = cost; this.name = name; this.id = id;
    }
}
