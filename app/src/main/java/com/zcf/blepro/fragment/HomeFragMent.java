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

import com.triggertrap.seekarc.SeekArc;
import com.zcf.blepro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragMent extends Fragment {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.tv_now)
    TextView tvNow;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.ll_dianhuo)
    LinearLayout llDianhuo;
    @BindView(R.id.ll_daishu)
    LinearLayout llDaishu;
    @BindView(R.id.ll_jiashi)
    LinearLayout llJiashi;
    @BindView(R.id.seekArc)
    SeekArc seekArc;
    @BindView(R.id.ll_round)
    LinearLayout llRound;
    @BindView(R.id.rl_bootom)
    RelativeLayout rlBootom;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_dianhuo)
    ImageView ivDianhuo;
    @BindView(R.id.iv_daisu)
    ImageView ivDaisu;
    @BindView(R.id.iv_jiashi)
    ImageView ivJiashi;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.iv_kaiguan)
    ImageView ivKaiguan;

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
        ButterKnife.bind(this, view);
        llState.setVisibility(View.GONE);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param");
            tvNow.setText(mParam1);
            llState.setVisibility(View.VISIBLE);

        }
        seekArc.setSweepAngle(90);
        seekArc.setArcRotation(240);
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
                String testStr = getResources().getString(R.string.num_text);
                String result = String.format(testStr, progress);
                tvNum.setText(result);
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ll_dianhuo, R.id.ll_daishu, R.id.ll_jiashi, R.id.iv_save, R.id.iv_kaiguan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dianhuo:
                ivDianhuo.setSelected(true);
                ivDaisu.setSelected(false);
                ivJiashi.setSelected(false);

                break;
            case R.id.ll_daishu:
                ivDianhuo.setSelected(false);
                ivDaisu.setSelected(true);
                ivJiashi.setSelected(false);
                break;
            case R.id.ll_jiashi:

                ivDianhuo.setSelected(false);
                ivDaisu.setSelected(false);
                ivJiashi.setSelected(true);
                break;
            case R.id.iv_kaiguan:

                break;
            case R.id.iv_save:
                break;
        }
    }
}
