package com.example.gozum.chatm8.controllers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gozum.chatm8.SocketClient;
import com.example.gozum.chatm8.adapters.MessageAdapter;
import com.example.gozum.chatm8.controllers.BaseController;
import com.example.gozum.chatm8.dto.MessagesDTO;
import com.example.gozum.chatm8.entities.Message;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MessageController implements BaseController{

    private static volatile MessageController _instance = null;
    private Context ctx;
    List<Message> _messages = null;
    public MessageAdapter adapter = null;
    public RecyclerView view;
    public MessageController(){
        if(_instance != null)
            throw new RuntimeException("Lütfen Instance() kullanınız !");
    }

    public static MessageController Instance()
    {
        if(_instance == null)
        {
            synchronized (MessageController.class)
            {
                _instance = new MessageController();
            }
        }
        return _instance;
    }




    public List<Message> GetAll(Context ctx) {
        return SocketClient.Instance().getMessage();
    }

    @Override
    public void GetAll(Context ctx,final VolleyCallback callback)
    {
        StringRequest getAll = new StringRequest(
                Request.Method.GET,
                "http://51.254.242.39:8000/getallmessages",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject array = new JSONObject(response);
                            callback.onSuccess(array.getJSONArray("data"));
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
        RequestQueue que = Volley.newRequestQueue(ctx);
        que.add(getAll);
    }


    public int GetSize()
    {
        return _messages.size();
    }

    public void PrepareMessages(RecyclerView view,final Context ctx)
    {
            _messages = GetAll(ctx);
            view.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
            llm.setStackFromEnd(false);
            view.setLayoutManager(llm);
            adapter = new MessageAdapter(ctx,_messages);
            view.setAdapter(adapter);
            view.scrollToPosition(GetSize() -1);
            this.view = view;
    }


    public void AddMessage(Message m)
    {
        _messages.add(m);
        adapter.notifyDataSetChanged();
        view.scrollToPosition(MessageController.Instance().GetSize() -1);
    }

    public void DeleteAll()
    {
        _messages.clear();
        adapter.notifyDataSetChanged();
    }


}
