package com.example.xiyoung.simplescrollview.simpleautoscrollview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/*******************************************
 * Created by 何祥源 on 16/1/22.
 * <p/>
 * Desc:这个是baseAnatoScrollAdapter
 * *******************************************
 */
public abstract class BaseAutoScrollViewAdapter<T> extends PagerAdapter {
    private List<T> dataList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();


    public BaseAutoScrollViewAdapter(List<T> dataList, List<View> viewList) {
        this.dataList = dataList;
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if (viewList == null) {
            return 0;
        }
        if (viewList.size() == 1) {
            return 1;
        }
        return AutoScrollBanner.VIEWPAGER_COUNT;// 伪无限制循环,将界面放置于一个很大的数额页码;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View v = null;
        if (viewList != null && viewList.size() != 0) {
            if (viewList.size() == 1) {
                v = viewList.get(0);
            } else {
                v = viewList.get(position % viewList.size());
            }

            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
            if(viewList.size()==0){
                return  null;
            }
            bindView(v,position % viewList.size());
            container.addView(v);
        }
        return v;
    }

    @Override
    public void destroyItem (ViewGroup container,int position, Object object){
    }

    /**
     * 实现此方法可以在adapter中初始化View的视图;
     * @param view
     * @param position
     */
    public abstract void bindView(View view,int position);//创建BindView
}