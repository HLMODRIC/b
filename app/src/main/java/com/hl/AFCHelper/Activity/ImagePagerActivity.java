package com.hl.AFCHelper.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.gyf.barlibrary.ImmersionBar;
import com.hl.AFCHelper.Adapter.HackyViewPager;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
//import com.squareup.leakcanary.RefWatcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**图片查看器
 * Created by 林川 on 2016/12/6.
 */
public class ImagePagerActivity extends Activity {
    public static final String INTENT_IMGURLS = "imgUrls";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_IMAGESIZE = "imageSize";
    public static final int fading_in=0x7f01000a;
    public static final int fading_out=0x7f01000b;

    private String curImageUrl = "";

    private List<View> guideViewList = new ArrayList<View>();
    public ImageSize imageSize;
    private int startPos;
    private ArrayList<String> imgUrls;

    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position, ImageSize imageSize) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<> (imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        intent.putExtra(INTENT_IMAGESIZE, imageSize);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_imagepager);
        overridePendingTransition(fading_out, fading_in);

        HackyViewPager viewPager = findViewById(R.id.pager);
        LinearLayout guideGroup = findViewById (R.id.guideGroup);

        getIntentData();

        ImageAdapter mAdapter = new ImageAdapter(getApplicationContext ());
        mAdapter.setDatas(imgUrls);
        mAdapter.setImageSize(imageSize);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
        //refWatcher.watch(this);
    }

    private void getIntentData() {
        startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        imageSize = (ImageSize) getIntent().getSerializableExtra(INTENT_IMAGESIZE);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setSelected(i == startPos);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;
        private ImageSize imageSize;
        private ImageView smallImageView = null;

        public void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }

        public void setImageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context)
            ;
        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);

            if (view != null) {
                final PhotoView imageView = (PhotoView ) view.findViewById(R.id.image);

                imageView.setOnPhotoTapListener(new OnPhotoTapListener () {
                    @Override
                    public void onPhotoTap(ImageView view, float x, float y) {
                        finish();
                        overridePendingTransition(fading_out, fading_in);
                    }
                });

//                长按事件
//                imageView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//
//                    }
//                });

                if (imageSize != null) {
                    //预览imageView
                    smallImageView = new ImageView(context);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageSize.getWidth(), imageSize.getHeight());
                    layoutParams.gravity = Gravity.CENTER;
                    smallImageView.setLayoutParams(layoutParams);
                    smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    ((FrameLayout) view).addView(smallImageView);
                }

                //loading
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;

                final String imgurl = datas.get(position);

                RequestOptions options = new RequestOptions ()
                        .diskCacheStrategy (DiskCacheStrategy.ALL)
                        .error(R.mipmap.load_error);

                Glide.with(getApplicationContext ())
                        .load(imgurl)
                        .apply (options)
                        .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                        .into(imageView);

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }

    public static class ImageSize implements Serializable {

        private int width;
        private int height;

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
