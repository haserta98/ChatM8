package com.example.gozum.chatm8.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gozum.chatm8.OnItemClickListener;
import com.example.gozum.chatm8.R;
import com.example.gozum.chatm8.adapters.FriendAdapter;
import com.example.gozum.chatm8.adapters.SearchFriendAdapter;
import com.example.gozum.chatm8.dto.FriendDTO;
import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.IEntity;
import com.example.gozum.chatm8.entities.User;
import com.example.gozum.chatm8.helpers.PreferencesManager;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FriendController extends AppCompatActivity implements BaseController {
    private static final String url = "http://51.254.242.39:8000/";
    private List<Friend> friendList = new ArrayList<Friend>();
    private FriendAdapter adapter;
    private static volatile FriendController _instance = null;
    private SearchFriendAdapter searchFriendAdapter;
    public FriendController()
    {
        if(_instance != null)
        {
            throw new RuntimeException("Lütfen Instance() kullanarak erişiniz !");
        }
    }
    public static FriendController Instance()
    {
        if(_instance == null)
        {
            synchronized (FriendController.class)
            {
                _instance = new FriendController();
            }
        }
        return _instance;
    }

    @Override
    public void GetAll(Context ctx, final VolleyCallback callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, url + "getallfriends/" + Objects.requireNonNull(PreferencesManager.Instance()._preferences.getString("accountid","")),
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

    public void GetAllFriendRequest(Context ctx, final VolleyCallback callback)
    {
        StringRequest request = new StringRequest(
                Request.Method.GET, url + "getfriendrequestbyid/" + Objects.requireNonNull(PreferencesManager.Instance()._preferences.getString("accountid", "")),
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

    public void AddFriend(Context ctx, final String friends_account_id, final VolleyCallback callback)
    {
        StringRequest request = new StringRequest(
                Request.Method.POST, url + "addfriend",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getJSONObject("data"));
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
        ){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("user_account_id", Objects.requireNonNull(PreferencesManager.Instance()._preferences.getString("accountid","")));
                params.put("friends_account_id", friends_account_id);
                params.put("created_time","010101");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(request);
    }

    public void PrepareFriends(final RecyclerView view,final Context ctx,boolean isCheckedForRequest)
    {
        friendList.clear();
        view.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
        manager.setStackFromEnd(false);
        view.setLayoutManager(manager);
        adapter = new FriendAdapter(ctx, friendList,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(IEntity entity,int position) {

                    }
                },
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(IEntity entity, int position) {
                        Friend friend = ((Friend) entity);
                        AcceptFriendRequest(ctx, friend.getFriends_account_id(), friend.getUser_account_id(),position);
                    }
                },
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(IEntity entity,int position) {
                        Friend friend = ((Friend) entity);
                        DeclineFriendRequest(ctx, friend.getFriends_account_id(), friend.getUser_account_id(),position);
                    }
                });

        if(isCheckedForRequest){
            GetAllFriendRequest(ctx, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                }
                @Override
                public void onSuccess(JSONArray array) {
                    friendList.addAll(FriendDTO.Instance().GetRequests(array));
                }
            });
        }

        GetAll(ctx, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(JSONArray array) {
                friendList.addAll(FriendDTO.Instance().Gets(array));
                view.setAdapter(adapter);
            }
        });
    }

    public void PrepareSearchedFriends(final RecyclerView view,final Context ctx, List<User> userList)
    {
        view.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
        manager.setStackFromEnd(false);
        view.setLayoutManager(manager);
        searchFriendAdapter = new SearchFriendAdapter(ctx, userList, new OnItemClickListener() {
            @Override
            public void onItemClick(final IEntity entity,int position) {
                //Toast.makeText(ctx,"TIKLADIN" + ((User) entity).getAccount_id(),Toast.LENGTH_LONG).show();
                SendFriendRequest(ctx, ((User) entity).getAccount_id(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        searchFriendAdapter.userList.remove(entity);
                        searchFriendAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onSuccess(JSONArray array) {

                    }
                });
            }
        });
        view.setAdapter(searchFriendAdapter);
    }


    public void SearchFriendsByName(Context ctx,final String name,final VolleyCallback volleyCallback)
    {
        StringRequest req = new StringRequest(
                Request.Method.POST, url + "searchfriends",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("data");
                            volleyCallback.onSuccess(array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY ERROR",error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("accountid",name);
            return params;
        }
    };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(req);
    }

    public void SendFriendRequest(Context ctx,final String toAccountId,final VolleyCallback callback)
    {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                url + "sendfriendrequest",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getJSONObject("data"));
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
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sended_request_accountid", Objects.requireNonNull(PreferencesManager.Instance()._preferences.getString("accountid","")));
                params.put("to_request_accountid",toAccountId);
                params.put("created_time","0101");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(req);
    }



    public void DeclineFriendRequest(Context ctx, final String sended_account_id, final String to_account_id, final int position)
    {
        DeleteFriendRequest(ctx, sended_account_id, to_account_id, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                RemoveFriendRequestElement(position);
            }

            @Override
            public void onSuccess(JSONArray array) {

            }
        });

    }


    public void AcceptFriendRequest(Context ctx, final String sended_account_id, final String to_account_id, final int position)
    {
        DeleteFriendRequest(ctx, sended_account_id, to_account_id, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                RemoveFriendRequestElement(position);
            }

            @Override
            public void onSuccess(JSONArray array) {

            }
        });

        AddFriend(ctx, sended_account_id, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                friendList.add(FriendDTO.Instance().Get(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess(JSONArray array) {

            }
        });

    }

    public void DeleteFriendRequest(Context ctx, final String sended_account_id, final String to_request_accountid, final VolleyCallback callback)
    {
        StringRequest request = new StringRequest(
                Request.Method.POST, url + "deletefriendrequest",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getJSONObject("data"));
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
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sended_request_accountid", sended_account_id);
                params.put("to_request_accountid", to_request_accountid);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(request);
    }

    public void RemoveFriendRequestElement(int position)
    {

        friendList.remove(position);
        adapter.notifyDataSetChanged();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.d("kardeş",String.valueOf(adapter.getItemCount()) + " " + String.valueOf(friendList.size()) +" " + String.valueOf(friendList.size()));

    }
}
