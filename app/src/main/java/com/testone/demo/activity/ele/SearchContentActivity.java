package com.testone.demo.activity.ele;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.testone.demo.R;

public class SearchContentActivity extends Activity {
    private TextView mSearchBGTxt;
    private EditText editText;
    private TextView mSearchTxt;
    private FrameLayout mContentFrame;
//    private ImageView mArrowImg;
    private boolean finishing;
    private float originX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele_search);
        initView();
        execute();
    }

    private void initView() {
        // 一个占位的 TextView
        mSearchBGTxt = (TextView) findViewById(R.id.tv_search_bg);


        // 中间显示的EditText
        editText = (EditText) findViewById(R.id.tv_hint);

        // 底下的Fraglayout
        mContentFrame = (FrameLayout) findViewById(R.id.frame_content_bg);

        // 右侧显示的取消按钮
        mSearchTxt = (TextView) findViewById(R.id.tv_search);
        mSearchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!finishing) {
                    finishing = true;
                    performExitAnimation();
                }
            }
        });

        ///////////
//        Rect imgRect = new Rect();
//        FrameLayout.LayoutParams focusItemParams = new FrameLayout.LayoutParams(10, 10);
//        selected.getGlobalVisibleRect(imgRect);
//
//        focusItemParams.leftMargin =imgRect.left;
//        focusItemParams.topMargin =imgRect.top;
//        focusItemParams.width = imgRect.width();
//        focusItemParams.height = imgRect.height();
//        selected.getLocationInWindow (viewPosition);
//        focusView.setLayoutParams(focusItemParams);//focusView为你需要设置位置的VIEW
//
//        注：在设置位置的时候，父layout尽量使用framelayout或者relativlayout，当然如果你非要用linearlayout的话
//        ，就不要用权重了，不然到时候坐边距和上边距，你都不知道设置多少

        // 左侧的返回箭头
//        mArrowImg = (ImageView) findViewById(R.id.iv_arrow);
//        mArrowImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!finishing) {
//                    finishing = true;
//                    performExitAnimation();
//                }
//            }
//        });
    }

    // 进来执行的动画
    private void execute() {
        mSearchBGTxt.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mSearchBGTxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        performEnterAnimation();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (!finishing) {
            finishing = true;
            performExitAnimation();
        }
    }

    // 进来执行的动画
    private void performEnterAnimation() {
        float y = mSearchBGTxt.getY();
        Log.i("111111111111","进来初始化：mSearchBGTxt.getY()："+y);
        initLocation();
        final float top = getResources().getDisplayMetrics().density * 20;

        Log.i("111111111111","进来执行的动画：mSearchBGTxt.getY()："+mSearchBGTxt.getY()+"======="+"top=" + top);
        final ValueAnimator translateVa = translateVa(mSearchBGTxt.getY(), top);
        final ValueAnimator scaleVa = scaleVa(1, 0.8f);
        final ValueAnimator alphaVa = alphaVa(0, 1f);
        originX = editText.getX();
//        final float leftSpace = mArrowImg.getRight() * 2;
//        final ValueAnimator translateVaX = translateVax(originX, leftSpace);
        final ValueAnimator translateVaX = translateVax(originX, originX);

        setDuration(translateVa, scaleVa, translateVaX, alphaVa);
        star(translateVa, scaleVa, translateVaX, alphaVa);
    }

    // 退出执行的动画
    private void performExitAnimation() {
        final float translateY = getTranslateY();
        final ValueAnimator translateVa = translateVa(mSearchBGTxt.getY(), mSearchBGTxt.getY() + translateY);
        final ValueAnimator scaleVa = scaleVa(0.8f, 1f);
        final ValueAnimator alphaVa = alphaVa(1f, 0f);
        exitListener(translateVa);
        final float currentX = editText.getX();
        ValueAnimator translateVaX = translateVax(currentX, originX);

        setDuration(translateVa, scaleVa, translateVaX, alphaVa);
        star(translateVa, scaleVa, translateVaX, alphaVa);
    }

    // 进来执行的动画设置位置
    private void initLocation() {
        final float translateY = getTranslateY();
        //放到前一个页面的位置
        mSearchBGTxt.setY(mSearchBGTxt.getY() + translateY);
        editText.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - editText.getHeight()) / 2);
        mSearchTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mSearchTxt.getHeight()) / 2);
    }

    private float getTranslateY() {
        float originY = getIntent().getIntExtra("y", 0);
        int[] location = new int[2];
        mSearchBGTxt.getLocationOnScreen(location);
        return originY - (float) location[1];
    }

    @NonNull
    private ValueAnimator translateVax(float from, float to) {
        ValueAnimator translateVaX = ValueAnimator.ofFloat(from, to);
        translateVaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                editText.setX(value);
            }
        });
        return translateVaX;
    }

    private ValueAnimator translateVa(float from, float to) {
        ValueAnimator translateVa = ValueAnimator.ofFloat(from, to);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSearchBGTxt.setY((Float) valueAnimator.getAnimatedValue());
//                mArrowImg.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mArrowImg.getHeight()) / 2);
                editText.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - editText.getHeight()) / 2);
                mSearchTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mSearchTxt.getHeight()) / 2);
            }
        });
        return translateVa;
    }

    private ValueAnimator scaleVa(float from, float to) {
        ValueAnimator scaleVa = ValueAnimator.ofFloat(from, to);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSearchBGTxt.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        return scaleVa;
    }

    @NonNull
    private ValueAnimator alphaVa(float from, float to) {
        ValueAnimator alphaVa = ValueAnimator.ofFloat(from, to);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
                mSearchTxt.setAlpha((Float) valueAnimator.getAnimatedValue());
//                mArrowImg.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        return alphaVa;
    }

    private void exitListener(ValueAnimator translateVa) {
        translateVa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setDuration(ValueAnimator translateVa, ValueAnimator scaleVa, ValueAnimator translateVaX, ValueAnimator alphaVa) {
        alphaVa.setDuration(350);
        translateVa.setDuration(350);
        scaleVa.setDuration(350);
        translateVaX.setDuration(350);
    }

    private void star(ValueAnimator translateVa, ValueAnimator scaleVa, ValueAnimator translateVaX, ValueAnimator alphaVa) {
        alphaVa.start();
        translateVa.start();
        scaleVa.start();
        translateVaX.start();
    }
}
