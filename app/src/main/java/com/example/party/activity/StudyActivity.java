package com.example.party.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.party.LoginActivity;
import com.example.party.R;
import com.example.party.adapter.StudyViewPagerAdapter;
import com.example.party.fragment.StudyGroupFragment;
import com.example.party.fragment.StudyMaterialsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity implements StudyGroupFragment.OnFragmentInteractionListener, StudyMaterialsFragment.OnFragmentInteractionListener {
    private List<Fragment> fragments;
    private StudyGroupFragment studyGroupFragment;
    private StudyMaterialsFragment studyMaterialsFragment;

    private ViewPager viewPager;
    private StudyViewPagerAdapter studyViewPagerAdapter;

    private List<TextView> textTitles;
    private List<LinearLayout> linearLayoutList;
    private LinearLayout linearStudyGroup;
    private LinearLayout linearStudyMaterials;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initData();
        initView();
        initListener();
    }


    private void initData() {
        Bundle bundle = new Bundle();
        fragments = new ArrayList<>();
        studyGroupFragment = new StudyGroupFragment();
        fragments.add(studyGroupFragment);
        studyMaterialsFragment = new StudyMaterialsFragment();
        fragments.add(studyMaterialsFragment);
        textTitles = new ArrayList<>();
    }

    private void initView() {
        Intent intent = getIntent();
        int party_study = intent.getIntExtra("party_study", 0);
        viewPager = findViewById(R.id.study_viewPager);
        studyViewPagerAdapter = new StudyViewPagerAdapter(getSupportFragmentManager(), 1, fragments);
        viewPager.setAdapter(studyViewPagerAdapter);
        viewPager.setCurrentItem(party_study);
        viewPager.setOffscreenPageLimit(3);
        textTitles.add(findViewById(R.id.text_study_group));
        textTitles.add(findViewById(R.id.text_study_materials));
        changePage(party_study);
        linearStudyGroup = findViewById(R.id.layout_study_group);
        linearStudyMaterials = findViewById(R.id.layout_study_materials);
        linearLayoutList = new ArrayList<>();
        linearLayoutList.add(linearStudyGroup);
        linearLayoutList.add(linearStudyMaterials);

        imgBack = findViewById(R.id.study_return_to_intelli);
    }

    private void initListener() {
        linearStudyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(0);
                viewPager.setCurrentItem(0);
            }
        });
        linearStudyMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(1);
                viewPager.setCurrentItem(1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                changePage(i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 返回按钮
        imgBack.setOnClickListener((view) -> {
            finish();
        });
    }

    private void changePage(int i) {
        for (TextView textTitle : textTitles) {
            textTitle.setTextColor(0xffb0b2bf);
        }
        textTitles.get(i).setTextColor(0xffff4400);
    }

    @Override
    public void onFragmentInteraction(JSONObject jsonObject) {
        String action = "";
        try {
            action = jsonObject.getString("action");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (action.equals("web")) {
            try {
                String url = jsonObject.getString("url");
                startActivity(new Intent(StudyActivity.this, WebActivity.class).putExtra("url", url));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
