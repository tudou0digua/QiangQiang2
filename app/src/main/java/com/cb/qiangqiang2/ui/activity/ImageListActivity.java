package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.util.ToastUtil;
import com.cb.qiangqiang2.ui.view.CustomPageAdapter;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

public class ImageListActivity extends BaseActivity {
    public static final String PARAM_IMAGE_URLS = "param_image_urls";
    public static final String PARAM_CURRENT_URL = "param_current_url";

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;

    private ArrayList<String> imageUrls;
    private int startPos = 0;
    private CustomPageAdapter adapter;
    private LayoutInflater inflater;

    public static void startActivity(Context context,  String currentUrl, ArrayList<String> urls) {
        Intent intent = new Intent(context, ImageListActivity.class);
        intent.putExtra(PARAM_CURRENT_URL, currentUrl);
        intent.putStringArrayListExtra(PARAM_IMAGE_URLS, urls);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_list;
    }

    @Override
    protected void initData() {
        imageUrls = getIntent().getStringArrayListExtra(PARAM_IMAGE_URLS);
        if (imageUrls == null || imageUrls.size() <= 0) {
            finish();
        }
        String currentUrl = getIntent().getStringExtra(PARAM_CURRENT_URL);
        if (imageUrls != null && currentUrl != null && imageUrls.contains(currentUrl)) {
            startPos = imageUrls.indexOf(currentUrl);
        }

        if (imageUrls == null) {
            imageUrls = new ArrayList<>();
        }

        inflater = LayoutInflater.from(ImageListActivity.this);

        adapter = new CustomPageAdapter() {

            @Override
            public int getCount() {
                return imageUrls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View rootView = inflater.inflate(R.layout.item_image_list, container, false);
                PhotoView photoView = rootView.findViewById(R.id.photo_view);
                String url = imageUrls.get(position);
                Glide.with(container.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(photoView);

                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        showOperationDialog();
                        return false;
                    }
                });
                container.addView(rootView);
                return rootView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndicator.setText(getString(R.string.image_list_indicator, position + 1, imageUrls.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initView() {
        tvIndicator.setText(getString(R.string.image_list_indicator, startPos + 1, imageUrls.size()));
        viewPager.setCurrentItem(startPos);
    }

    private void showOperationDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog
                .Builder(ImageListActivity.this);

        final String[] list = new String[]{"保存图片", "识别图中二维码"};

        builder.setTitle("");
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //保存图片
                        saveImageToLocal();
                        break;
                    case 1:
                        //识别图中二维码
                        detectQrCodeInImage();
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (lp != null) {
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        window.setWindowAnimations(R.style.StyleBottomInOut);
        dialog.show();
    }

    private void saveImageToLocal() {

    }

    private void detectQrCodeInImage() {
        if (adapter != null && adapter.getCurrentItemView() != null) {
            ViewGroup container = adapter.getCurrentItemView();
            PhotoView photoView = container.findViewById(R.id.photo_view);
            if (photoView != null && photoView.getDrawable() != null
                    && ((GlideBitmapDrawable) photoView.getDrawable()).getBitmap() != null) {
                String content = QRCodeDecoder.syncDecodeQRCode(((GlideBitmapDrawable) photoView.getDrawable()).getBitmap());
                if (!TextUtils.isEmpty(content)) {
                    showDealQrcodeContentDialog(content);
                } else {
                    ToastUtil.show(ImageListActivity.this, "未识别到二维码");
                }
            }
        }
    }

    private void showDealQrcodeContentDialog(final String qrcodeContent) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog
                .Builder(ImageListActivity.this);

//        final String[] list = new String[]{"复制", "在浏览器打开", "在支付宝打开", "在淘宝打开", "在天猫打开", "在京东打开"};
        final String[] list = new String[]{"复制", "浏览器打开"};

        builder.setTitle(qrcodeContent);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //复制

                        break;
                    case 1:
                        //浏览器打开
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri qrcode_url = Uri.parse(qrcodeContent);
                        intent.setData(qrcode_url);
                        startActivity(intent);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (lp != null) {
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        window.setWindowAnimations(R.style.StyleBottomInOut);
        dialog.show();
    }
}
