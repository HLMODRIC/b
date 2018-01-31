package com.hl.AFCHelper.TabFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hl.AFCHelper.Activity.ContentActivity;
import com.hl.AFCHelper.Activity.ListActivity;
import com.hl.AFCHelper.ImageViewPager.ImagePagerActivity;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 2018/1/23
 * 主页
 * 具有图片展播，搜索和四号线工作介绍
 *
 */
public class HomeTabFragment extends Fragment {
    private View view;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    private Context mContext;
    private List<String> ListPhotos;
    private TextView title;
    private ViewPagerAdapter madapter;
    private ScheduledExecutorService scheduledExecutorService;
    //db数据库
    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private SQLiteDatabase dbRead;
    MyDBOpenHelper dbHelper;
    String titleStr;
    String contentStr;
    int mid;

    private Cursor mCursor;


    //****viewpager  四号线图片展播
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.test,
            R.drawable.test,
            R.drawable.test,
            R.drawable.test,
            R.drawable.test
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "轮播1",
            "轮播2",
            "轮播3",
            "轮播4",
            "轮播5"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.fg_home, container, false);
        LinearLayout home_item1 = (LinearLayout) view.findViewById (R.id.ll_item1);
        LinearLayout home_item2 = (LinearLayout) view.findViewById (R.id.ll_item2);
        LinearLayout home_item3 = (LinearLayout) view.findViewById (R.id.ll_item3);
        LinearLayout home_item4 = (LinearLayout) view.findViewById (R.id.ll_item4);
        LinearLayout home_item5 = (LinearLayout) view.findViewById (R.id.ll_item5);
        LinearLayout home_item6 = (LinearLayout) view.findViewById (R.id.ll_item6);
        home_item1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from basic where id < 100");
            }
        });
        home_item2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from basic where id between 100 and 200");
            }
        });
        home_item3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from basic where id between 200 and 300");
            }
        });
        home_item4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from basic where id between 300 and 400");
            }
        });
        home_item5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from basic where id between 400 and 500");
            }
        });
        home_item6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from basic where id between 500 and 600");
            }
        });
        setView ();
        return view;
    }


    private void setView() {
        mViewPaper = ( ViewPager ) view.findViewById (R.id.home_vp);

        //显示的图片
        images = new ArrayList<ImageView> ();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView (getActivity ());
            imageView.setBackgroundResource (imageIds[i]);
            images.add (imageView);
        }
        //图片展播 图片底部显示的小点
        dots = new ArrayList<View> ();
        dots.add (view.findViewById (R.id.dot_0));
        dots.add (view.findViewById (R.id.dot_1));
        dots.add (view.findViewById (R.id.dot_2));
        dots.add (view.findViewById (R.id.dot_3));
        dots.add (view.findViewById (R.id.dot_4));
        //图片展播  图片位置
        ListPhotos = new ArrayList<>();
        ListPhotos.add ("file:///android_asset/advert/01.jpg");
        ListPhotos.add ("file:///android_asset/advert/02.png");
        ListPhotos.add ("file:///android_asset/advert/01.jpg");
        ListPhotos.add ("file:///android_asset/advert/02.png");
        ListPhotos.add ("file:///android_asset/advert/01.jpg");


        title = ( TextView ) view.findViewById (R.id.title1);
        title.setText (titles[0]);
        madapter = new ViewPagerAdapter ();
        mViewPaper.setAdapter (madapter);
        mViewPaper.setOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageSelected(int position) {
                title.setText (titles[position]);
                dots.get (position).setBackgroundResource (R.drawable.test);
                dots.get (oldPosition).setBackgroundResource (R.drawable.test);
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
            final Intent intent = new Intent(getActivity (), ImagePagerActivity.class);
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
                    2,
                    2,
                    TimeUnit.SECONDS);
        }

        /**
         * 图片轮播任务
         *
         * @author 黄磊
         */
        private class ViewPageTask implements Runnable {

            @Override
            public void run() {
                currentItem = (currentItem + 1) % imageIds.length;
                mHandler.sendEmptyMessage (0);
            }
        }

        /**
         * 接收子线程传递过来的数据
         */
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
        dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        mCursor = dbRead.rawQuery (sql,null);
        datas = new ArrayList<Data> ();
        while (mCursor.moveToNext ()) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        Intent intent=new Intent ();
        intent.setClass(getActivity(), ListActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable ("data",datas);
        intent.putExtras(bundle);
        startActivity (intent);
    }

    }



