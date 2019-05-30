package com.example.gozum.chatm8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gozum.chatm8.controllers.FriendController;
import com.example.gozum.chatm8.dto.FriendDTO;
import com.example.gozum.chatm8.dto.UserDTO;
import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.User;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.List;

public class SearchFriendsActivity extends AppCompatActivity {
    EditText friendName;
    Button search;
    RecyclerView searchFriendRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);

        friendName = findViewById(R.id.friendName);
        search = findViewById(R.id.searchButton);
        searchFriendRecycler = findViewById(R.id.searchFriendRecycler);
        search.setOnClickListener(searchButtonListener);

        Toolbar toolbar =  findViewById(R.id.search_friend_toolbar);
        setSupportActionBar(toolbar);


    }


    View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FriendController.Instance().SearchFriendsByName(getApplicationContext(), friendName.getText().toString(), new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                }

                @Override
                public void onSuccess(JSONArray array) {
                    if(array.length() != 0){
                        List<User> users = UserDTO.Instance().Gets(array);
                        FriendController.Instance().PrepareSearchedFriends((RecyclerView) findViewById(R.id.searchFriendRecycler) , getApplicationContext(),users);
                    }
                }
            });
        }
    };



}
