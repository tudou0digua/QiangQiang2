package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.util.ToastUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;

public class ImageListActivity extends BaseActivity {
    public static final String PARAM_IMAGE_URLS = "param_image_urls";
    public static final String PARAM_CURRENT_URL = "param_current_url";

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;

    private ArrayList<String> imageUrls;
    private int startPos = 0;
    private PagerAdapter adapter;
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

        adapter = new PagerAdapter() {

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
                ToastUtil.show(ImageListActivity.this, list[i]);
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
