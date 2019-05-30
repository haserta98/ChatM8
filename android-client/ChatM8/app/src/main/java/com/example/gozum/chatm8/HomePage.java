package com.example.gozum.chatm8;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.gozum.chatm8.controllers.RoomController;
import com.example.gozum.chatm8.helpers.PreferencesManager;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView name;
    TextView accountid;
    NavigationView navigationView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Boot();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                Intent i = new Intent(HomePage.this,MainActivity.class);
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
                Intent i = new Intent(HomePage.this,FriendsActivity.class);
                startActivity(i);
                break;
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Boot()
    {

        recyclerView =  findViewById(R.id.recys);

        /*
        Set Toolbar
         */
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        Set Floating Button Process
         */
        FloatingActionButton fab =  findViewById(R.id.newRoom);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this,CreateGroup.class);
                startActivity(i);
            }
        });

        /*
        Set Toggle
         */
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*
        ** NavigationHeader set
         */
        View navigationHeader = navigationView.getHeaderView(0);
        name = navigationHeader.findViewById(R.id.nav_header_name);
        accountid = navigationHeader.findViewById(R.id.nav_header_accountid);
        name.setText(PreferencesManager.Instance()._preferences.getString("name",""));
        accountid.setText(PreferencesManager.Instance()._preferences.getString("accountid",""));

        RoomController.Instance().PrepareRooms(recyclerView,getApplicationContext());
    }


    public void Profile(View v)
    {
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }
}
