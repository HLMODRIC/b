package com.hl.b.ListFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hl.b.Activity.ContentActivity;
import com.hl.b.Activity.ListActivity;
import com.hl.b.R;
import com.hl.b.db.Data;
import com.hl.b.db.MyDBOpenHelper;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/6 0006.
 */
@SuppressLint("ValidFragment")
public class ListFragment extends Fragment {
    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private SQLiteDatabase dbRead;
    //1.18
    private RecyclerView mCrimeRecyclerView;
    private DataAdapter mAdapter;
    //1.18
    MyDBOpenHelper dbHelper;
    String titleStr;
    String contentStr;
    int mid;
    private Cursor mCursor;

    public ListFragment(FragmentManager fManager) {
        this.fManager = fManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.recycler_list, container, false);
        //1.18
        mCrimeRecyclerView = ( RecyclerView ) view
                .findViewById (R.id.recycler_view);
        mCrimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));

        datas = ( ArrayList<Data> ) getActivity().getIntent().getSerializableExtra ("data");
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
            Bundle bundle=new Bundle();
            bundle.putString("data",datas.get (getPosition ()).getNew_content ());
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


