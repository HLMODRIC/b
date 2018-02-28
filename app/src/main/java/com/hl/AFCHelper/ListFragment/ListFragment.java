package com.hl.AFCHelper.ListFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hl.AFCHelper.Activity.ContentActivity;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/6 0006.
 */
@SuppressLint("ValidFragment")
public class ListFragment extends Fragment {
    private ImageButton backButton;
    private FragmentManager fManager;
    private TextView topbar;
    private ArrayList<Data> datas;
    private String tableName;
    private RecyclerView mCrimeRecyclerView;
    private DataAdapter mAdapter;

    public ListFragment(FragmentManager fManager) {
        this.fManager = fManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.recycler_list, container, false);
        backButton = (ImageButton ) view.findViewById (R.id.list_back);
        backButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        topbar = (TextView) view.findViewById (R.id.txt_topbar_list);
        mCrimeRecyclerView = ( RecyclerView ) view.findViewById (R.id.recycler_view);
        mCrimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));

        tableName = getActivity ().getIntent ().getStringExtra ("table_name");
        datas = ( ArrayList<Data> ) getActivity().getIntent().getSerializableExtra ("data");
        updateUI();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    private void updateUI() {
        mAdapter = new DataAdapter (datas);
        mCrimeRecyclerView.setAdapter(mAdapter);
        topbar.setText (tableName);
         }

    private class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Data mData;
        public TextView mTitleTextView;

        public DataHolder(View view) {
            super (view);
            mTitleTextView = ( TextView ) view.findViewById (R.id.txt_item_title);
            itemView.setOnClickListener (this);
        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent ();
            intent.setClass(getActivity(), ContentActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("data",datas.get (getPosition ()).getNew_content ());
            bundle.putString ("title",datas.get (getPosition ()).getNew_title ());
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    private class DataAdapter extends RecyclerView.Adapter<DataHolder> {
        private ArrayList<Data> datas;
        private int position;

        public DataAdapter(ArrayList<Data> datas) {
            this.datas = datas;
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from (getActivity ());
            View view = layoutInflater
                    .inflate (R.layout.recycler_list_item, parent, false);
            return new DataHolder (view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, final int position) {
            Data data = datas.get (position);
            holder.mTitleTextView.setText (data.getNew_title ());
        }

        @Override
        public int getItemCount() {
            return datas.size ();
        }
    }
}


