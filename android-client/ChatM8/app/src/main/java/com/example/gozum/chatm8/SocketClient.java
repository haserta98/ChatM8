package com.example.gozum.chatm8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gozum.chatm8.controllers.MessageController;
import com.example.gozum.chatm8.controllers.RoomController;
import com.example.gozum.chatm8.dto.MessagesDTO;
import com.example.gozum.chatm8.dto.RoomDTO;
import com.example.gozum.chatm8.entities.Message;
import com.example.gozum.chatm8.entities.Room;
import com.example.gozum.chatm8.helpers.GlobalApplication;
import com.example.gozum.chatm8.helpers.PreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public final class SocketClient extends AppCompatActivity {

    /*
    *Singleton
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,MessageActivity.class));
    }

    private static SocketClient single_instance = null;
    public static SocketClient Instance()
    {
        if(single_instance == null)
        {
            synchronized (SocketClient.class)
            {
                single_instance = new SocketClient();
            }
        }
        return single_instance;
    }

    public SocketClient()  {
        /*
        *Connect socket with localhost and 3000 port
         */
        try {
            _socket = IO.socket("http://51.254.242.39:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        _socket.connect();

        /*
        *Register All Event Catchers
        * if not register, cannot catches events
        * but not need to register emit functions
         */
        GetMessages();
        GetAllRooms();
        OnMessage();
        NewRoom();
        ClearMessage();
        SomeOneJoinedRoom();
        OnDisconnect();
    }
    private static Socket _socket;
    private List<Room> _rooms = new ArrayList<>();
    private List<Message> _messages = new ArrayList<>();
    private Room room=null;

    public void JoinRoom(String room,String name,String roomType){
        JSONObject arg = new JSONObject();
        try {
            arg.put("room",room);
            arg.put("name",name);
            if(roomType == null)
                roomType = "public";
            arg.put("roomType",roomType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        _socket.emit("join",arg);
    }

    public void SomeOneJoinedRoom()
    {
        _socket.on("new join", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    Log.d("SOCKET_LOG_NEW_JOIN",data.getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Message> GetMessages(){
        _socket.on("coming message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                    JSONArray data = (JSONArray)args[0];
                    _messages = MessagesDTO.Instance().Gets(data);
            }
        });
        return _messages;
    }

    public List<Message> getMessage()
    {
        if(_messages == null)
            GetMessages();
        return _messages;
    }
    Message m;
    public void OnMessage()
    {
        _socket.on("on message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONArray arg = (JSONArray)args[0];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            m = MessagesDTO.Instance().Get(arg.getJSONObject(0));
                            MessageController.Instance().AddMessage(m);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /* TODO CHECK IT */
    public void SendMessage(String name,String message,String userid){
        JSONObject obj = new JSONObject();
        try {
            obj.put("name",name);
            obj.put("message",message);
            obj.put("userid",userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        _socket.emit("new message",obj);
    }

    public void NewRoom()
    {
        _socket.on("new room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];
                Log.d("ÜRETTİ",obj.toString());
                room = RoomDTO.Instance().Get(obj);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RoomController.Instance().AddRoom(room);
                    }
                });
            }
        });
    }

    public void CreateRoom(String roomname)
    {
        JSONObject obj = new JSONObject();
        try{
            obj.put("roomName",roomname);
            obj.put("created_time","123");
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
        _socket.emit("new room",obj);
    }

    public void GetAllRooms()
    {
        _socket.on("get all room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray rooms = (JSONArray)args[0];
                _rooms = RoomDTO.Instance().Gets(rooms);
                Log.d("ODALAR","ODALAR GELDİ" + String.valueOf(rooms.length()));
            }
        });
    }

    /* TODO CHECK IT */
    public void ClearMessage()
    {
        _socket.on("clear message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MessageController.Instance().DeleteAll();
                    }
                });

            }
        });
    }

    public void ClearMessageEmit()
    {
        _socket.emit("delete messages by room id");
        ClearMessage();
    }

    public void OnDisconnect()
    {
        _socket.on("on disconnect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //JSONObject arg = (JSONObject) args[0];
            }
        });
    }

}
