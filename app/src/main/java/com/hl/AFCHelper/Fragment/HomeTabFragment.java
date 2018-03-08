package com.hl.AFCHelper.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hl.AFCHelper.Activity.ListActivity;
import com.hl.AFCHelper.Activity.ImagePagerActivity;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Until.SearchDataHelper;
import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;
import com.squareup.leakcanary.RefWatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 2018/1/23
 * 主页
 *
 *
 */
public class HomeTabFragment extends Fragment {
    private View view;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    private List<String> ListPhotos;
    private TextView title;
    private ScheduledExecutorService scheduledExecutorService;
    //db数据库
    private String tableName;
    private String[] imageUrl;
    private String[] titles;


    //****viewpager  四号线图片展播
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.fg_home, container, false);
        //初始化布局,数据
        initData ();
        initView ();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
          imageUrl = new String[]{
                "file:///android_asset/advert/viewpager_1.jpg",
                "file:///android_asset/advert/viewpager_1.jpg",
                "file:///android_asset/advert/viewpager_1.jpg",
                "file:///android_asset/advert/viewpager_1.jpg"
        };
        //存放图片的标题
         titles = new String[]{
                "轮播1", "轮播2", "轮播3", "轮播4"};
        //图片展播  图片位置
        ListPhotos = new ArrayList<>();
        ListPhotos.add ("file:///android_asset/advert/viewpager_1.jpg");
        ListPhotos.add ("file:///android_asset/advert/viewpager_2.jpg");
        ListPhotos.add ("file:///android_asset/advert/viewpager_3.jpg");
        ListPhotos.add ("file:///android_asset/advert/viewpager_4.jpg");

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mViewPaper = view.findViewById (R.id.home_vp);
        title = view.findViewById (R.id.vp_title);
        title.setText (titles[0]);

        LinearLayout home_item1 = view.findViewById (R.id.ll_item1);
        LinearLayout home_item2 = view.findViewById (R.id.ll_item2);
        LinearLayout home_item3 = view.findViewById (R.id.ll_item3);
        LinearLayout home_item4 = view.findViewById (R.id.ll_item4);
        LinearLayout home_item5 = view.findViewById (R.id.ll_item5);
        LinearLayout home_item6 = view.findViewById (R.id.ll_item6);
        LinearLayout home_item7 = view.findViewById (R.id.ll_item7);
        LinearLayout home_item8 = view.findViewById (R.id.ll_item8);
        LinearLayout home_item9 = view.findViewById (R.id.ll_item9);

        //显示的图片
        images = new ArrayList<> ();
        for (int i = 0; i < imageUrl.length; i++) {
            ImageView imageView = new ImageView (getActivity ());
            imageView.setAdjustViewBounds (true);
            imageView.setScaleType (ImageView.ScaleType.FIT_XY);

            RequestOptions options = new RequestOptions ()
                    .diskCacheStrategy (DiskCacheStrategy.ALL)
                    .error(R.mipmap.load_error);

            Glide.with(getActivity ())
                    .load(imageUrl[i])
                    .apply (options)
                    .into(imageView);
            images.add (imageView);
        }
        //图片展播 图片底部显示的小点
        dots = new ArrayList<> ();
        dots.add (view.findViewById (R.id.dot_0));
        dots.add (view.findViewById (R.id.dot_1));
        dots.add (view.findViewById (R.id.dot_2));
        dots.add (view.findViewById (R.id.dot_3));

        ViewPagerAdapter madapter = new ViewPagerAdapter ();
        mViewPaper.setAdapter (madapter);
        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageSelected(int position) {
                title.setText (titles[position]);
                dots.get (position).setBackgroundResource (R.drawable.bannertrue);
                dots.get (oldPosition).setBackgroundResource (R.drawable.bannerfalse);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        final SearchDataHelper searchDataHelper = new SearchDataHelper ();
        home_item1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_1);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_2);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_3);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_4);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_5);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_6);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item7.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_7);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item8.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_8);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
        home_item9.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.home_button_text_9);
                getData (searchDataHelper.ShowData (tableName));
            }
        });
    }



    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size ();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            view.removeView (images.get (position));
        }

        //图片展播 按键事件
        @Override
        public Object instantiateItem(ViewGroup view, final int position) {

            // TODO Auto-generated method stub
            switch (position) {
                case 0:   //图片1
                    images.get (0).setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            ImagePagerActivity.startImagePagerActivity(getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize(50, 50));
                                    }
                    });
                    break;
                case 1:      //图片2
                    images.get (1).setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            ImagePagerActivity.startImagePagerActivity(getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize(50, 50));
                        }
                    });
                    break;
                case 2:     //图片3
                    images.get (2).setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            ImagePagerActivity.startImagePagerActivity(getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize(50, 50));
                        }
                    });
                    break;
                case 3:      //图片4
                    images.get (3).setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            ImagePagerActivity.startImagePagerActivity(getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize(50, 50));
                        }
                    });
                    break;
                case 4:        //图片5
                    images.get (4).setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            ImagePagerActivity.startImagePagerActivity(getActivity (), ListPhotos, position, new ImagePagerActivity.ImageSize(50, 50));
                        }
                    });
                    break;
                    default:
                        break;
            }


            view.addView (images.get (position));
            return images.get (position);
        }
    }

        /**
         * 利用线程池定时执行动画轮播
         */
        @Override
        public void onStart() {
            // TODO Auto-generated method stub
            super.onStart ();
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor ();
            scheduledExecutorService.scheduleWithFixedDelay (
                    new ViewPageTask (),
                    5,
                    5,
                    TimeUnit.SECONDS);
        }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

        /**
         * 图片轮播任务
         *
         * @author 黄磊
         */
        private class ViewPageTask implements Runnable {

            @Override
            public void run() {
                currentItem = (currentItem + 1) % imageUrl.length;
                mHandler.sendEmptyMessage (0);
            }
        }

        /**
         * 接收子线程传递过来的数据
         */
        @SuppressLint("HandlerLeak")
        private Handler mHandler = new Handler () {
            public void handleMessage(android.os.Message msg) {
                mViewPaper.setCurrentItem (currentItem);
            }
        };

        @Override
        public void onStop() {
            // TODO Auto-generated method stub
            super.onStop ();
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown ();
                scheduledExecutorService = null;
            }
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
        Intent intent=new Intent (getActivity (),ListActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString ("table_name",tableName);
        bundle.putParcelableArrayList ("data", datas);
        intent.putExtras(bundle);
        bundle.clear ();
        startActivity (intent);
    }

    }



