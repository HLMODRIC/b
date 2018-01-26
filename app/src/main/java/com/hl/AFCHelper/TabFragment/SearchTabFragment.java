package com.hl.AFCHelper.TabFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.AFCHelper.R;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class SearchTabFragment extends Fragment {

    public SearchTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_search,container,false);

         return view;
    }
}
