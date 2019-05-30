package com.example.gozum.chatm8;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gozum.chatm8.adapters.FriendAdapter;
import com.example.gozum.chatm8.adapters.FriendViewPager;
import com.example.gozum.chatm8.adapters.FriendViewPagerAdapter;
import com.example.gozum.chatm8.controllers.FriendController;
import com.example.gozum.chatm8.controllers.RoomController;
import com.example.gozum.chatm8.dto.FriendDTO;
import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.IEntity;
import com.example.gozum.chatm8.helpers.GlobalApplication;
import com.example.gozum.chatm8.helpers.PreferencesManager;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateGroup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    List<Friend> friendList;
    RecyclerView friendRecyclerView;
    FriendAdapter adapter;
    ViewPager viewPager;
    List<FriendViewPager> friendViewPagers;
    FriendViewPagerAdapter viewPagerAdapter;
    TextView groupName;
    List<String> addedFriendList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        PrepareSpinner();
        PrepareFriends();
        PrepareAddedFriends();

        groupName = findViewById(R.id.groupName);
    }

    public void PrepareSpinner()
    {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.group_type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void PrepareFriends()
    {
        friendRecyclerView = findViewById(R.id.friendRecyclerView);
        friendRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        manager.setStackFromEnd(false);
        friendRecyclerView.setLayoutManager(manager);
        friendList = new ArrayList<>();
        adapter = new FriendAdapter(this, friendList,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(IEntity entity,int position) {
                        if(!addedFriendList.contains(((Friend) entity).getFriends_account_id())){
                            addedFriendList.add(((Friend) entity).getFriends_account_id());
                            friendViewPagers.add(new FriendViewPager ( ((Friend) entity).getFriends_account_id()));
                            viewPagerAdapter.notifyDataSetChanged();
                            viewPager.setCurrentItem(friendViewPagers.size() -1);
                        }
                    }
                }
                ,null,null);
        FriendController.Instance().GetAll(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(JSONArray array) {
                friendList.addAll(FriendDTO.Instance().Gets(array));
                friendRecyclerView.setAdapter(adapter);
            }
        });
    }

    public void PrepareAddedFriends()
    {
        friendViewPagers = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.addedFriendViewPager);
        viewPagerAdapter = new FriendViewPagerAdapter(this, friendViewPagers, new OnItemClickListener() {
            @Override
            public void onItemClick(IEntity entity, int position) {
                friendViewPagers.remove(position);
                viewPagerAdapter.notifyDataSetChanged();
                addedFriendList.remove(((FriendViewPager) entity).getName());
            }
        });
        viewPager.setAdapter(viewPagerAdapter);
    }

    int position;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        switch (position)
        {
            case 0:
                friendRecyclerView.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                break;
            case 1:
                friendRecyclerView.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void CreateGroup(View v)
    {
        switch (position)
        {
            case 0:
                if(groupName.getText().toString().length() >=4)
                {
                    GlobalApplication.Wait(50);
                    RoomController.Instance().JoinRoom(groupName.getText().toString(),"public");
                    SocketClient.Instance().CreateRoom(groupName.getText().toString()); //for other clients
                    Intent i = new Intent(this,HomePage.class);
                    startActivity(i);
                }
                break;
            case 1:
                if(groupName.getText().toString().length() >4 && friendViewPagers.size() >= 1)
                {
                    List<String> userList = new ArrayList<>();
                    for (FriendViewPager friend : friendViewPagers) {
                        userList.add(friend.getName());
                    }
                    RoomController.Instance().CreateInvitedGroup(this, userList,groupName.getText().toString(), new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.d("kardeş",response.toString());
                        }

                        @Override
                        public void onSuccess(JSONArray array) {

                        }
                    });
                    GlobalApplication.Wait(50);
                    SocketClient.Instance().CreateRoom(groupName.getText().toString()); //for other clients
                    GlobalApplication.Wait(50);
                    RoomController.Instance().JoinRoom(groupName.getText().toString(),"private");
                }
        }
    }


}
