package com.example.gozum.chatm8.dto;

import android.util.Log;

import com.example.gozum.chatm8.entities.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO implements BaseDTO<Room>{

    private static volatile RoomDTO _instance = null;
    public static RoomDTO Instance()
    {
        if(_instance == null)
        {
            synchronized (RoomDTO.class)
            {
                _instance = new RoomDTO();
            }
        }
        return _instance;
    }

    public RoomDTO()
    {

    }

    public Room Get(JSONObject obj){
        if(obj == null)
            return null;
        Room r = new Room();
        try {
            //r.setRoomid(obj.getString("_id"));
            r.setRoomname(obj.getString("roomName"));
            r.setCreated_time(obj.getString("created_time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return r;
    }

    public List<Room> Gets(JSONArray args){

        List<Room> list = new ArrayList<Room>();
        for (int i=0;i<args.length();i++)
        {
            Room r = new Room(null,null,null);
            try {
                JSONObject obj = args.getJSONObject(i);
                r.setRoomid( obj.getString("_id"));
                r.setRoomname(obj.getString("roomName"));
                r.setCreated_time(obj.getString("created_time"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            list.add(r);
        }
        return list;
    }
}
