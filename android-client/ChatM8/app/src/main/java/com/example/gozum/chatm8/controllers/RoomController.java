package com.example.gozum.chatm8.controllers;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gozum.chatm8.MessageActivity;
import com.example.gozum.chatm8.OnItemClickListener;
import com.example.gozum.chatm8.SocketClient;
import com.example.gozum.chatm8.adapters.RoomAdapter;
import com.example.gozum.chatm8.dto.RoomDTO;
import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.IEntity;
import com.example.gozum.chatm8.entities.Room;
import com.example.gozum.chatm8.helpers.PreferencesManager;
import com.example.gozum.chatm8.helpers.VolleyCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomController implements BaseController{

    private Context _ctx = null;

    private static volatile RoomController _instance = null;

    public void SetContext(Context ctx)
    {
        this._ctx = ctx;
    }

    public RoomController()
    {
        if(_instance != null)
        {
            throw new RuntimeException("Lütfen Instance() statik fonksiyonunu kullanınız !");
        }

    }

    public static RoomController Instance(){
        if(_instance == null)
        {
            synchronized (RoomController.class)
            {
            _instance = new RoomController();
            }
        }
        return _instance;
    }

    public List<Room> roomList = new ArrayList<>();
    RoomAdapter roomAdapter;


    /*
    TODO ROOM SOCKET İLE DEĞİL DİREK VERİTABANINDAN ÇEKİLSİN
     */
    @Override
    public void GetAll(@Nullable Context c, final VolleyCallback callback) {
        StringRequest req = new StringRequest(
                Request.Method.GET, "http://51.254.242.39:8000/getroomusers/" + PreferencesManager.Instance()._preferences.getString("accountid", ""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getJSONArray("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(_ctx);
        queue.add(req);
            //List<Room> rooms = SocketClient.Instance().GetAllRooms();
    }

    public void CreateInvitedGroup(Context ctx, List<String> userList,String roomName, final VolleyCallback callback)
    {
        JSONArray array = new JSONArray();

        for (String a :
                userList) {
            JSONObject obj= new JSONObject();
            try {
                obj.put("roomName",roomName);
                obj.put("accountid",a);

                array.put(obj);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject obj= new JSONObject();
        try {
            obj.put("roomName",roomName);
            obj.put("accountid",PreferencesManager.Instance()._preferences.getString("accountid",""));
            array.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject();
        try {
            object.put("users",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, "http://51.254.242.39:8000/adduserstoroom",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(jsonObjectRequest);
    }


    public void PrepareRooms(final RecyclerView recyclerView, final Context ctx)
    {
        roomList.clear();
        LinearLayoutManager manager = new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        roomAdapter = new RoomAdapter(ctx, roomList, new OnItemClickListener() {
            @Override
            public void onItemClick(IEntity entity,int position) {
                JoinRoom(((Room) entity).getRoomname(),"");
            }
        });
        recyclerView.setAdapter(roomAdapter);
        GetAll(ctx, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(JSONArray array) {

                roomList.addAll(RoomDTO.Instance().Gets(array));
                roomAdapter.notifyDataSetChanged();
            }
        });

        GetAllPublicRooms(ctx, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(JSONArray array) {
                Log.d("kardeş","public gliyo");
                roomList.addAll(RoomDTO.Instance().Gets(array));
                roomAdapter.notifyDataSetChanged();
            }
        });
    }


    public void AddRoom(Room room)
    {
        roomList.add(room);
        roomAdapter.notifyDataSetChanged();
    }

    public void JoinRoom(String roomName,String roomType)
    {
        /*
        Socket hızı yetişemediği için 100ms sleep
        TODO ARTIK VERİLERİ DB DEN ÇEK
         */
        SocketClient.Instance().JoinRoom(roomName,PreferencesManager.Instance()._preferences.getString("accountid",""),roomType);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(_ctx,MessageActivity.class);
        intent.putExtra("roomName",roomName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _ctx.startActivity(intent);
    }

    public void GetAllPublicRooms(Context ctx,final VolleyCallback callback)
    {
        StringRequest request = new StringRequest(
                Request.Method.GET, "http://51.254.242.39:8000/getallpublicrooms",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getJSONArray("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(request);
    }
}
