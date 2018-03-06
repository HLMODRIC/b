package com.hl.AFCHelper.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Fragment.HomeTabFragment;
import com.hl.AFCHelper.Fragment.SettingTabFragment;
import com.hl.AFCHelper.Fragment.SearchTabFragment;
import com.hl.AFCHelper.Fragment.TheoryTabFragment;
import com.hl.AFCHelper.db.DBManager;
import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    //TextView标题
    private TextView toolbar_title;
    //设置退出时间
    private long exitTime = 0;
    //Fragment Object
    private HomeTabFragment    mHomeTabFragment;
    private SettingTabFragment mSettingTabFragment;
    private TheoryTabFragment  mTheoryTabFragment;
    private SearchTabFragment mSearchTabFragment;
    private FragmentManager fManager;
    //Toolbar相关
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        setSupportActionBar(mToolbar);
        fManager = getSupportFragmentManager ();
        //加载布局
        initView ();
        //数据库操作
        DBManager.openDatabase (getApplicationContext ());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getApplicationContext ());
        refWatcher.watch(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //FrameLayout fl_content = findViewById (R.id.main_content);
        //Toolbar
        mToolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById (R.id.toolbar_title);
        //生成选项菜单
        mToolbar.inflateMenu(R.menu.tab_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });
        //RadioGroup
        RadioGroup rg_tab_bar = findViewById (R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        RadioButton rb_home = findViewById (R.id.rb_home);
        rb_home.setChecked(true);
    }


//点击回退键的处理：判断Fragment栈中是否有Fragment
//没，双击退出程序，否则像是Toast提示
//有，popBackStack弹出栈
@Override
public void onBackPressed() {
    if (fManager.getBackStackEntryCount() == 0) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            System.exit(0);
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
                toolbar_title.setText (getResources ().getText (R.string.app_name));
                if(mHomeTabFragment == null){
                    mHomeTabFragment = new HomeTabFragment ();
                    fTransaction.add(R.id.main_content,mHomeTabFragment);
                }else{
                    fTransaction.show(mHomeTabFragment);
                }
                break;
            case R.id.rb_theory:
                toolbar_title.setText (getResources ().getText (R.string.tab_menu_theory));
                if(mTheoryTabFragment == null){
                    mTheoryTabFragment = new TheoryTabFragment ();
                    fTransaction.add(R.id.main_content,mTheoryTabFragment);
                }else{
                    fTransaction.show(mTheoryTabFragment);
                }
                break;
            case R.id.rb_setting:
                toolbar_title.setText (getResources ().getText (R.string.tab_menu_setting));
                if(mSettingTabFragment == null){
                    mSettingTabFragment = new SettingTabFragment ();
                    fTransaction.add(R.id.main_content, mSettingTabFragment);
                }else{
                    fTransaction.show(mSettingTabFragment);
                }
                break;
            case R.id.rb_search:
                toolbar_title.setText (getResources ().getText (R.string.tab_menu_search));
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
