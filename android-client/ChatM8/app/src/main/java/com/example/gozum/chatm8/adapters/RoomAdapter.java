package com.example.gozum.chatm8.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gozum.chatm8.OnItemClickListener;
import com.example.gozum.chatm8.R;
import com.example.gozum.chatm8.entities.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Context ctx;
    private List<Room> roomList;
    private OnItemClickListener listener;

    public RoomAdapter(Context ctx, List<Room> roomList,OnItemClickListener listener) {
        this.ctx = ctx;
        this.roomList = roomList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.home_room,null);
        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder roomViewHolder, int i) {
        Room room = roomList.get(i);

        roomViewHolder.content.setText(String.valueOf(room.getRoomid()));
        roomViewHolder.name.setText(room.getRoomname());
        roomViewHolder.image.setImageResource(R.mipmap.ic_launcher_round);
        roomViewHolder.bind(roomList.get(i),listener,i);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder{
        TextView name,content;
        ImageView image;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.adapter_name);
            content = itemView.findViewById(R.id.adapter_content);
            image = itemView.findViewById(R.id.adapter_image);
        }

        void bind(final Room item,final OnItemClickListener listener,final int position)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item,position);
                }
            });
        }
    }
}
