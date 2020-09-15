package com.example.party.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.party.R;
import com.example.party.adapter.MainViewPagerAdapter;
import com.example.party.bean.User;
import com.example.party.fragment.HomeFragment;
import com.example.party.fragment.IntelliStudyFragment;
import com.example.party.fragment.MineFragment;
import com.example.party.fragment.NewsFragment;
import com.example.party.net.LoginNet;
import com.example.party.util.ImageUrl;
import com.example.party.util.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, IntelliStudyFragment.OnFragmentInteractionListener, MineFragment.OnFragmentInteractionListener, NewsFragment.OnFragmentInteractionListener {
    private List<Fragment> fragments;//定义fragments链表
    private HomeFragment homeFragment;//定义homeFragment
    private IntelliStudyFragment intelliStudyFragment;
    private NewsFragment newsFragment;
    private MineFragment mineFragment;

    private ViewPager viewPager;
    private MainViewPagerAdapter mainViewPagerAdapter;//定义连接后端数据和前端显示的适配器接口adapter(这里是主界面的adapter)

    private List<ImageView> imgIcons;//定义一个用来管理图形icon的链表imgIcons
    private List<TextView> textTitles;//定义一个用来管理文字view的链表textTitles

    private List<LinearLayout> linearLayoutList;
    private LinearLayout linearHome;//定义整个Home的LinearLayout
    private LinearLayout linearIntelliStudy;
    private LinearLayout linearNews;
    private LinearLayout linearMine;

    private Gson gson;
    private SharedPreferences preferences;

    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    public void initData() {
        //将home、intellistudy、news、mine四个fragments添加到链表fragments中
        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        intelliStudyFragment = new IntelliStudyFragment();
        fragments.add(intelliStudyFragment);
        newsFragment = new NewsFragment();
        fragments.add(newsFragment);
        mineFragment = new MineFragment();
        fragments.add(mineFragment);

        imgIcons = new ArrayList<>();
        textTitles = new ArrayList<>();

        gson = new Gson();
        preferences = getSharedPreferences("Party", MODE_PRIVATE);
        UserInfo.loadUserInfo(preferences);
        new LoginNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = (String) msg.obj;
                if (!response.equals("error")) {
                    UserInfo.user.setState(User.LOGIN);
                    loginSuccess(UserInfo.user);
                }
            }
        }).post(UserInfo.user.getAccount(), UserInfo.user.getPassword());
    }

    public void initView() {
        viewPager = findViewById(R.id.main_viewPager);
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 1, fragments);
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

        imgIcons.add(findViewById(R.id.icon_label_home));
        imgIcons.add(findViewById(R.id.icon_label_intelli_study));
        imgIcons.add(findViewById(R.id.icon_label_message));
        imgIcons.add(findViewById(R.id.icon_label_mine));

        textTitles.add(findViewById(R.id.text_label_home));
        textTitles.add(findViewById(R.id.text_label_intelli_study));
        textTitles.add(findViewById(R.id.text_label_message));
        textTitles.add(findViewById(R.id.text_label_mine));

        linearHome = findViewById(R.id.label_home);
        linearIntelliStudy = findViewById(R.id.label_intelli_study);
        linearNews = findViewById(R.id.label_message);
        linearMine = findViewById(R.id.label_mine);

        linearLayoutList = new ArrayList<>();
        linearLayoutList.add(linearHome);
        linearLayoutList.add(linearIntelliStudy);
        linearLayoutList.add(linearNews);
        linearLayoutList.add(linearMine);
    }

    public void initListener() {
        linearHome.setOnClickListener(v -> {
            changePage(0);
            viewPager.setCurrentItem(0);
        });

        linearIntelliStudy.setOnClickListener(v -> {
            changePage(1);
            viewPager.setCurrentItem(1);
        });

        linearNews.setOnClickListener(v -> {
            changePage(2);
            viewPager.setCurrentItem(2);
        });

        linearMine.setOnClickListener(v -> {
            changePage(3);
            viewPager.setCurrentItem(3);
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

    //    0xffff4400 红色
    //    0xffb0b2bf 灰色
    private void changePage(int i) {
        for (ImageView imgIcon : imgIcons) {
            imgIcon.setImageTintList(ColorStateList.valueOf(0xffb0b2bf));
        }
        for (TextView textTitle : textTitles) {
            textTitle.setTextColor(0xffb0b2bf);
        }
        imgIcons.get(i).setImageTintList(ColorStateList.valueOf(0xffff4400));
        textTitles.get(i).setTextColor(0xffff4400);
    }

    @Override
    public void onFragmentInteraction(JSONObject jsonObject) {
        String fragment = null;
        try {
            fragment = (String) jsonObject.get("action");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert fragment != null;
        if (fragment.equals("self_think")) {
            startActivity(new Intent(MainActivity.this, SelfThinkActivity.class));
        }
        // 登录页
        if (fragment.equals("sign_in")) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivityForResult(intent, MainActivity.LOGIN_REQUEST);
        }
        if (fragment.equals("logout")) {
            deleteUser();
            logout();
        }
        if (fragment.equals("about")) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        if (fragment.equals("q_and_a")) {
            startActivity(new Intent(MainActivity.this, IntelliQAActivity.class));
        }
        if (fragment.equals("account_manage")) {
            startActivity(new Intent(MainActivity.this, AccountManageActivity.class));
        }
        if (fragment.equals("group")) {
            startActivity(new Intent(MainActivity.this, StudyActivity.class).putExtra("party_study", 0));
        }
        if (fragment.equals("materials")) {
            startActivity(new Intent(MainActivity.this, StudyActivity.class).putExtra("party_study", 1));
        }
        if (fragment.equals("paper")) {
            startActivity(new Intent(MainActivity.this, TestActivity.class).putExtra("party_test", 0));
        }
        if (fragment.equals("check")) {
            startActivity(new Intent(MainActivity.this, TestActivity.class).putExtra("party_test", 1));
        }
        if (fragment.equals("wrong")) {
            startActivity(new Intent(MainActivity.this, TestActivity.class).putExtra("party_test", 2));
        }
        if (fragment.equals("web")) {
            try {
                String url = jsonObject.getString("url");
                startActivity(new Intent(MainActivity.this, WebActivity.class).putExtra("url", url));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.LOGIN_REQUEST && resultCode == MainActivity.LOGIN_SUCCESS) {
            if (data != null) {
                User user = gson.fromJson(data.getStringExtra("user"), User.class);
                user.setState(User.LOGIN);
                UserInfo.user = user;
                saveUser(user);
                loginSuccess(user);
            }
        }
    }

    private void deleteUser() {
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove("account");
        edit.apply();
        UserInfo.user.setState(User.LOGOUT);
    }

    private void logout() {
        mineFragment.getTextMotto().setText("编辑你的个性签名吧！");
        mineFragment.getTextUsername().setText("未登录");
        mineFragment.getButton_to_signin().setVisibility(View.VISIBLE);
        mineFragment.getImgHead().setImageResource(R.drawable.shape_circular);
    }

    private void saveUser(User user) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("account", user.getAccount());
        edit.putString("password", user.getPassword());
        edit.putString("username", user.getUsername());
        edit.putString("motto", user.getMotto());
        edit.putString("icon", user.getIcon());
        edit.putString("identity", user.getIdentity());
        edit.putString("branch", user.getBranch());
        edit.apply();
    }

    private void loginSuccess(User user) {
        mineFragment.getTextUsername().setText(user.getUsername());
        mineFragment.getTextMotto().setText(user.getMotto());
        if (user.getIcon() == null) {
            Glide.with(this)
                    .load(ImageUrl.HEAD_DEFAULT)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(mineFragment.getImgHead());
        } else {
            Glide.with(this)
                    .load(user.getIcon())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(mineFragment.getImgHead());
        }
        mineFragment.getButton_to_signin().setVisibility(View.GONE);
        mineFragment.getPersonNet().postPersonas(user.getAccount());
    }
}
