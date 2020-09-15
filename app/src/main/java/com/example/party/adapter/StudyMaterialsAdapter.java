package com.example.party.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.party.R;
import com.example.party.bean.StudyMaterial;

import java.util.List;
import java.util.Random;

public class StudyMaterialsAdapter extends BaseAdapter {
    private Context context;
    private List<StudyMaterial> studyMaterials;

    public StudyMaterialsAdapter(Context context, List<StudyMaterial> studyMaterials) {
        this.context = context;
        this.studyMaterials = studyMaterials;
    }

    @Override
    public int getCount() {
        return studyMaterials.size();
    }

    @Override
    public Object getItem(int i) {
        return studyMaterials.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        VH vh;
        if (view == null) {
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_study_materials_listview, null, true);
            vh.textTitle = view.findViewById(R.id.item_study_materials_title);
            vh.textDemand = view.findViewById(R.id.item_study_materials_demand);
            vh.textBrowse = view.findViewById(R.id.item_study_materials_browse);
            view.setTag(vh);
        } else {
            vh = (StudyMaterialsAdapter.VH) view.getTag();
        }

        StudyMaterial studyMaterial = studyMaterials.get(i);
        vh.textTitle.setText(studyMaterial.getTitle());
        vh.textDemand.setText("要求：" + studyMaterial.getDemand());
        vh.textBrowse.setText((new Random().nextInt(100) + 10) + "次浏览");
        return view;
    }

    private static class VH {
        private TextView textTitle;
        private TextView textDemand;
        private TextView textBrowse;
    }
}
