package com.cb.qiangqiang2.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

/**
 * Created by cb on 2016/12/10.
 */

public class URLImageParser {
    private Context mContext;
    private TextView mTextView;
    private int mImageSize;

    public URLImageParser(TextView textView, Context context, int imageSize) {
        mTextView = textView;
        mContext = context;
        mImageSize = imageSize;
    }

    public Drawable getDrawable(String url) {
        URLDrawable urlDrawable = new URLDrawable();
        new ImageGetterAsyncTask(mContext, url, urlDrawable).execute(mTextView);
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {
        private static final String TAG = "ImageGetterAsyncTask";

        private URLDrawable urlDrawable;
        private Context context;
        private String source;
        private TextView textView;

        public ImageGetterAsyncTask(Context context, String source, URLDrawable urlDrawable) {
            this.context = context;
            this.source = source;
            this.urlDrawable = urlDrawable;
        }

        @Override
        protected Bitmap doInBackground(TextView... params) {
            textView = params[0];
            try {
                Logger.d(TAG, "Downloading the image from: " + source);
                return Glide.with(context).load(source).asBitmap().fitCenter().into(mImageSize, mImageSize).get();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            try {
                Drawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                bitmapDrawable.setBounds(0, 0, mImageSize, mImageSize);
                urlDrawable.setBounds(0, 0, mImageSize, mImageSize);
                urlDrawable.drawable = bitmapDrawable;
                urlDrawable.invalidateSelf();
//                textView.invalidate();
            } catch (Exception e) {
                /* Like a null bitmap, etc. */
            }
        }
    }
}
