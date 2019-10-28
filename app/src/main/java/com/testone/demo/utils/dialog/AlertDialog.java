package com.testone.demo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.testone.demo.R;

public class AlertDialog extends Dialog {

    private AlertController mAlert;

    protected AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    /**
     * 设置文本
     *
     * @param viewId view
     * @param text   text
     */
    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
    }

    /**
     * 添加findViewById缓存
     *
     * @param viewId /
     * @param <T>    /
     * @return TextView
     */
    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId   布局id
     * @param listener 监听
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mAlert.setOnClickListener(viewId, listener);
    }

    public static class Builder {

        private final AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }

        /**
         * 设置布局
         *
         * @param view view
         * @return /
         */
        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置布局id
         *
         * @param viewLayoutResId id
         * @return /
         */
        public Builder setContentView(int viewLayoutResId) {
            P.mView = null;
            P.mViewLayoutResId = viewLayoutResId;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置文本
         *
         * @param viewId 控件id
         * @param text   文本
         * @return /
         */
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId          设置点击事件的控件id
         * @param onClickListener /
         * @return /
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            P.mClickArray.put(viewId, onClickListener);
            return this;
        }

        /**
         * 设置是否全屏
         *
         * @return /
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置从底部弹出
         *
         * @param isAnimation 是否有动画
         * @return /
         */
        public Builder formBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置dialog 的宽高
         *
         * @param width  宽度
         * @param height 高度
         * @return /
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 设置默认动画
         *
         * @return /
         */
        public Builder addDefaultAnimation() {
            P.mAnimation = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置动画
         *
         * @return /
         */
        public Builder setAnimation(int styleAnimation) {
            P.mAnimation = styleAnimation;
            return this;
        }

        /**
         * 设置点击屏幕其他地方是否关闭dialog
         *
         * @return /
         */
        public Builder setCancelable(boolean isCancelable) {
            P.mCancelable = isCancelable;
            return this;
        }

        public AlertDialog create() {
            final AlertDialog dialog = new AlertDialog(P.context, P.themeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * 显示dialog
         *
         * @return /
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
