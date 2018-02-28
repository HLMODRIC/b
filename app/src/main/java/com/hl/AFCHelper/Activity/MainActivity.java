package com.hl.AFCHelper.Activity;


import android.app.*;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.TabFragment.HomeTabFragment;
import com.hl.AFCHelper.TabFragment.SettingTabFragment;
import com.hl.AFCHelper.TabFragment.SearchTabFragment;
import com.hl.AFCHelper.TabFragment.TheoryTabFragment;
import com.hl.AFCHelper.db.DBManager;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by Coder-pig on 2015/8/29 0028.
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    //控件
    private RadioGroup rg_tab_bar;
    private RadioButton rb_home;
    private RadioButton rb_theory;
    private RadioButton rb_search;
    private RadioButton rb_setting;
    private TextView txt_title;
    private FrameLayout fl_content;
    //上下文
    private Context mContext;
    //设置
    private long exitTime = 0;
    //Fragment Object
    private HomeTabFragment    mHomeTabFragment;
    private SettingTabFragment mSettingTabFragment;
    private TheoryTabFragment  mTheoryTabFragment;
    private SearchTabFragment mSearchTabFragment;
    private FragmentManager fManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        mContext = MainActivity.this;
        fManager = getFragmentManager();
        bindViews();

        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_theory = (RadioButton) findViewById(R.id.rb_theory);
        rb_search = (RadioButton) findViewById(R.id.rb_search);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rb_home.setChecked(true);



    }
    @Override
    protected void onStart() {
        //进行数据库传输
        super.onStart ();
        DBManager.openDatabase (this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }

    //
    private void bindViews() {
        txt_title = (TextView) findViewById(R.id.txt_topbar);
        fl_content = (FrameLayout) findViewById(R.id.main_content);
    }
//
//点击回退键的处理：判断Fragment栈中是否有Fragment
//没，双击退出程序，否则像是Toast提示
//有，popbackstack弹出栈
@Override
public void onBackPressed() {
    if (fManager.getBackStackEntryCount() == 0) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    } else {
        fManager.popBackStack();
    }
}
//


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_home:
                if(mHomeTabFragment == null){
                    mHomeTabFragment = new HomeTabFragment ();
                    fTransaction.add(R.id.main_content,mHomeTabFragment);
                }else{
                    fTransaction.show(mHomeTabFragment);
                }
                break;
            case R.id.rb_theory:
                if(mTheoryTabFragment == null){
                    mTheoryTabFragment = new TheoryTabFragment ();
                    fTransaction.add(R.id.main_content,mTheoryTabFragment);
                }else{
                    fTransaction.show(mTheoryTabFragment);
                }
                break;
            case R.id.rb_setting:
                if(mSettingTabFragment == null){
                    mSettingTabFragment = new SettingTabFragment ();
                    fTransaction.add(R.id.main_content, mSettingTabFragment);
                }else{
                    fTransaction.show(mSettingTabFragment);
                }
                break;
            case R.id.rb_search:
                if(mSearchTabFragment == null){
                    mSearchTabFragment = new SearchTabFragment ();
                    fTransaction.add(R.id.main_content,mSearchTabFragment);
                }else{
                    fTransaction.show(mSearchTabFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(mHomeTabFragment != null)fragmentTransaction.hide(mHomeTabFragment);
        if(mTheoryTabFragment != null)fragmentTransaction.hide(mTheoryTabFragment);
        if(mSearchTabFragment != null)fragmentTransaction.hide(mSearchTabFragment);
        if(mSettingTabFragment != null)fragmentTransaction.hide(mSettingTabFragment);
    }

}
