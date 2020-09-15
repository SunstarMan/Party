package com.example.party.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.party.R;
import com.example.party.bean.Message;
import com.example.party.bean.NewsBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<Message> messages;
    private SimpleDateFormat dateFormat;

    public NewsAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.CHINA);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh;
        if (view == null) {
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_news, null, true);
            vh.img = view.findViewById(R.id.item_news_image);
            vh.textTitle = view.findViewById(R.id.item_news_title);
            vh.textTime = view.findViewById(R.id.item_news_time);
            vh.textAddress = view.findViewById(R.id.item_news_address);
            view.setTag(vh);
        } else {
            vh = (VH) view.getTag();
        }

        Message message = messages.get(i);
        Glide.with(context).load(message.getIcon()).into(vh.img);
        vh.textTitle.setText(message.getTitle());
        vh.textTime.setText(dateFormat.format(new Date(message.getTimestamp())));
        vh.textAddress.setText(message.getLocation());
        return view;
    }

    private static class VH {
        private ImageView img;
        private TextView textTitle;
        private TextView textTime;
        private TextView textAddress;
    }
}
