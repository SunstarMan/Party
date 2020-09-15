package com.example.party.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.party.R;
import com.example.party.bean.User;
import com.example.party.net.QuestionNet;
import com.example.party.util.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;


public class TestWrongFragment extends Fragment {
    private TextView textWrongZG;
    private ImageView imgZG;

    private QuestionNet questionNet;
    private OnFragmentInteractionListener mListener;

    public TestWrongFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_wrong, container, false);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initData() {
        questionNet = new QuestionNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                textWrongZG.setText("错题数：" + msg.obj);
            }
        });
    }

    private void initView(View view) {
        textWrongZG = view.findViewById(R.id.frag_test_wrong_text_zonggang);
        if (UserInfo.user.getState() == User.LOGIN) {
            questionNet.postErrorNumber(UserInfo.user.getAccount());
        }
        imgZG = view.findViewById(R.id.test_wrong_option_1_bg);
    }

    private void initListener() {
        imgZG.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("tiaozhuan", "error-bank");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListener.onFragmentInteraction(jsonObject);
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
