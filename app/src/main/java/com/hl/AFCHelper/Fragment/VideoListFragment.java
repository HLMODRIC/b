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

import com.hl.AFCHelper.Activity.VideoContentActivity;
import com.hl.AFCHelper.Adapter.ListDataAdapter;
import com.hl.AFCHelper.Adapter.TheoryListDataAdapter;
import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
//import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

public class VideoListFragment extends Fragment {
    private ArrayList<Data> mData;
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
       // RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        //refWatcher.watch(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mData = getActivity ().getIntent ().getParcelableArrayListExtra ("videoData");
        if (getArguments ()!= null){
            Bundle bundle = getArguments();//从activity传过来的Bundle
            mData =  bundle.getParcelableArrayList ("videoData");
            bundle.clear ();
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        ListDataAdapter adapter = new ListDataAdapter (R.layout.recycler_list_item, mData);
        RecyclerView crimeRecyclerView = view.findViewById (R.id.recycler_view);
        crimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter.setItemClickListener (new TheoryListDataAdapter.onItemClickListener () {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent=new Intent (getActivity (),VideoContentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("data",mData.get (position).getNew_content ());
                bundle.putString ("title",mData.get (position).getNew_title ());
                bundle.putString("imageUrl",mData.get (position).getImageUrl ());
                bundle.putString ("videoUrl",mData.get (position).getVideoUrl ());
                intent.putExtras(bundle);
                bundle.clear ();
                startActivity(intent);
            }
        });
        crimeRecyclerView.setAdapter(adapter);
         }
}


