package com.testone.demo.utils.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class DialogViewHelper {

    private View mContentView = null;
    //防止内存泄漏
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this.mContentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId, null);
        init();
    }

    public DialogViewHelper() {
        init();
    }

    private void init() {
        mViews = new SparseArray<>();
    }

    /**
     * 设置文本
     *
     * @param viewId view
     * @param text   text
     */
    public void setText(int viewId, CharSequence text) {

        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 添加findViewById缓存
     *
     * @param viewId /
     * @param <T>    /
     * @return TextView
     */
    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null)
                mViews.put(viewId, new WeakReference<>(view));
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     *
     * @param viewId   布局id
     * @param listener 监听
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View tv = getView(viewId);
        if (tv != null) {
            tv.setOnClickListener(listener);
        }
    }

    /**
     * 获取ContentView
     *
     * @return VIEW
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置布局
     *
     * @param mView view
     */
    public void setContentView(View mView) {
        this.mContentView = mView;
    }
}
