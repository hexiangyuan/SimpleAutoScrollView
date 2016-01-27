package com.example.xiyoung.simplescrollview.simpleautoscrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xiyoung.simplescrollview.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AutoScrollBanner extends FrameLayout implements OnPageChangeListener {
    private ViewPager mViewpager;
    private LinearLayout pointGroup;
    private List<View> viewList = new ArrayList<>();
    private int lastPointIndex;
    private boolean isAutoScroll = true;
    private long before;
    private int scrollStatus;
    private int realSize = 0;
    private int auto_scroll_time = 4000;//自动滚动的时间;
    public static final int VIEWPAGER_COUNT = 10000;//伪无限制循环,将界面放置于一个很大的数额页码,如果换成Integer.MAX_VALUE可能会出错,原因未知.....;
    private BaseAutoScrollViewAdapter adapter;
    private ImageLoader imageLoader;
    private static final int HANDLER_MSG_ID = 122459093;
    // 自动滚动的消息机制
    private Timer timer;
    private ImageHandler handler = new ImageHandler(new WeakReference<AutoScrollBanner>(this));


    public AutoScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public AutoScrollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AutoScrollBanner(Context context) {
        super(context);
        initView(context);
    }

    // 初始化控件
    private void initView(final Context context) {
        View.inflate(context, R.layout.view_auto_scroll_banner, this);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) findViewById(R.id.ll_image_point);
        mViewpager.addOnPageChangeListener(this);
    }

    public void setViewList(List<View> list) {
        viewList.clear();
        mViewpager.setAdapter(adapter);
        pointGroup.removeAllViews();
        if (list.size() > 0) {
            viewList.addAll(list);
            initViewsDate();
        }
    }


    private void initViewsDate() {
        if (viewList.size() < 4 && viewList.size() > 1) {
            viewList.addAll(viewList);
            realSize = viewList.size() / 2;
        } else {
            realSize = viewList.size();
        }
        initPoints(realSize);
        if (viewList.size() == 1) {
            isAutoScroll = false;
            adapter.notifyDataSetChanged();
            //设置当前的item为一个无限循环的中间值
            mViewpager.setCurrentItem(0);
        } else {
            isAutoScroll = true;
            adapter.notifyDataSetChanged();
            //设置当前的item为一个VIEWPAGER_COUNT无限循环的中间值
            mViewpager.setCurrentItem(VIEWPAGER_COUNT / 2 - VIEWPAGER_COUNT / 2 % realSize);
        }
        lastPointIndex = 0;
        pointGroup.getChildAt(0).setEnabled(true);//设置第一个点viewList.size
    }

    private void initPoints(int size) {
        for (int i = 0; i < size; i++) {
            //设置下面指示点的指示个数
            ImageView point = new ImageView(getContext());
            point.setBackgroundResource(R.drawable.selector_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 3, 15, 8);
            point.setLayoutParams(params);
            point.setEnabled(false);
            pointGroup.addView(point);
        }
    }

    /**
     * 设置是否自动滚动
     *
     * @param autoScroll
     */
    public void setAutoScroll(boolean autoScroll) {
        if (autoScroll) {
            startAutoScroll();
        } else {
            stopAutoScroll();
        }
    }

    /**
     * 设置绑定Adapter
     *
     * @param adapter
     */
    public void setAdapter(BaseAutoScrollViewAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 设置是否可见指示点
     *
     * @param isVisible
     */
    public void setPointIsVisible(boolean isVisible) {
        if (pointGroup != null) {
            if (isVisible) {
                pointGroup.setVisibility(View.VISIBLE);
            } else {
                pointGroup.setVisibility(View.GONE);
            }
        }
    }

    protected void startAutoScroll() {
        if (isAutoScroll && handler != null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //停止,并且实际间隔大雨定义时长,且开启自动滚动,有handler处理对象
                    if (scrollStatus == ViewPager.SCROLL_STATE_IDLE && System.currentTimeMillis() - before >= auto_scroll_time && getContext() != null && handler != null) {
                        handler.sendEmptyMessage(HANDLER_MSG_ID);
                    }
                }
            }, auto_scroll_time, auto_scroll_time);
        }
    }

    public void changePage() {
        mViewpager.setCurrentItem(mViewpager.getCurrentItem() + 1);
    }

    protected void stopAutoScroll() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * pager监听器
     */
    @Override
    public void onPageSelected(int position) {
        before = System.currentTimeMillis();
        if (realSize == 0) {
            return;
        }
        int realPosition = position % realSize;
        // 设置当前point的状态和前一个point的状态
        pointGroup.getChildAt(realPosition).setEnabled(true);
        pointGroup.getChildAt(lastPointIndex).setEnabled(false);
        // 将当前的arg0赋值给lastPointIndex
        lastPointIndex = realPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        scrollStatus = state;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private static class ImageHandler extends Handler {
        private WeakReference<AutoScrollBanner> weakReference;

        protected ImageHandler(WeakReference<AutoScrollBanner> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AutoScrollBanner activity = weakReference.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case AutoScrollBanner.HANDLER_MSG_ID:
                    activity.changePage();
                    break;
                default:
                    break;
            }
        }
    }
}
