package com.zcf.blepro.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zcf.blepro.R;

import java.util.List;

public class MianAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public MianAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }
}
