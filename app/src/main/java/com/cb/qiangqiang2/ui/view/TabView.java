package com.cb.qiangqiang2.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.cb.qiangqiang2.R;

/**
 * Created by cb on 2016/10/17.
 */

public class TabView extends View {
    private static final int DEFAULT_COLOR = 0xBDBDBD;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_PADDING = 2;
    private static final String INSTANCE_STATUS = "instance_status";
    private static final String ALPHA_STATUS = "alpha_status";

    private float mAlpha;
    private Bitmap mDefaultImage;
    private Bitmap mSelectedImage;
    private int mDefaultColor;
    private int mSelectedColor;
    private int mTextSize;
    private int mTextPadding;
    private String mText;

    private TextPaint mTextPaint;
    private Paint mImagePaint;
    private Rect mTextBounds;
    private Rect mImageRect;
    private int mWidth;
    private int mHeight;
    private GestureDetector mGestureDetector;

    public TabView(Context context) {
        super(context);
        initView(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabView);
            if (array.getDrawable(R.styleable.TabView_imageDefault) != null) {
                mDefaultImage = ((BitmapDrawable)array.getDrawable(R.styleable.TabView_imageDefault)).getBitmap();
            }
            if (array.getDrawable(R.styleable.TabView_imageSelected) != null) {
                mSelectedImage = ((BitmapDrawable)array.getDrawable(R.styleable.TabView_imageSelected)).getBitmap();
            }
            mAlpha = array.getFloat(R.styleable.TabView_alpha, 0);
            mDefaultColor = array.getColor(R.styleable.TabView_coloDefault, DEFAULT_COLOR);
            mSelectedColor = array.getColor(R.styleable.TabView_coloSelected, DEFAULT_COLOR);
            mTextSize = array.getDimensionPixelSize(R.styleable.TabView_textSize, dip2px(DEFAULT_TEXT_SIZE));
            mTextPadding = array.getDimensionPixelSize(R.styleable.TabView_textPadding, dip2px(DEFAULT_TEXT_PADDING));
            String text = array.getString(R.styleable.TabView_text);
            mText = text == null ? "" : text;
            array.recycle();
        } else {
            mDefaultColor = DEFAULT_COLOR;
            mSelectedColor = DEFAULT_COLOR;
            mTextSize = dip2px(DEFAULT_TEXT_SIZE);
            mTextPadding = dip2px(DEFAULT_TEXT_PADDING);
            mText = "";
            mAlpha = 0;
        }

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mImagePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mImagePaint.setFilterBitmap(true);
        mTextBounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        mImageRect = new Rect();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(ALPHA_STATUS, mAlpha);
        return bundle;
    }
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            mAlpha = bundle.getFloat(ALPHA_STATUS);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int alpha = Math.round(255 * mAlpha);
        if (alpha != 255) drawImage(255 - alpha, canvas, mDefaultImage, mDefaultColor);
        if (alpha != 0) drawImage(alpha, canvas, mSelectedImage, mSelectedColor);
        if (alpha != 255) drawText(255 - alpha, canvas, mDefaultColor);
        if (alpha != 0) drawText(alpha, canvas, mSelectedColor);
    }

    private void drawText(int alpha, Canvas canvas, int textColor) {
        mTextPaint.setColor(textColor);
        mTextPaint.setAlpha(alpha);
        int baseX = (int) ((mWidth - getPaddingLeft() - getPaddingRight() - mTextPaint.measureText(mText)) / 2 + getPaddingLeft());
        int baseY = (int) (mHeight - getPaddingBottom() - mTextPaint.descent());
        canvas.drawText(mText, baseX, baseY, mTextPaint);
    }

    private void drawImage(int alpha, Canvas canvas, Bitmap bitmap, int color) {
        if (bitmap == null) return;
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        int imageRectHeight = mHeight - getPaddingTop() - getPaddingBottom() - mTextPadding - mTextBounds.height();
        int imageRectWidth = Math.round(imageRectHeight * (imageWidth * 1.0f / imageHeight));
        int left = (mWidth - getPaddingLeft() - getPaddingRight() - imageRectWidth) / 2 + getPaddingLeft();
        mImageRect = new Rect(left, getPaddingTop(), left + imageRectWidth, getPaddingTop() + imageRectHeight);

        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        mImagePaint.setColor(color);
        mImagePaint.setAlpha(alpha);
        canvas.drawRect(mImageRect, mImagePaint);
        mImagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mImagePaint.setAlpha(255);
        canvas.drawBitmap(bitmap, null, mImageRect, mImagePaint);
        mImagePaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //使用mGestureDetector监听tabview的双击事件
        if (mGestureDetector != null) return mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void setGestureDetector(GestureDetector mGestureDetector) {
        this.mGestureDetector = mGestureDetector;
    }

    public void setmAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    public void setUnSelected(){
        this.mAlpha = 0;
        invalidateView();
    }

    public void setSelected(){
        this.mAlpha = 1.0f;
        invalidateView();
    }

    private void invalidateView() {
        if(Looper.getMainLooper() == Looper.myLooper()){
            invalidate();
        }else {
            postInvalidate();
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
