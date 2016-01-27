package com.example.xiyoung.simplescrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiyoung.simplescrollview.simpleautoscrollview.AutoScrollBanner;
import com.example.xiyoung.simplescrollview.simpleautoscrollview.BaseAutoScrollViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AutoScrollBanner autoScrollBanner, autoScrollBanner2;
    private List<BannerBean> bannerBeans;
    private List<BannerBean2> bannerBeans2;
    private List<View> viewList = new ArrayList<>();
    private List<View> viewList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        setContentView(R.layout.activity_main);
        autoScrollBanner = (AutoScrollBanner) findViewById(R.id.scroolBanner);
        autoScrollBanner2 = (AutoScrollBanner) findViewById(R.id.scroolBanner2);
        //设置第一个Banner
        initList();
        initViewList();
        initAutoBanner();
        //设置第二个banner
        initList2();
        initViewList2();
        initAutoBanner2();
    }

    private void initAutoBanner2() {
        Adapter2 adapter2 = new Adapter2(bannerBeans2, viewList2);
        autoScrollBanner2.setAutoScroll(true);//设置是否可以自动滚动
        autoScrollBanner2.setPointIsVisible(false);//指示点是否可见
        autoScrollBanner2.setAdapter(adapter2);//设置适配器,绑定View
        autoScrollBanner2.setViewList(viewList2);//添加Viewlist
    }

    private void initViewList2() {
        for (int i = 0; i < bannerBeans2.size(); i++) {
            viewList2.add(View.inflate(this, R.layout.view_auto_scroll_banner2, null));
        }
    }

    private void initList2() {
        bannerBeans2 = new ArrayList<>();
        bannerBeans2.add(new BannerBean2("小学僧", "http://pic.duowan.com/lol/1103/164979821556/164979874675.jpg", "http://ol.15w.com/uploads/allimg/140122/15452240R-5.png", "http://upload.ct.youth.cn/2013/0704/1372908452902.jpg"));
        bannerBeans2.add(new BannerBean2("托儿索", "http://i3.17173.itc.cn/2013/lol/zt/yasuo/yasuo10.jpg", "http://www.demaxiya.com/uploads/allimg/140822/1_140822170553_1.jpg", "http://att.bbs.duowan.com/forum/201401/05/114421cr7rhj12rrjb93lc.jpg"));
        bannerBeans2.add(new BannerBean2("儿童劫", "http://www.lolqu.com/uploads/allimg/c140805/140H4JU59250-36019.png", "https://i.ytimg.com/vi/pWOaho6bQr8/hqdefault.jpg", "http://img.miigii.com.tw/Files/Topic/2012-12-04_1739573506_thumb.jpg"));
    }

    private void initAutoBanner() {
        Adapter adapter = new Adapter(bannerBeans, viewList);
        autoScrollBanner.setAutoScroll(true);//设置是否可以自动滚动
        autoScrollBanner.setPointIsVisible(true);//指示点是否可见
        autoScrollBanner.setAdapter(adapter);//设置适配器,绑定View
        autoScrollBanner.setViewList(viewList);//添加Viewlist
    }

    private void initViewList() {
        for (int i = 0; i < bannerBeans.size(); i++) {
            viewList.add(View.inflate(this, R.layout.layout_banner, null));
        }
    }

    private void initList() {
        bannerBeans = new ArrayList<>();
        bannerBeans.add(new BannerBean("Image1", "http://l.paipaitxt.com/118851/12/02/09/104_15627737_34ee313119cd3f6.jpg"));
        bannerBeans.add(new BannerBean("Image2", "http://tse1.mm.bing.net/th?id=OIP.Mbaa904cff0f871ebfe1e220af3784805o0&pid=15.1"));
        bannerBeans.add(new BannerBean("Image3", "http://i0.sinaimg.cn/travel/2015/0507/U9385P704DT20150507151553.jpg"));
        bannerBeans.add(new BannerBean("Image4", "http://photo.880sy.com/4/2893/110762.jpg"));
    }

    class Adapter extends BaseAutoScrollViewAdapter {
        List<BannerBean> datas = new ArrayList<>();
        List<View> views = new ArrayList<>();

        public Adapter(List<BannerBean> dataList, List<View> list) {
            super(dataList, list);
            datas = dataList;
            views = list;
        }

        @Override
        public void bindView(View view, int position) {
            ImageLoaderFactory.loadImage(datas.get(position).getImagPath(), (ImageView) view.findViewById(R.id.imageView));
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(datas.get(position).getTitle());
        }

    }

    class Adapter2 extends BaseAutoScrollViewAdapter<BannerBean2> {
        List<BannerBean2> datas = new ArrayList<>();
        List<View> views = new ArrayList<>();

        public Adapter2(List<BannerBean2> dataList, List<View> viewList) {
            super(dataList, viewList);
            datas = dataList;
            views = viewList;
        }

        @Override
        public void bindView(View view, int position) {
            ImageView ivIcon = (ImageView) view.findViewById(R.id.ivICon);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            ImageView ivImg1 = (ImageView) view.findViewById(R.id.ivImg1);
            ImageView ivImg2 = (ImageView) view.findViewById(R.id.ivImg2);
            ImageLoaderFactory.loadCircleImage(datas.get(position).imagePath1, ivIcon);
            ImageLoaderFactory.loadImage(datas.get(position).imagePath2, ivImg1);
            ImageLoaderFactory.loadImage(datas.get(position).imagePath3, ivImg2);
            tvName.setText(datas.get(position).title);

        }
    }
}
