package com.hl.AFCHelper.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import com.hl.AFCHelper.UI.MyToolBar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hl.AFCHelper.Bean.db.DBManager;
import com.hl.AFCHelper.Fragment.HomeTabFragment;
import com.hl.AFCHelper.Fragment.SearchTabFragment;
import com.hl.AFCHelper.Fragment.TheoryTabFragment;
import com.hl.AFCHelper.Fragment.VideoTabFragment;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.UI.BottomBar;
import com.hl.AFCHelper.UI.BottomBarTab;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolBar mToolbar;
    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    //设置退出时间
    private long exitTime = 0;
    //Fragment
    private HomeTabFragment mHomeTabFragment;
    private VideoTabFragment mVideoTabFragment;
    private TheoryTabFragment mTheoryTabFragment;
    private SearchTabFragment mSearchTabFragment;
    private FragmentManager fManager;
    //
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;


    @Override
    protected boolean isImmersionBarEnabled() {
        return super.isImmersionBarEnabled ();
    }

    @Override
    protected void initView() {
        //Fragment
        fManager = getSupportFragmentManager ();
        selectedFragment(0);
        //Toolbar
        mToolbar.setTitle ("");
        setSupportActionBar (mToolbar);
        //NavigationView
        mNavView.setNavigationItemSelectedListener (new NavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers ();
                return true;
            }
        });
        //
        mBottomBar
                .addItem(new BottomBarTab(this, R.mipmap.tab_home,getString(R.string.tab_menu_home)))
                .addItem(new BottomBarTab (this, R.mipmap.tab_theory,getString(R.string.tab_menu_theory)))
                .addItem(new BottomBarTab(this, R.mipmap.tab_video,getString(R.string.tab_menu_video)))
                .addItem(new BottomBarTab(this, R.mipmap.tab_search,getString(R.string.tab_menu_search)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
               selectedFragment (position);
            }

            @Override
            public void onTabUnselected(int position) {
            }


            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar ();
        mImmersionBar.titleBar (R.id.toolbar).init ();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getApplicationContext ());
        refWatcher.watch (this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ac_main;
    }

    //后退键监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer (GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected (item);
    }



    //点击回退键的处理：判断Fragment栈中是否有Fragment
//没，双击退出程序，否则像是Toast提示
//有，popBackStack弹出栈
    @Override
    public void onBackPressed() {
        if (fManager.getBackStackEntryCount () == 0) {
            if ((System.currentTimeMillis () - exitTime) > 2000) {
                Toast.makeText (getApplicationContext (), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show ();
                exitTime = System.currentTimeMillis ();
            } else {
                super.onBackPressed ();
                System.exit (0);
            }
        } else {
            fManager.popBackStack ();
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeTabFragment != null) fragmentTransaction.hide (mHomeTabFragment);
        if (mTheoryTabFragment != null) fragmentTransaction.hide (mTheoryTabFragment);
        if (mSearchTabFragment != null) fragmentTransaction.hide (mSearchTabFragment);
        if (mVideoTabFragment != null) fragmentTransaction.hide (mVideoTabFragment);
    }
    private void selectedFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment (transaction);
        switch (position) {
            case FIRST:
                mToolbarTitle.setText (getResources ().getText (R.string.app_name));
                if (mHomeTabFragment == null) {
                    mHomeTabFragment = new HomeTabFragment ();
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_UNLOCKED);
                    transaction.add(R.id.main_content, mHomeTabFragment);
                } else
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_UNLOCKED);

                transaction.show(mHomeTabFragment);
                break;
            case SECOND:
                mToolbarTitle.setText (getResources ().getText (R.string.tab_menu_theory));
                if (mTheoryTabFragment == null) {
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    mTheoryTabFragment = new TheoryTabFragment ();
                    transaction.add(R.id.main_content, mTheoryTabFragment);
                } else
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                    transaction.show(mTheoryTabFragment);
                break;
            case THIRD:
                //DrawerLayout
                mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mToolbarTitle.setText (getResources ().getText (R.string.tab_menu_video));
                if (mVideoTabFragment == null) {
                    mVideoTabFragment = new VideoTabFragment ();
                    transaction.add(R.id.main_content, mVideoTabFragment);
                } else
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    transaction.show(mVideoTabFragment);
                break;
            case FOUR:
                mToolbarTitle.setText (getResources ().getText (R.string.tab_menu_search));
                if (mSearchTabFragment == null) {
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    mSearchTabFragment = new SearchTabFragment ();
                    transaction.add(R.id.main_content, mSearchTabFragment);
                } else
                    //DrawerLayout
                    mDrawerLayout.setDrawerLockMode (DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    transaction.show(mSearchTabFragment);
                break;

        }
        transaction.commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


}
