package com.example.party.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.party.R;
import com.example.party.adapter.GroupRecyclerAdapter;
import com.example.party.bean.User;
import com.example.party.net.GroupNet;
import com.example.party.util.UserInfo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudyGroupFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private TextView textGroupName;
    private ImageView imgMember1;
    private ImageView imgMember2;
    private ImageView imgMember3;
    private ImageView imgSelf;

    private GroupRecyclerAdapter groupRecyclerAdapter;
    private List<User> groupMembers;
    private GroupNet groupNet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_group, container, false);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initData() {
        groupMembers = new ArrayList<>();
        groupRecyclerAdapter = new GroupRecyclerAdapter(groupMembers);
        groupNet = new GroupNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Gson gson = new Gson();
                try {
                    JSONObject result = new JSONObject((String) msg.obj);
                    JSONArray members = result.getJSONArray("members");
                    groupMembers.clear();
                    for (int i = 0; i < members.length(); i++) {
                        groupMembers.add(gson.fromJson(members.getJSONObject(i).toString(), User.class));
                        Log.i("study",groupMembers.get(i).toString());
                    }
                    groupRecyclerAdapter.notifyDataSetChanged();

                    textGroupName.setText(result.getString("groupName"));
                    Glide.with(getContext())
                            .load(groupMembers.get(0).getIcon())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(imgMember1);
                    Glide.with(getContext())
                            .load(groupMembers.get(1).getIcon())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(imgMember2);
                    Glide.with(getContext())
                            .load(groupMembers.get(2).getIcon())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(imgMember3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.frag_study_group_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(groupRecyclerAdapter);
        textGroupName = view.findViewById(R.id.frag_study_group_text_group_name);
        imgMember1 = view.findViewById(R.id.study_group_member1);
        imgMember2 = view.findViewById(R.id.study_group_member2);
        imgMember3 = view.findViewById(R.id.study_group_member3);
        imgSelf = view.findViewById(R.id.study_group_portrait);
        if (UserInfo.user.getState() == User.LOGIN) {
            Glide.with(this)
                    .load(UserInfo.user.getIcon())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imgSelf);
            groupNet.post(UserInfo.user.getAccount());
        }
    }

    private void initListener() {

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
