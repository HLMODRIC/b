package com.hl.b.TabFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.b.R;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class HomeTabFragment extends Fragment {

    public HomeTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home,container,false);
         return view;
    }
}
