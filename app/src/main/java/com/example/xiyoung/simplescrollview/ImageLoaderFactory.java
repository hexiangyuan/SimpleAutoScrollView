package com.example.xiyoung.simplescrollview;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * imageLoad工具代码
 *
 * @author 何祥源
 */
public class ImageLoaderFactory {


    /**
     * 加载图片
     *
     * @param
     * @param
     */
    public static void loadImage(String imgUrl, ImageView imageView) {
       ImageLoader.getInstance().displayImage(imgUrl,imageView);
    }


    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(String imgUrl, ImageView imageView) {
        ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)// 添加正在加载中的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)// 设置当链接为空的图标
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)// 设置缓存
                .cacheOnDisk(true).considerExifParams(true)//
                .displayer(new RoundedBitmapDisplayer(90)).build();// 90表示圆角
        ImageLoader.getInstance().displayImage(imgUrl, imageView, options,
                animateFirstListener);
    }

    /**
     * 加载图片，根据自己的角度去加载
     */
    public static void loadImageWithAngle(String imgUrl, ImageView imageView,
                                          int angle) {
        ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)// 添加正在加载中的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)// 设置当链接为空的图标
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)// 设置缓存
                .cacheOnDisk(true).considerExifParams(true)//
                .displayer(new RoundedBitmapDisplayer(angle)).build();// 90表示圆角
        ImageLoader.getInstance().displayImage(imgUrl, imageView, options,
                animateFirstListener);
    }

    /**
     * 加载图片根据自己的定义去加载
     */
    public static void loadImageWithDisplayImageOptions(String imgUrl,
                                                        ImageView imageView, DisplayImageOptions options) {
        ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        ImageLoader.getInstance().displayImage(imgUrl, imageView, options,
                animateFirstListener);
    }

    /**
     * 动画加载效果
     *
     * @author Administrator
     */
    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        // 图片加载
        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 2000);// 设置动画渐变时间
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}

