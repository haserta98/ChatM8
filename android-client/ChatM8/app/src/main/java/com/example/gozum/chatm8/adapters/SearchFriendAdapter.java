package com.example.gozum.chatm8.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gozum.chatm8.OnItemClickListener;
import com.example.gozum.chatm8.R;
import com.example.gozum.chatm8.entities.Friend;
import com.example.gozum.chatm8.entities.User;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<User> userList;
    private Context ctx;
    private OnItemClickListener listener;
    public SearchFriendAdapter(Context ctx, List<User> userList, OnItemClickListener listener)
    {
        this.ctx = ctx;
        this.userList = userList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflator = LayoutInflater.from(ctx);
        View v = inflator.inflate(R.layout.search_friend_adapter,null);
        return new FriendsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        User user = userList.get(i);
        FriendsViewHolder friendsViewHolder = (FriendsViewHolder)viewHolder;
        friendsViewHolder.accountid.setText(user.getAccount_id());

        /*
        TODO RESMI VERITABANINDAN ÇEK
         */
        friendsViewHolder.profilePhoto.setImageResource(R.mipmap.ic_launcher_round);
        friendsViewHolder.bind(userList.get(i),listener,i);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class FriendsViewHolder extends RecyclerView.ViewHolder
    {
        TextView accountid;
        ImageView profilePhoto;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            accountid = itemView.findViewById(R.id.search_friend_adapter_account_id);
            profilePhoto = itemView.findViewById(R.id.search_friend_adapter_profile_photo);
        }
        void bind(final User userid, final OnItemClickListener listener, final int position)
        {
            itemView.findViewById(R.id.search_friend_add_friend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(userid,position);
                }
            });
        }
    }
}
