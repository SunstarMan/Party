package com.example.party.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.party.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TestCheckFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_test_check, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initData() {
    }

    private void initView(View view) {
        button = view.findViewById(R.id.test_check_button1);

    }


    private void initListener() {
        button.setOnClickListener(view -> {
            JSONObject object = new JSONObject();
            try {
                object.put("tiaozhuan","paper_question");
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
