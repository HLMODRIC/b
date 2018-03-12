package com.hl.AFCHelper.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hl.AFCHelper.Activity.ImagePagerActivity;
import com.hl.AFCHelper.Activity.ListActivity;
import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.Bean.db.MyDBOpenHelper;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Until.GlideImageLoader;
import com.hl.AFCHelper.Until.SearchDataHelper;
import com.squareup.leakcanary.RefWatcher;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 2018/1/23
 * 主页
 */
public class HomeTabFragment extends Fragment {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_name1)
    TextView mTvName1;
    @BindView(R.id.ll_item1)
    LinearLayout mLlItem1;
    @BindView(R.id.tv_name2)
    TextView mTvName2;
    @BindView(R.id.ll_item2)
    LinearLayout mLlItem2;
    @BindView(R.id.tv_name3)
    TextView mTvName3;
    @BindView(R.id.ll_item3)
    LinearLayout mLlItem3;
    @BindView(R.id.tv_name4)
    TextView mTvName4;
    @BindView(R.id.ll_item4)
    LinearLayout mLlItem4;
    @BindView(R.id.tv_name5)
    TextView mTvName5;
    @BindView(R.id.ll_item5)
    LinearLayout mLlItem5;
    @BindView(R.id.tv_name6)
    TextView mTvName6;
    @BindView(R.id.ll_item6)
    LinearLayout mLlItem6;
    @BindView(R.id.home_item_tv_7)
    TextView mHomeItemTv7;
    @BindView(R.id.ll_item7)
    LinearLayout mLlItem7;
    @BindView(R.id.home_item_tv_8)
    TextView mHomeItemTv8;
    @BindView(R.id.ll_item8)
    LinearLayout mLlItem8;
    @BindView(R.id.home_item_tv_9)
    TextView mHomeItemTv9;
    @BindView(R.id.ll_item9)
    LinearLayout mLlItem9;
    Unbinder unbinder;
    private List<String> ListPhotos;
    private ScheduledExecutorService scheduledExecutorService;
    private String tableName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fg_home, container, false);
        unbinder = ButterKnife.bind (this, view);
        //初始化布局,数据
        initData ();
        initView ();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //图片展播  图片位置
        ListPhotos = new ArrayList<> ();
        ListPhotos.add ("file:///android_asset/advert/viewpager_1.jpg");
        ListPhotos.add ("file:///android_asset/advert/viewpager_2.jpg");
        ListPhotos.add ("file:///android_asset/advert/viewpager_3.jpg");
        ListPhotos.add ("file:///android_asset/advert/viewpager_4.jpg");

    }

    /**
     * 初始化布局
     */
    private void initView() {
        //Banner
        //设置banner样式
        mBanner.setBannerStyle (BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader (new GlideImageLoader ());
        //设置图片集合
        mBanner.setImages (ListPhotos);
        //设置banner动画效果
        mBanner.setBannerAnimation (Transformer.Stack);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay (true);
        //设置轮播时间
        mBanner.setDelayTime (5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity (BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start ();
        mBanner.setOnBannerListener (new OnBannerListener () {
            @Override
            public void OnBannerClick(final int position) {
                switch (position) {
                    case 0:   //图片1
                        ImagePagerActivity.startImagePagerActivity (getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize (50, 50));

                        break;
                    case 1:      //图片2
                        ImagePagerActivity.startImagePagerActivity (getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize (50, 50));
                        break;
                    case 2:     //图片3
                        ImagePagerActivity.startImagePagerActivity (getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize (50, 50));
                        break;
                    case 3:      //图片4
                        ImagePagerActivity.startImagePagerActivity (getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize (50, 50));
                        break;
                    case 4:        //图片5
                        ImagePagerActivity.startImagePagerActivity (getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize (50, 50));
                        break;
                    default:
                        break;
                }
            }
        });

        final SearchDataHelper searchDataHelper = new SearchDataHelper ();
        mLlItem1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_1);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_2);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_3);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_4);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_5);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_6);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem7.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_7);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem8.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_8);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        mLlItem9.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_9);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getActivity ());
        refWatcher.watch (this);
    }


    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop ();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown ();
            scheduledExecutorService = null;
        }
        mBanner.stopAutoPlay();
    }

    //数据库数据获取
    private void getData(String sql) {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        Cursor cursor = dbRead.rawQuery (sql, null);
        ArrayList<Data> datas = new ArrayList<> ();
        while (cursor.moveToNext ()) {
            int mid = cursor.getInt (cursor.getColumnIndex ("id"));
            String titleStr = cursor.getString (cursor.getColumnIndex ("title"));
            String contentStr = cursor.getString (cursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }
        cursor.close ();
        dbHelper.close ();
        dbRead.close ();
        Intent intent = new Intent (getActivity (), ListActivity.class);
        Bundle bundle = new Bundle ();
        bundle.putString ("table_name", tableName);
        bundle.putParcelableArrayList ("data", datas);
        intent.putExtras (bundle);
        bundle.clear ();
        startActivity (intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }
}



