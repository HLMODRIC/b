package com.hl.AFCHelper.SearchFragment;

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
import android.widget.TextView;

import com.hl.AFCHelper.Activity.ContentActivity;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/6 0006.
 */
@SuppressLint("ValidFragment")
public class SearchListFragment extends Fragment {
    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private RecyclerView mCrimeRecyclerView;
    private DataAdapter mAdapter;

    public SearchListFragment(FragmentManager fManager) {
        this.fManager = fManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.search_recycler_list, container, false);
        mCrimeRecyclerView = ( RecyclerView ) view.findViewById (R.id.recycler_view);
        mCrimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        Bundle bundle = getArguments();//从activity传过来的Bundle
        datas = ( ArrayList<Data> ) bundle.getSerializable ("data");
        updateUI();

        return view;
    }
    private void updateUI() {
        mAdapter = new DataAdapter (datas);
        mCrimeRecyclerView.setAdapter(mAdapter);
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
            Bundle bundle2=new Bundle();
            bundle2.putString("data",datas.get (getPosition ()).getNew_content ());
            intent.putExtras(bundle2);
            startActivity(intent);
        }

    }

    private class DataAdapter extends RecyclerView.Adapter<SearchListFragment.DataHolder> {
        private ArrayList<Data> datas;
        private int position;

        public DataAdapter(ArrayList<Data> datas) {
            this.datas = datas;
        }

        @Override
        public SearchListFragment.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from (getActivity ());
            View view = layoutInflater
                    .inflate (R.layout.recycler_list_item, parent, false);
            return new SearchListFragment.DataHolder (view);
        }

        @Override
        public void onBindViewHolder(SearchListFragment.DataHolder holder, final int position) {
            Data data = datas.get (position);
            holder.mTitleTextView.setText (data.getNew_title ());
        }

        @Override
        public int getItemCount() {
            return datas.size ();
        }
    }
}


