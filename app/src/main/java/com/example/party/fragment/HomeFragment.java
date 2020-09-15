package com.example.party.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.party.R;
import com.example.party.adapter.HomeListAdapter;
import com.example.party.bean.News;
import com.example.party.net.NewsNet;
import com.example.party.util.GlideImageLoader;
import com.youth.banner.Banner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Banner banner;
    private HomeListAdapter homeListAdapter;
    private ListView listHomeLists;
    private List<News> newsList;
    private List<String> imgUrls;
    private LinearLayout linearLayout_home_materials;
    private LinearLayout linearLayout_selfthink;
    private LinearLayout linearLayoutSign;
    private ConstraintLayout constraintLayoutSign;
    private TextView textSignConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initData() {
        //新闻列表
        newsList = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(getContext(), newsList);
        new NewsNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                newsList.addAll((List<News>) msg.obj);
                homeListAdapter.notifyDataSetChanged();
            }
        }).post(-1, -1);

        //轮播台图片
        imgUrls = new ArrayList<>();
        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573025009&di=dec0738d1cc2a4d01206e6c90abdb5ac&imgtype=jpg&er=1&src=http%3A%2F%2Fi2.hexun.com%2F2019-09-30%2F198732748.jpg");
        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572446758151&di=9861f5328149f1fca807149199a263f9&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171113%2F43ecefecf79a4c8c9c83db2cdf4f5c93.jpeg");
    }

    private void initView(View view) {
        linearLayout_selfthink = view.findViewById(R.id.home_self_think);
        linearLayout_home_materials = view.findViewById(R.id.home_study_materials);
        listHomeLists = view.findViewById(R.id.home_listView);
        listHomeLists.setAdapter(homeListAdapter);
        linearLayoutSign = view.findViewById(R.id.sign_in);
        constraintLayoutSign = view.findViewById(R.id.frag_home_cl_sign);
        textSignConfirm = view.findViewById(R.id.frag_home_text_sign);

        banner = view.findViewById(R.id.home_banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgUrls);
        banner.start();
    }


    private void initListener() {
        linearLayoutSign.setOnClickListener(v -> {
            constraintLayoutSign.setVisibility(View.VISIBLE);
        });

        textSignConfirm.setOnClickListener(v -> {
            constraintLayoutSign.setVisibility(View.GONE);
        });
        // 跳转到心得体会页
        linearLayout_selfthink.setOnClickListener(view -> {
            JSONObject object = new JSONObject();
            try {
                object.put("action", "self_think");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListener.onFragmentInteraction(object);
        });

        // 跳转到学习资料页
        linearLayout_home_materials.setOnClickListener(view -> {
            JSONObject object = new JSONObject();
            try {
                object.put("action", "materials");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListener.onFragmentInteraction(object);
        });

        listHomeLists.setOnItemClickListener((parent, view, position, id) -> {
            JSONObject object = new JSONObject();
            try {
                object.put("action", "web");
                object.put("url", newsList.get(position).getUrl());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListener.onFragmentInteraction(object);
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
