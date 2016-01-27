#SimpleAutoScrollView
####效果展示
![](https://github.com/hexiangyuan/SimpleAutoScrollView/blob/master/simple.gif)  

#### 使用方法
* 1.布局文件中引用

```xml
<com.example.xiyoung.simplescrollview.simpleautoscrollview.AutoScrollBanner
        android:id="@+id/scroolBanner"
        android:layout_width="match_parent"
        android:layout_height="200dp" />

```
* 1.设置适配器继承BaseAdapter以及绑定控件


```
public class Adapter extends BaseAutoScrollViewAdapter {
        List<BannerBean> datas = new ArrayList<>();
        List<View> views = new ArrayList<>();
       public Adapter(List<BannerBean> dataList, List<View> list) {
            super(dataList, list);
            datas = dataList;
            views = list;
        }
        @Override
        public void bindView(View view, int position) {
        //这里是绑定每一个View的方法

        }
    }
```
    
 * 3.初始化适配器以及控件属性就Ok了

```
Adapter adapter = new Adapter(bannerBeans, viewList);
autoScrollBanner.setAutoScroll(true);//设置是否可以自动滚动
autoScrollBanner.setPointIsVisible(true);//指示点是否可见
autoScrollBanner.setAdapter(adapter);//设置适配器,绑定View
autoScrollBanner.setViewList(viewList);//添加Viewlist
```