package com.zcf.blepro.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zcf.blepro.MainActivity;
import com.zcf.blepro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragMent extends Fragment {

    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_1, R.id.rl_2, R.id.rl_3})
    public void onViewClicked(View view) {
        String a = null;
        MainActivity mainActivity = (MainActivity) getActivity();

        switch (view.getId()) {
            case R.id.rl_1:
                mainActivity.reFragment("V6 engine");
                break;
            case R.id.rl_2:
                mainActivity.reFragment("V8 engine");
                break;
            case R.id.rl_3:
                mainActivity.reFragment("miss firng");
                break;
        }
    }
}
