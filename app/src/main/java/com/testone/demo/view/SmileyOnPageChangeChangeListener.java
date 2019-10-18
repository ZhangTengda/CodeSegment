package com.testone.demo.view;

import android.support.v4.view.ViewPager;
import android.util.Log;

public class SmileyOnPageChangeChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i("111111111111","onPageScrolled+==position:"
                +position+"===positionOffset:"+positionOffset+"===posPixels:"+positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        Log.i("111111111111","onPageSelected======position:"+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        switch (state) {
            // 空闲状态
            case ViewPager.SCROLL_STATE_IDLE:
                Log.i("111111111111","onPageScrollStateChanged  空闲状态");
                break;
            // 滑动后自然沉降的状态
            case ViewPager.SCROLL_STATE_SETTLING:
                Log.i("111111111111","onPageScrollStateChanged  滑动后自然沉降的状态");
                break;
            // 滑动状态
            case ViewPager.SCROLL_STATE_DRAGGING:
                Log.i("111111111111","onPageScrollStateChanged  滑动状态");
                break;
        }

    }
}
