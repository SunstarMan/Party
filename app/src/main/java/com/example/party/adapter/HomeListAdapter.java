package com.example.party.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.party.R;
import com.example.party.bean.News;
import com.example.party.util.ImageUrl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HomeListAdapter extends BaseAdapter {
    private Context context;
    private List<News> newsList;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    public HomeListAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.calendar = Calendar.getInstance();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.CHINA);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsList.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_home_listview, null, true);
            vh.img = view.findViewById(R.id.item_home_listview_image);
            vh.textTitle = view.findViewById(R.id.item_home_listview_title);
            vh.textTime = view.findViewById(R.id.item_home_listview_time);
            vh.textBrowse = view.findViewById(R.id.item_home_listview_browse);

            view.setTag(vh);
        } else {
            vh = (VH) view.getTag();
        }

        News news = newsList.get(i);
        if (news.getImg() != null) {
            Glide.with(context).load(news.getImg()).into(vh.img);
        } else {
            Glide.with(context).load(ImageUrl.NEWSDEFAULT).into(vh.img);
        }
        vh.textTitle.setText(news.getTitle());
        calendar.setTimeInMillis(news.getTimestamp());
        vh.textTime.setText(dateFormat.format(calendar.getTime()));
        vh.textBrowse.setText(String.valueOf(new Random().nextInt(100) + 1));
        return view;

    }

    private static class VH {
        private ImageView img;
        private TextView textTitle;
        private TextView textTime;
        private TextView textBrowse;
    }
}
