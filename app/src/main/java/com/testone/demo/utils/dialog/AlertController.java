package com.testone.demo.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    AlertController(AlertDialog mDialog, Window mWindow) {
        this.mDialog = mDialog;
        this.mWindow = mWindow;
    }

    private void setViewHelper(DialogViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }

    /**
     * 设置文本
     *
     * @param viewId view
     * @param text   text
     */
    void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    /**
     * 添加findViewById缓存
     *
     * @param viewId /
     * @param <T>    /
     * @return TextView
     */
    <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId   布局id
     * @param listener 监听
     */
    void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);
    }


    /**
     * 获取dialog
     *
     * @return /
     */
    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取dialog的window
     *
     * @return /
     */
    private Window getWindow() {
        return mWindow;
    }

    static class AlertParams {

        Context context;
        int themeResId;
        //点击空白是否能够取消
        boolean mCancelable = true;
        //dialog Cancel监听
        DialogInterface.OnCancelListener mOnCancelListener;
        //dialog Dismiss监听
        DialogInterface.OnDismissListener mOnDismissListener;
        //dialog Key监听
        DialogInterface.OnKeyListener mOnKeyListener;
        //布局View
        View mView;
        //布局的layout
        int mViewLayoutResId;
        //存放文本的修改
        SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放点击事件
        SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        //设置宽度
        int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        //设置弹出动画
        int mAnimation = 0;
        //位置
        int mGravity = Gravity.CENTER;
        //设置高度
        int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        AlertParams(Context context, int themeResId) {
            this.context = context;
            this.themeResId = themeResId;
        }


        /**
         * 绑定和设置参数
         *
         * @param mAlert /
         */
        void apply(AlertController mAlert) {

            //设置布局
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(context, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new NullPointerException("布局不能为空(setContentView(null))");
            }
            mAlert.getDialog().setContentView(viewHelper.getContentView());


            //设置Controller的辅助类
            mAlert.setViewHelper(viewHelper);


            //设置文本
            int size = mTextArray.size();
            for (int i = 0; i < size; i++) {
                mAlert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }


            //设置点击事件
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                mAlert.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //设置自定义效果  全屏  从底部弹出  默认动画等
            Window window = mAlert.getWindow();

            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimation != 0)
                window.setWindowAnimations(mAnimation);
            //设置宽高
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            window.setAttributes(attributes);
        }
    }
}
