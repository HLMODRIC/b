package com.hl.AFCHelper.Adapter;

import android.content.Context;

import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Bean.Data;
import java.util.ArrayList;


public class ListDataAdapter extends BaseRecyclerAdapter<Data, BaseRecyclerAdapter.BaseViewHolder> {

    public ListDataAdapter(int layoutResId, ArrayList<Data> data) {
        super(layoutResId, data);
    }
    @Override
    protected void bindTheData(BaseRecyclerAdapter.BaseViewHolder holder, Data data, int position) {
    holder.setText (R.id.txt_item_title,data.getNew_title ());
    }


}