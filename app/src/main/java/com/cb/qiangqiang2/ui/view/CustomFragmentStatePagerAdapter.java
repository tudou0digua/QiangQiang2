package com.cb.qiangqiang2.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by chenbin on 2018/2/26.
 * @see <a href="https://stackoverflow.com/questions/8785221/retrieve-a-fragment-from-a-viewpager">retrieve-a-fragment-from-a-viewpager</a>
 */
public abstract class CustomFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private Fragment currentFragment;

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public CustomFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            this.currentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }
}
