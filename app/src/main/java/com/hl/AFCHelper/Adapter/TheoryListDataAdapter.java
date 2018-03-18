package com.hl.AFCHelper.Adapter;

import android.content.Context;

import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.R;

import java.util.ArrayList;
import java.util.List;


public class TheoryListDataAdapter extends BaseRecyclerAdapter<Data, BaseRecyclerAdapter.BaseViewHolder> {
    private Context mContext;
    public TheoryListDataAdapter(Context context,int layoutResId, ArrayList<Data> data) {
        super(layoutResId, data);
        mContext = context;
    }
    @Override
    protected void bindTheData(BaseRecyclerAdapter.BaseViewHolder holder, Data data, int position) {
    holder.setText (R.id.txt_item_title,data.getNew_title ());
    holder.setImageResource (mContext,R.id.iv1, data.getImageUrl ());
    }
}