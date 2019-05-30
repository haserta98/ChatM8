package com.example.gozum.chatm8;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gozum.chatm8.controllers.MessageController;
import com.example.gozum.chatm8.entities.Message;
import com.example.gozum.chatm8.helpers.PreferencesManager;

public class MessageActivity extends AppCompatActivity {
    EditText messageText;
    RecyclerView view;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Boot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Boot() {
        /*
         * PREPARE MESSAGE AND LISTENERS
         */
        view = findViewById(R.id.messageRecys);
        MessageController.Instance().PrepareMessages(view,this);
        messageText = findViewById(R.id.messageText);
        name = PreferencesManager.Instance()._preferences.getString("name","");
        messageText.setOnKeyListener(enterListener);


        messageText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction())
                    MessageController.Instance().view.scrollToPosition(MessageController.Instance().GetSize()-1);
                return false;
            }
        });


        /*
         ** PREPARE TOOLBAR
         */
        Toolbar toolbar =  findViewById(R.id.message_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.account_box_black_24dp);
        toolbar.setNavigationOnClickListener(backPressedListener);
        toolbar.setTitle(getIntent().getExtras().getString("roomName"));


        messageText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //Perform your Actions here.

                }
                return handled;
            }
        });
    }



    public void AdapterScroll()
    {

    }

    /*
    Enter listener for sending message
     */
    View.OnKeyListener enterListener= new View.OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if((event.getAction()) == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER && !TextUtils.isEmpty(messageText.getText().toString()))
            {
                String message = messageText.getText().toString();
                String userid = PreferencesManager.Instance()._preferences.getString("accountid","");
                SocketClient.Instance().SendMessage(name,message,userid);
                MessageController.Instance().AddMessage(new Message(0,message,userid,null,null,null));
                messageText.setText("");
                return false;
            }
            return false;
        }
    };

    View.OnClickListener backPressedListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            MessageActivity.super.onBackPressed();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.room_menu:
                SocketClient.Instance().ClearMessageEmit();
                MessageController.Instance().DeleteAll();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
