package com.hl.AFCHelper.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hl.AFCHelper.Activity.ContentActivity;
import com.hl.AFCHelper.Adapter.BaseRecyclerAdapter;
import com.hl.AFCHelper.Adapter.ListDataAdapter;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Bean.Data;
import com.squareup.leakcanary.RefWatcher;
import java.util.ArrayList;

public class ListFragment extends Fragment {
    private ArrayList<Data> datas = null;
    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.recycler_list, container, false);
        //初始化数据和布局
        initData ();
        initView ();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        datas = getActivity ().getIntent ().getParcelableArrayListExtra ("data");
        if (getArguments ()!= null){
            Bundle bundle = getArguments();//从activity传过来的Bundle
            datas =  bundle.getParcelableArrayList ("data");
            bundle.clear ();
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        ListDataAdapter adapter = new ListDataAdapter (R.layout.recycler_list_item, datas);
        RecyclerView crimeRecyclerView = view.findViewById (R.id.recycler_view);
        crimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter.setItemClickListener (new BaseRecyclerAdapter.onItemClickListener () {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent=new Intent (getActivity (),ContentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("data",datas.get (position).getNew_content ());
                bundle.putString ("title",datas.get (position).getNew_title ());
                intent.putExtras(bundle);
                bundle.clear ();
                startActivity(intent);
            }
        });
        crimeRecyclerView.setAdapter(adapter);
         }
}


