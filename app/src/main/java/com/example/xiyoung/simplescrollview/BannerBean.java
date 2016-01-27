package com.example.xiyoung.simplescrollview;

/**
 * Created by 何祥源 on 16/1/14.
 * Desc:
 */
public class BannerBean{
    String title;
    String imagPath;

    public BannerBean() {
    }

    public BannerBean(String title, String imagPath) {
        this.title = title;
        this.imagPath = imagPath;
    }

    @Override
    public String toString() {
        return "adBean [title=" + title + ", imagPath=" + imagPath + "]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagPath() {
        return imagPath;
    }

    public void setImagPath(String imagPath) {
        this.imagPath = imagPath;
    }

}
