package com.example.aderz.on_lineauction;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by aderz on 10.04.2018.
 */
public class Client implements Runnable {
    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    static Object obj[];
    static Object obj1[];

 /*  Client(){
        new Thread(new Client()).start();
    }*/

    @Override
    public void run() {
        try {
            while(true) {
                Log.d("MyLogs", "конектинг");
                connection = new Socket(InetAddress.getByName("188.225.18.37"), 567);

                Log.d("MyLogs", "успешно");
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                obj1 = (Object[]) input.readObject();
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void sentData(String a, String name, double cost, int owner, int buyer){
        try {
            Object obj[] = {a, name, cost, owner, buyer};
            output.flush();
            output.writeObject(obj);
        } catch (IOException e) {}
    }

    static void insertUser(String a, String name, String serName, String  login, String password, String email, int code){
        try {
            Object obj[] = {a, name, serName, login, password, email, code};
            output.flush();
            output.writeObject(obj);
            Log.d("MyLogs", "Объект отправлен");
        } catch (IOException e) {}
    }

    static void checkUser(String a, String email, String password){
        Object obj[] = {a, email, password};
        try {
            output.flush();
            output.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
