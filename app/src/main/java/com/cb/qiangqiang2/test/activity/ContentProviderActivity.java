package com.cb.qiangqiang2.test.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;

import butterknife.BindView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class ContentProviderActivity extends BaseAutoLayoutActivity {

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_content_provider;
    }

    private void initView() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
    }

    private void readContact() {
        StringBuilder builder = new StringBuilder();
        try {
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                builder.append(name).append("---").append(number).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvPhone.setText(builder.toString());
    }

    @OnClick({R.id.btn_get_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_phone:
                readContact();
                break;
        }
    }
}
