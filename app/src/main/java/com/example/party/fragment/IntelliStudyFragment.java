package com.example.party.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.party.R;
import com.example.party.activity.StudyActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class IntelliStudyFragment extends Fragment {
    private LinearLayout linearLayout_group;
    private LinearLayout linearLayout_materials;
    private LinearLayout linearLayout_paper;
    private LinearLayout linearLayout_check;
    private LinearLayout linearLayout_wrong;
    private OnFragmentInteractionListener mListener;
    private LinearLayout linearLayout_q_and_a;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_intelli_study, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initData() {
    }

    private void initView(View view) {
        linearLayout_group = view.findViewById(R.id.intelli_group);
        linearLayout_materials = view.findViewById(R.id.intelli_materials);
        linearLayout_paper = view.findViewById(R.id.intelli_personal_paper);
        linearLayout_check = view.findViewById(R.id.intelli_check);
        linearLayout_wrong = view.findViewById(R.id.intelli_worng);
        linearLayout_q_and_a = view.findViewById(R.id.intelli_study_q_and_a);
    }

    private void initListener() {

        //智能问答
        linearLayout_q_and_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "q_and_a");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });
        // 智能小组
        linearLayout_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "group");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

        // 学习资料
        linearLayout_materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "materials");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

        // 个人练习
        linearLayout_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "paper");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

        // 考核
        linearLayout_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "check");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

        // 错题库
        linearLayout_wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "wrong");
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
