package com.hl.AFCHelper.Activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.UI.MyToolBar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hl.AFCHelper.Fragment.HomeTabFragment;
import com.hl.AFCHelper.Fragment.SearchTabFragment;
import com.hl.AFCHelper.Fragment.TheoryTabFragment;
import com.hl.AFCHelper.Fragment.VideoTabFragment;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.UI.BottomBar;
import com.hl.AFCHelper.UI.BottomBarTab;
import com.hl.AFCHelper.Until.FileUtils;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
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

    private final int WRITE_AND_MOUNT = 1;
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
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};



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
                    .addItem(new BottomBarTab(this, R.drawable.tab_home,getString(R.string.tab_menu_home)))
                    .addItem(new BottomBarTab (this, R.drawable.tab_theory,getString(R.string.tab_menu_theory)))
                    .addItem(new BottomBarTab(this, R.drawable.tab_video,getString(R.string.tab_menu_video)))
                    .addItem(new BottomBarTab(this, R.drawable.tab_search,getString(R.string.tab_menu_search)));

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
    protected void initData() {
        super.initData ();
         if (EasyPermissions.hasPermissions(getApplicationContext (), perms)) {
            FileUtils.getInstance (getApplicationContext ()).createSDCardDir ();
            FileUtils.getInstance(getApplicationContext ()).copyAssetsToSD("video","AFCHelper");
        } else {
            EasyPermissions.requestPermissions(this, "视频播放需要读写外部存储，请授权",
                    WRITE_AND_MOUNT, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        FileUtils.getInstance (getApplicationContext ()).createSDCardDir ();
        FileUtils.getInstance(getApplicationContext ()).copyAssetsToSD("video","AFCHelper");

    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        }



    @Override
    protected void onDestroy() {
        super.onDestroy ();
        System.gc ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getApplicationContext ());
        refWatcher.watch (this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ac_main;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer (GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected (item);
    }


    //后退键监听
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
                    mToolbar.setNavigationIcon (R.drawable.item_more);
                    transaction.add(R.id.main_content, mHomeTabFragment);
                } else
                    mToolbar.setNavigationIcon (R.drawable.item_more);
                    transaction.show(mHomeTabFragment);
                break;
            case SECOND:

                mToolbarTitle.setText (getResources ().getText (R.string.tab_menu_theory));
                if (mTheoryTabFragment == null) {
                    mToolbar.setNavigationIcon (null);
                    mTheoryTabFragment = new TheoryTabFragment ();

                    transaction.add(R.id.main_content, mTheoryTabFragment);
                } else
                    mToolbar.setNavigationIcon (null);
                      transaction.show(mTheoryTabFragment);
                break;
            case THIRD:
                mToolbar.setNavigationIcon (null);
                 mToolbarTitle.setText (getResources ().getText (R.string.tab_menu_video));
                if (mVideoTabFragment == null) {
                    mVideoTabFragment = new VideoTabFragment ();
                    transaction.add(R.id.main_content, mVideoTabFragment);
                } else
                    mToolbar.setNavigationIcon (null);
                    transaction.show(mVideoTabFragment);
                break;
            case FOUR:
                mToolbar.setNavigationIcon (null);
                mToolbarTitle.setText (getResources ().getText (R.string.tab_menu_search));
                if (mSearchTabFragment == null) {
                    mToolbar.setNavigationIcon (null);
                    mSearchTabFragment = new SearchTabFragment ();
                    transaction.add(R.id.main_content, mSearchTabFragment);
                } else
                    mToolbar.setNavigationIcon (null);
                    transaction.show(mSearchTabFragment);
                break;

        }
        transaction.commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


}
