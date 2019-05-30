package com.example.gozum.chatm8.helpers;

import android.app.Application;
import android.content.Context;

public class GlobalApplication extends Application {

    private static Context appContext;
    private String roomName;


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return appContext;
    }

    public static void Wait(int milis)
    {
        try{
            Thread.sleep(milis);
        }catch(InterruptedException E)
        {
            E.printStackTrace();
        }
    }
}
