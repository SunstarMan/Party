package com.example.party.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.party.R;
import com.example.party.adapter.NewsAdapter;
import com.example.party.bean.Message;
import com.example.party.bean.NewsBean;
import com.example.party.net.NewsNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private NewsAdapter newsAdapter;
    private ListView listNews;
    private List<Message> messages;
    private Button button_system_notice_go;

    private NewsNet newsNet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listNews = view.findViewById(R.id.news_listView);
        listNews.setAdapter(newsAdapter);
        initView(view);
        initListener();
        return view;
    }


    private void initData() {
        messages = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), messages);
        newsNet = new NewsNet(new Handler() {
            @Override
            public void handleMessage(@NonNull android.os.Message msg) {
                messages.addAll((List<Message>) msg.obj);
                newsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView(View view) {
        button_system_notice_go = view.findViewById(R.id.news_system_notice_go_btn);
        newsNet.postMessages();
    }


    private void initListener() {
        button_system_notice_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "about");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(JSONObject jsonObject);
    }
}
