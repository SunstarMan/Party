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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.party.R;
import com.example.party.adapter.StudyMaterialsAdapter;
import com.example.party.bean.StudyMaterial;
import com.example.party.net.StudyMaterialNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterialsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView listMaterials;
    private List<StudyMaterial> studyMaterials;
    private StudyMaterialsAdapter studyMaterialsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_study_materials, container, false);
        listMaterials = view.findViewById(R.id.study_materials_listView);
        listMaterials.setAdapter(studyMaterialsAdapter);
        initListener();
        return view;
    }


    private void initData() {
        studyMaterials = new ArrayList<>();
        studyMaterialsAdapter = new StudyMaterialsAdapter(getContext(), studyMaterials);
        new StudyMaterialNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                studyMaterials.addAll((List<StudyMaterial>) msg.obj);
                studyMaterialsAdapter.notifyDataSetChanged();
            }
        }).post(-1, -1);
    }

    private void initListener() {
        listMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("action", "web");
                    jsonObject.put("url", studyMaterials.get(position).getUrl());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(jsonObject);
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
