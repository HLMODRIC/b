package com.hl.AFCHelper.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hl.AFCHelper.Adapter.TheoryFragmentPagerAdapter;
import com.hl.AFCHelper.R;
import java.util.ArrayList;
import java.util.List;

public class TheoryTabFragment extends Fragment {
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_theory,container,false);

        initData ();
        initView ();
         return view;
    }
    /**
     * 初始化数据
     */
    private void initData(){}
    /**
     * 初始化布局
     */
    private void initView() {
        ViewPager viewPager = view.findViewById (R.id.mViewPager1);
        TabLayout tabLayout = view.findViewById (R.id.mTabLayout);
        List<Fragment> fragments = new ArrayList<> ();
        fragments.add(new TheoryListFragment ());
        fragments.add(new RepairListFragment ());

        //这里注意的是，因为我是在fragment中创建MyFragmentPagerAdapter，所以要传getChildFragmentManager()
        viewPager.setAdapter(new TheoryFragmentPagerAdapter (getChildFragmentManager (), fragments));
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager (viewPager);


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
