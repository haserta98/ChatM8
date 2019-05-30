package com.example.gozum.chatm8.dto;

import android.util.Log;

import com.example.gozum.chatm8.entities.Message;
import com.example.gozum.chatm8.helpers.PreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessagesDTO  {

    private static volatile MessagesDTO _instance = null;
    public MessagesDTO()
    {
        if(_instance != null)
            throw new RuntimeException("Lütfen Instance() kullanınız !");
    }

    public static MessagesDTO Instance()
    {
        if(_instance == null)
        {
            synchronized (MessagesDTO.class)
            {
                _instance = new MessagesDTO();
            }
        }
        return _instance;
    }


    public List<Message> Gets(JSONArray args)
    {
        List<Message> list = new ArrayList<>();
        for (int i=0;i<args.length();i++)
        {
        Message m = new Message(0,null,null,null,null,null);
            try {
                JSONObject obj = args.getJSONObject(i);
                m.setMessage(obj.getString("message"));
                //m.setRoomid(obj.getString("roomid"));
                m.setMessage_from_id(obj.getString("userid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(m);
        }
        return list;
    }

    public Message Get(JSONObject data)
    {
        Message m = new Message();


        try {
            m.setMessage_from_id(data.get("userid").toString());
            m.setMessage(data.get("message").toString());
            //m.setRoomid(data.getString("roomid"));
            return m;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
