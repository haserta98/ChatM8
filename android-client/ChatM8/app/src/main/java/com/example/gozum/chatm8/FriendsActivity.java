package com.example.gozum.chatm8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gozum.chatm8.controllers.FriendController;
import com.example.gozum.chatm8.helpers.PreferencesManager;

public class FriendsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RecyclerView view;
    Intent addFriends;
    FloatingActionButton addFriendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar =  findViewById(R.id.friends_toolbar);
        setSupportActionBar(toolbar);
        view = findViewById(R.id.friendRecyc);
        FriendController.Instance().PrepareFriends(view,this,true);
        addFriends = new Intent(this, SearchFriendsActivity.class);
        addFriendButton = (FloatingActionButton) findViewById(R.id.addFriends);

        addFriendButton.setOnClickListener(OnAddFriends);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_settings:
                PreferencesManager.Instance()._editor.putBoolean("auth",false);
                PreferencesManager.Instance()._editor.commit();
                Intent i = new Intent(FriendsActivity.this,MainActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.nav_friends:
                //Intent i = new Intent(FriendsActivity.this,FriendsActivity.class);
                //startActivity(i);
                break;
        }
        return true;
    }

    View.OnClickListener OnAddFriends = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(addFriends);
        }
    };



}
