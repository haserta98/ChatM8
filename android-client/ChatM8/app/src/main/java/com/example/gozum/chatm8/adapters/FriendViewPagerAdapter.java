package com.example.gozum.chatm8.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gozum.chatm8.OnItemClickListener;
import com.example.gozum.chatm8.R;

import java.util.ArrayList;
import java.util.List;


public class FriendViewPagerAdapter extends PagerAdapter {
    private Context ctx;
    private List<FriendViewPager> friendViewPagers;
    private OnItemClickListener listener;
    public FriendViewPagerAdapter(Context ctx, List<FriendViewPager> friendViewPagers, OnItemClickListener listener)
    {
        this.ctx = ctx;
        this.friendViewPagers = friendViewPagers;
        this.listener = listener;
    }

    @Override
    public float getPageWidth(int position) {
        return(0.2f);
    }

    @Override
    public int getItemPosition(Object object)
    {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.friend_view_pager_for_create_room, collection, false);

        TextView view = layout.findViewById(R.id.friendViewPagerName);
        ImageView image = layout.findViewById(R.id.friendViewPagerImage);

        view.setText(friendViewPagers.get(position).getName());
        image.setImageResource(R.mipmap.ic_launcher_round);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(friendViewPagers.get(position),position);
            }
        });
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return friendViewPagers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "anan";
    }


}
