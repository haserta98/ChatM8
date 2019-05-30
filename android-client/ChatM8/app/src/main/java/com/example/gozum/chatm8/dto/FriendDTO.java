package com.example.gozum.chatm8.dto;

import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FriendDTO {

    private static volatile FriendDTO _instance = null;
    public FriendDTO()
    {
        if(_instance != null)
            throw new RuntimeException("Lütfen Instance() kullanınız !");
    }

    public static FriendDTO Instance()
    {
        if(_instance == null)
        {
            synchronized (FriendDTO.class)
            {
                _instance = new FriendDTO();
            }
        }
        return _instance;
    }


    public List<Friend> Gets(JSONArray args)
    {
        List<Friend> list = new ArrayList<>();
        for (int i=0;i<args.length();i++)
        {
        Friend m = new Friend();
            try {
                JSONObject obj = args.getJSONObject(i);
                m.setFriend_id(obj.getString("_id"));
                m.setFriends_account_id(obj.getString("friends_account_id"));
                m.setUser_account_id(obj.getString("user_account_id"));
                m.setCreated_time(obj.getString("created_time"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(m);
        }
        return list;
    }

    public List<Friend> GetRequests(JSONArray args){
        List<Friend> list = new ArrayList<>();
        for (int i=0;i<args.length();i++)
        {
            Friend m = new Friend();
            try {
                JSONObject obj = args.getJSONObject(i);
                m.setFriend_id("0");
                m.setFriends_account_id(obj.getString("sended_request_accountid"));
                m.setUser_account_id(obj.getString("to_request_accountid"));
                m.setCreated_time(obj.getString("created_time"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(m);
        }
        return list;
    }

    public Friend GetRequest(JSONObject obj){

            Friend m = new Friend();
            try {
                m.setFriend_id("0");
                m.setFriends_account_id(obj.getString("sended_request_accountid"));
                m.setUser_account_id(obj.getString("to_request_accountid"));
                m.setCreated_time(obj.getString("created_time"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return m;
    }

    public Friend Get(JSONObject obj)
    {
        Friend m = new Friend();
        try {
            m.setFriend_id(obj.getString("_id"));
            m.setFriends_account_id(obj.getString("friends_account_id"));
            m.setUser_account_id(obj.getString("user_account_id"));
            m.setCreated_time(obj.getString("created_time"));
            return m;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
