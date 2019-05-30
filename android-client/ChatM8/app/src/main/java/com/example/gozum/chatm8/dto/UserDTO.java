package com.example.gozum.chatm8.dto;

import android.util.Log;

import com.example.gozum.chatm8.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserDTO implements BaseDTO<User> {

    private static volatile UserDTO _instance = null;
    public UserDTO()
    {
        if(_instance != null)
            throw new RuntimeException("Lütfen Instance() ile kullanın !");
    }

    public static UserDTO Instance(){
        if(_instance == null)
        {
            synchronized (UserDTO.class)
            {
                _instance = new UserDTO();
            }
        }
        return _instance;
    }


    @Override
    public User Get(JSONObject obj) {
        return null;
    }

    @Override
    public List<User> Gets(JSONArray jsonArray) {
        List<User> _list = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++)
        {
            User r = new User(0,null,null,null,null,false,null,null);
            try{
                JSONObject obj = jsonArray.getJSONObject(i);
                r.setAccount_id(obj.getString("accountid"));
                r.setActive(obj.getBoolean("isActive"));
                r.setCreated_time(obj.getString("created_time"));
                r.setImgUrl(obj.getString("imgUrl"));
                r.setName(obj.getString("name"));
                //r.setUser_id(obj.getString("_id"));
                Log.d("GETALL","eklendi kardeş");
            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            _list.add(r);
        }
        return _list;
    }
}
