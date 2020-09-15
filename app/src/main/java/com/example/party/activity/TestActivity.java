package com.example.party.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.party.R;
import com.example.party.adapter.StudyViewPagerAdapter;
import com.example.party.adapter.TestViewPagerAdapter;
import com.example.party.fragment.TestCheckFragment;
import com.example.party.fragment.TestPaperFragment;
import com.example.party.fragment.TestWrongFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements TestPaperFragment.OnFragmentInteractionListener, TestCheckFragment.OnFragmentInteractionListener, TestWrongFragment.OnFragmentInteractionListener {
    private List<Fragment> fragments;
    private TestPaperFragment testPaperFragment;
    private TestCheckFragment testCheckFragment;
    private TestWrongFragment testWrongFragment;

    private ViewPager viewPager;
    private TestViewPagerAdapter testViewPagerAdapter;

    private List<TextView> testTextTitles;
    private List<LinearLayout> linearLayoutList;
    private LinearLayout linearTestPaper;
    private LinearLayout linearTestCheck;
    private LinearLayout linearTestWrong;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        fragments = new ArrayList<>();
        testPaperFragment = new TestPaperFragment();
        fragments.add(testPaperFragment);
        testCheckFragment = new TestCheckFragment();
        fragments.add(testCheckFragment);
        testWrongFragment = new TestWrongFragment();
        fragments.add(testWrongFragment);
        testTextTitles = new ArrayList<>();
    }

    private void initView() {
        Intent intent = getIntent();
        int party_test = intent.getIntExtra("party_test", 0);
        viewPager = findViewById(R.id.test_viewPager);
        testViewPagerAdapter = new TestViewPagerAdapter(getSupportFragmentManager(), 1, fragments);
        viewPager.setAdapter(testViewPagerAdapter);
        viewPager.setCurrentItem(party_test);
        viewPager.setOffscreenPageLimit(3);
        testTextTitles.add(findViewById(R.id.text_test_group));
        testTextTitles.add(findViewById(R.id.text_test_check));
        testTextTitles.add(findViewById(R.id.text_test_wrong));
        changePage(party_test);
        linearTestPaper = findViewById(R.id.layout_test_personal_paper);
        linearTestCheck = findViewById(R.id.layout_test_check);
        linearTestWrong = findViewById(R.id.layout_test_wrong);
        linearLayoutList = new ArrayList<>();
        linearLayoutList.add(linearTestPaper);
        linearLayoutList.add(linearTestCheck);
        linearLayoutList.add(linearTestWrong);
    }

    private void initListener() {
        linearTestPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(0);
                viewPager.setCurrentItem(0);
            }
        });
        linearTestCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(1);
                viewPager.setCurrentItem(1);
            }
        });
        linearTestWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(2);
                viewPager.setCurrentItem(2);
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

    }

    private void changePage(int i) {
        for (TextView textTitle : testTextTitles) {
            textTitle.setTextColor(0xffb0b2bf);
        }
        testTextTitles.get(i).setTextColor(0xffff4400);
    }

    @Override
    public void onFragmentInteraction(JSONObject jsonObject) {
        String fragment = "";
        try {
            fragment = (String) jsonObject.get("tiaozhuan");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (fragment.equals("paper_question")) {
            startActivity(new Intent(TestActivity.this, PaperQuestionActivity.class));
        } else if (fragment.equals("error-bank")) {
            startActivity(new Intent(TestActivity.this, ErrorBankActivity.class));
        }
    }
}
