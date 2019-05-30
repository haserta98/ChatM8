package com.example.gozum.chatm8.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gozum.chatm8.OnItemClickListener;
import com.example.gozum.chatm8.R;
import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.Room;
import com.example.gozum.chatm8.helpers.PreferencesManager;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    private List<Friend> friendList;
    private OnItemClickListener listener;
    private OnItemClickListener acceptListener;
    private OnItemClickListener declineListener;
    private static final int LAYOUT_REQUEST = 0;
    private static final int LAYOUT_FRIEND = 1;
    public FriendAdapter(Context ctx, List<Friend> friendList, OnItemClickListener listener,OnItemClickListener acceptListener,OnItemClickListener declineListener)
    {
        this.ctx = ctx;
        this.friendList = friendList;
        this.listener = listener;
        this.acceptListener = acceptListener;
        this.declineListener = declineListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        switch (i)
        {
            case LAYOUT_REQUEST:
                View v = inflater.inflate(R.layout.friend_request_adapter,null);
                return new FriendRequestViewHolder(v);
            case LAYOUT_FRIEND:
                View _v = inflater.inflate(R.layout.friend_layout,null);
                return new FriendViewHolder(_v);
            default:
                View __v = inflater.inflate(R.layout.friend_layout,null);
                return new FriendViewHolder(__v);
        }
    }

    @Override
    public int getItemViewType(int i)
    {
        if(friendList.get(i).getFriend_id() == "0")
            return LAYOUT_REQUEST;
        return LAYOUT_FRIEND;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Friend friend = friendList.get(i);

        switch (viewHolder.getItemViewType())
        {
            case LAYOUT_REQUEST:
                FriendRequestViewHolder friendRequestViewHolder = (FriendRequestViewHolder)viewHolder;
                friendRequestViewHolder.friendName.setText(friend.getFriends_account_id());
                friendRequestViewHolder.friendImage.setImageResource(R.drawable.account_box_black_24dp);
                    friendRequestViewHolder.bind(friendList.get(i),acceptListener,declineListener,i);
                break;
            case LAYOUT_FRIEND:
                FriendViewHolder friendViewHolder = (FriendViewHolder)viewHolder;
                friendViewHolder.friendName.setText(friend.getFriends_account_id());
                friendViewHolder.image.setImageResource(R.mipmap.ic_launcher_round);
                friendViewHolder.bind(friendList.get(i),listener,i);
                break;
        }
        /*
        TODO ID'SİNİ DEĞİL DE İSMİNİ GETİR
         */
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView friendName;
        ImageView image;
        FriendViewHolder(@NonNull View v) {
            super(v);
            friendName = v.findViewById(R.id.adapter_friend_name);
            image = v.findViewById(R.id.friend_header_image);
        }
        void bind(final Friend item, final OnItemClickListener listener,final int position)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item,position);
                }
            });
        }
    }

    class FriendRequestViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;
        ImageView friendImage;
        Button accept,decline;
        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            friendImage = itemView.findViewById(R.id.friend_request_adapter_image);
            friendName = itemView.findViewById(R.id.friend_request_adapter_accountid);
            accept = itemView.findViewById(R.id.friend_request_accept);
            decline = itemView.findViewById(R.id.friend_request_decline);
        }

        void bind(final Friend item, final OnItemClickListener acceptListener, final OnItemClickListener declineListener, final int position)
        {
            itemView.findViewById(R.id.friend_request_accept).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    acceptListener.onItemClick(item,position);
                }
            });

            itemView.findViewById(R.id.friend_request_decline).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    declineListener.onItemClick(item,position);
                }
            });
        }
    }
}
