package com.testone.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SmileyAdapter extends PagerAdapter {

    private boolean supportShowHalf;
    private List<View> pageViews;

    public SmileyAdapter(List<View> pageViews, boolean supportShowHalf) {
        this.pageViews = pageViews;
        this.supportShowHalf = supportShowHalf;
    }

    @Override
    public int getCount() {
        return pageViews == null ? 0 : pageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.i("roger", "------- current destory position is " + position);
        container.removeView(pageViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(pageViews.get(position));
        Log.i("roger", "++++++++++ current instantiate position is " + position);
        return pageViews.get(position);
    }

    @Override
    public float getPageWidth(int position) {
        if (supportShowHalf) {
            if (position == 0) {
                return 0.58f;
            }
            return super.getPageWidth(position);
        } else {
            return super.getPageWidth(position);
        }
    }
}
