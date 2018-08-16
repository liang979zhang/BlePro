package com.zcf.blepro.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcf.blepro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragMent extends Fragment {
    @BindView(R.id.tv_now)
    TextView tvNow;
    Unbinder unbinder;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.ll_dianhuo)
    LinearLayout llDianhuo;
    @BindView(R.id.ll_daishu)
    LinearLayout llDaishu;
    @BindView(R.id.ll_jiashi)
    LinearLayout llJiashi;
    @BindView(R.id.ll_round)
    LinearLayout llRound;
    @BindView(R.id.rl_bootom)
    RelativeLayout rlBootom;

    public static HomeFragMent newInstance(String text) {
        HomeFragMent fragment = new HomeFragMent();
        Bundle args = new Bundle();
        args.putString("param", text);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        llState.setVisibility(View.GONE);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param");
            tvNow.setText(mParam1);
            llState.setVisibility(View.VISIBLE);

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_dianhuo, R.id.ll_daishu, R.id.ll_jiashi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dianhuo:
                break;
            case R.id.ll_daishu:
                break;
            case R.id.ll_jiashi:
                break;
        }
    }
}
