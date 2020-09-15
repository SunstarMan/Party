package com.example.party.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.party.R;
import com.example.party.bean.Personas;
import com.example.party.bean.User;
import com.example.party.net.PersonNet;
import com.example.party.util.UserInfo;
import com.example.party.view.WordCloudView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class MineFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Button button_to_signin;
    private ConstraintLayout constraintLayout_about;
    private ConstraintLayout constraintLayoutLogout;
    private ConstraintLayout constraintLayoutLogoutWin;
    private LinearLayout linearLayout_account_manage;
    private ImageView imgHead;
    private TextView textUsername;
    private TextView textMotto;
    private WordCloudView wcv;
    private Button btnLogoutCancel;
    private Button btnLogoutConfirm;
    private PersonNet personNet;
    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        initListener();
        return view;
    }

    public PersonNet getPersonNet() {
        return personNet;
    }

    private void initData() {
        gson = new Gson();
        personNet = new PersonNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    JSONObject jsonObject = new JSONObject((String) msg.obj);
                    int max = jsonObject.getInt("max");
                    int min = jsonObject.getInt("min");
                    JSONArray jsonArray = jsonObject.getJSONArray("personas");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Personas personas = gson.fromJson(jsonArray.getJSONObject(i).toString(), Personas.class);
                        wcv.addTextView(personas.getTag(), calSize(personas.getFrequency(), max, min));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int calSize(int frequency, int max, int min) {
        int x = frequency - min;
        int y = max - min;
        return (int) (12 + 12.0 * x / y);
    }

    public Button getButton_to_signin() {
        return button_to_signin;
    }

    public TextView getTextUsername() {
        return textUsername;
    }

    public TextView getTextMotto() {
        return textMotto;
    }

    public ImageView getImgHead() {
        return imgHead;
    }

    private void initView(View view) {
        button_to_signin = view.findViewById(R.id.mine_to_signin_btn);
        constraintLayout_about = view.findViewById(R.id.mine_about);
        constraintLayoutLogout = view.findViewById(R.id.mine_logout);
        constraintLayoutLogoutWin = view.findViewById(R.id.mine_constrain_logout);
        linearLayout_account_manage = view.findViewById(R.id.mine_account_manage);
        imgHead = view.findViewById(R.id.mine_user_head_img);
        textUsername = view.findViewById(R.id.mine_text_username);
        textMotto = view.findViewById(R.id.mine_text_motto);
        btnLogoutCancel = view.findViewById(R.id.mine_btn_logout_cancel);
        btnLogoutConfirm = view.findViewById(R.id.mine_btn_logout_confirm);
        wcv = view.findViewById(R.id.frag_mine_wcv);
    }

    private void initListener() {
        constraintLayoutLogout.setOnClickListener(v -> {
            constraintLayoutLogoutWin.setVisibility(View.VISIBLE);
        });

        btnLogoutCancel.setOnClickListener(v -> {
            constraintLayoutLogoutWin.setVisibility(View.GONE);
        });

        btnLogoutConfirm.setOnClickListener(v -> {
            constraintLayoutLogoutWin.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("action", "logout");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListener.onFragmentInteraction(jsonObject);
        });

        button_to_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "sign_in");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

        constraintLayout_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "about");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onFragmentInteraction(object);
            }
        });

        linearLayout_account_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action", "account_manage");
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
