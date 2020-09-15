package com.example.party.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.party.R;
import com.example.party.bean.User;
import com.example.party.net.QuestionNet;
import com.example.party.util.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class TestPaperFragment extends Fragment {
    private ImageView imageView_begin;
    private TextView textErrorNumber;
    private TextView textSolvingNumber;
    private TextView textProgress;
    private ProgressBar progressBar;
    private QuestionNet questionNet;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_paper, container, false);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initData() {
        questionNet = new QuestionNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == QuestionNet.SOLVING) {
                    String[] results = ((String) msg.obj).split("-");
                    int all = Integer.parseInt(results[1]);
                    int solved = Integer.parseInt(results[0]);
                    textProgress.setText(results[0] + " / " + results[1]);
                    textSolvingNumber.setText(String.valueOf(all - solved));
                    progressBar.setProgress((int) (solved * 100.0 / all));
                } else if (msg.what == QuestionNet.ERROR) {
                    textErrorNumber.setText((String) msg.obj);
                }
            }
        });
    }

    private void initView(View view) {
        imageView_begin = view.findViewById(R.id.test_paper_begin_frame);
        textErrorNumber = view.findViewById(R.id.frag_test_paper_text_error_number);
        textSolvingNumber = view.findViewById(R.id.frag_test_paper_text_solving_number);
        textProgress = view.findViewById(R.id.frag_test_paper_text_progress);
        progressBar = view.findViewById(R.id.frag_test_paper_progress);
        Log.i("user", String.valueOf(UserInfo.user.getState()));
        if (UserInfo.user.getState() == User.LOGIN) {
            questionNet.postErrorNumber(UserInfo.user.getAccount());
            questionNet.postSolvingNumber(UserInfo.user.getAccount());
        }
    }

    private void initListener() {
        imageView_begin.setOnClickListener(view -> {
            JSONObject object = new JSONObject();
            try {
                object.put("tiaozhuan", "paper_question");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListener.onFragmentInteraction(object);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserInfo.user.getState() == User.LOGIN) {
            questionNet.postErrorNumber(UserInfo.user.getAccount());
            questionNet.postSolvingNumber(UserInfo.user.getAccount());
        }
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
