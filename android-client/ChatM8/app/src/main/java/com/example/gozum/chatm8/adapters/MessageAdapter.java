package com.example.gozum.chatm8.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gozum.chatm8.R;
import com.example.gozum.chatm8.entities.Message;
import com.example.gozum.chatm8.helpers.GlobalApplication;
import com.example.gozum.chatm8.helpers.PreferencesManager;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LAYOUT_SELF = 0;
    private static final int LAYOUT_SOMEONE=1;
    private Context mCtx;
    private List<Message> messageList;

    public MessageAdapter(Context mCtx, List<Message> messageList) {
        this.mCtx = mCtx;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        switch(i)
        {
            case LAYOUT_SELF:
                View v = inflater.inflate(R.layout.home_messages, null);
                return new MessageViewHolder(v);
            case LAYOUT_SOMEONE:
                View _v = inflater.inflate(R.layout.message_from_another, null);
                return new MessageViewHolder2(_v);
            default:
                View __v = inflater.inflate(R.layout.home_messages, null);
                return new MessageViewHolder(__v);
        }
    }

    @Override
    public int getItemViewType(int i)
    {
        if(messageList.get(i).getMessage_from_id().equals(PreferencesManager.Instance()._preferences.getString("accountid","")))
            return LAYOUT_SELF;
        return LAYOUT_SOMEONE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message message = messageList.get(i);

        switch(viewHolder.getItemViewType())
        {
            case LAYOUT_SELF:
                MessageViewHolder viewHolder0 = (MessageViewHolder)viewHolder;
                viewHolder0._content.setText(message.getMessage());
                viewHolder0._name.setText(String.valueOf(message.getMessage_from_id()));

                break;
            case LAYOUT_SOMEONE:
                MessageViewHolder2 viewHolder1 = (MessageViewHolder2)viewHolder;
                viewHolder1.content.setText(message.getMessage());
                viewHolder1.name.setText(message.getMessage_from_id());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView _name,_content;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            //image = itemView.findViewById(R.id.message_image);
            _name = itemView.findViewById(R.id.adapter_message_name);
            _content = itemView.findViewById(R.id.adapter_message_content);
            //content = itemView.findViewById(R.id.message_content);
        }
    }

    class MessageViewHolder2 extends RecyclerView.ViewHolder{
        TextView name,content;
        public MessageViewHolder2(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.adapter_message_name_another);
            content = itemView.findViewById(R.id.adapter_message_content_another);
        }
    }
}
