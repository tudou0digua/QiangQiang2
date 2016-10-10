package com.cb.qiangqiang2.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cb on 2016/9/20.
 */
public class Win10Circle extends View{
    private int mWidth, mHeight;
    private Paint mPaint;
    private Path mPath;
    private Path mDstPath;
    private PathMeasure mPathMeasure;
    private ValueAnimator valueAnimator;
    private float t = 0;

    public Win10Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                t = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
//        valueAnimator.setInterpolator(new TimeInterpolator() {
//            @Override
//            public float getInterpolation(float input) {
//
//                return input;
//            }
//        });

        mDstPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2f, mHeight / 2f);
        mDstPath.reset();
        float length = mPathMeasure.getLength();
        float startPoints[] = new float[4];
        float increment = 0;
        for (int i = 0; i < startPoints.length; i++) {
            startPoints[i] = (1f + increment) * t - increment;
            increment += 0.05f;
        }
        float a = -1.1f;
        float b = 2.1f;
        for (int i = 0; i < startPoints.length; i++) {
//            mPathMeasure.getSegment((float) (length * Math.sin(startPoints[i] * Math.PI / 2)), (float) (length * Math.sin(startPoints[i] * Math.PI / 2) + 1), mDstPath, true);
            mPathMeasure.getSegment(length * (a * startPoints[i] * startPoints[i] + b * startPoints[i]),
                    length * (a * startPoints[i] * startPoints[i] + b * startPoints[i]) + 1, mDstPath, true);
        }
        canvas.drawPath(mDstPath, mPaint);
        if (t >= 0.9) {
            canvas.drawPoint(0, -150, mPaint);
        }
        if (!valueAnimator.isRunning()){
            valueAnimator.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        setPath();
    }

    private void setPath() {
        mPath = new Path();
        int diameter = Math.min(mWidth, mHeight);
        RectF rectF = new RectF(-150, -150, 150, 150);
        //选360度，会出现测量失误？
        mPath.addArc(rectF, -90f, 359.99f);
        mPathMeasure = new PathMeasure(mPath, false);
    }
}
