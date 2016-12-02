package com.cb.qiangqiang2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cb on 2016/12/2.
 */

public class ImageParamBean implements Parcelable{
    private int viewWidth;
    private int viewHeight;
    private int viewLeft;
    private int viewTop;
    private int imageWidth;
    private int imageHeight;

    public ImageParamBean() {
    }

    protected ImageParamBean(Parcel in) {
        viewWidth = in.readInt();
        viewHeight = in.readInt();
        viewLeft = in.readInt();
        viewTop = in.readInt();
        imageWidth = in.readInt();
        imageHeight = in.readInt();
    }

    public static final Creator<ImageParamBean> CREATOR = new Creator<ImageParamBean>() {
        @Override
        public ImageParamBean createFromParcel(Parcel in) {
            return new ImageParamBean(in);
        }

        @Override
        public ImageParamBean[] newArray(int size) {
            return new ImageParamBean[size];
        }
    };

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWight) {
        this.viewWidth = viewWight;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public int getViewLeft() {
        return viewLeft;
    }

    public void setViewLeft(int viewLeft) {
        this.viewLeft = viewLeft;
    }

    public int getViewTop() {
        return viewTop;
    }

    public void setViewTop(int viewTop) {
        this.viewTop = viewTop;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWight) {
        this.imageWidth = imageWight;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(viewWidth);
        parcel.writeInt(viewHeight);
        parcel.writeInt(viewLeft);
        parcel.writeInt(viewTop);
        parcel.writeInt(imageWidth);
        parcel.writeInt(imageHeight);
    }
}
