package com.testone.demo.activity.smiley;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.FlowLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.adapter.RecyclerSmileyAdapter;
import com.testone.demo.adapter.SmileyAdapter;
import com.testone.demo.utils.NetWorkManagementUtils;
import com.testone.demo.utils.ScreenUtil;
import com.testone.demo.utils.SharedPreferenceUtil;
import com.testone.demo.utils.ToastUtil;
import com.testone.demo.view.SmileyOnPageChangeChangeListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoUI extends BaseActivity {

    public static final String COMMON_USE_SMILEY_KEY = "common_use_smiley_key";
    private List<ConversationSmiley> commonlyUseSmily;
    private List<ConversationSmiley> commonlyUseSmilyRun;

    private List<RecyclerSmileyAdapter> smileyAdapters = new ArrayList<>();

    private List<ImageView> pointViews = new ArrayList<>();

    private List<View> pageViews = new ArrayList<>();
    private boolean showRecentlyUsed;

    private ViewPager viewPager;
    private int mCurrentPage;
    private LinearLayout smiley_panel_dot;
    private RecyclerSmileyAdapter recyclerSmileyAdapter;
    private List<List<ConversationSmiley>> emojis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initView();

        initSmilely();

        initData();

//        initDemo();
    }

    private void initSmilely() {
        commonlyUseSmilyRun = new ArrayList<>();
        commonlyUseSmily = new ArrayList<>();

        final ConversationSmileyConversionUtil util = ConversationSmileyConversionUtil.getInstace(this);

        final String value = (String) SharedPreferenceUtil.getParam(this, COMMON_USE_SMILEY_KEY, "");
        if (!TextUtils.isEmpty(value)) {
            final Map<String, ConversationSmiley> emojiMap = util.getEmojiMap(this);
            final String[] split = value.split(",");
            Map<String, ConversationSmiley> temp = new HashMap<>(9);
            for (final ConversationSmiley smiley : emojiMap.values()) {
                for (final String resName : split) {
                    if (TextUtils.equals(resName, smiley.getResName())) {
                        temp.put(resName, smiley);
                    }
                }
            }
            // 使用 map 做转换是为了解决顺序问题
            for (final String resName : split) {
                if (temp.get(resName) != null) {
                    commonlyUseSmily.add(temp.get(resName));
                }
            }
        }
        commonlyUseSmilyRun.addAll(commonlyUseSmily);
    }

    private void initData() {

        emojis = ConversationSmileyConversionUtil.getInstace(this).getSmileyList(this);
        if (commonlyUseSmily != null && commonlyUseSmily.size() > 0) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.nine_smiley_layout, null);
            RecyclerView commonly_use = linearLayout.findViewById(R.id.use_recycler);
            commonly_use.setLayoutManager(new GridLayoutManager(this, 3));
            RecyclerSmileyAdapter adapterCommonly_use = new RecyclerSmileyAdapter(this, commonlyUseSmily);
            commonly_use.setAdapter(adapterCommonly_use);
            adapterCommonly_use.setOnItemClickListener(new RecyclerSmileyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    ConversationSmiley conversationSmiley = commonlyUseSmily.get(position);
                    ToastUtil.showToast(DemoUI.this, conversationSmiley.getText() + "Hello");
                }
            });
            smileyAdapters.add(adapterCommonly_use);
            pageViews.add(linearLayout);
            showRecentlyUsed = true;
        }

        for (int i = 0; i < emojis.size(); i++) {
            List<ConversationSmiley> conversationSmilies = emojis.get(i);
            LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.smiley_flowlayout, null);
            RecyclerView recyclerView = inflate.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 7));

            recyclerSmileyAdapter = new RecyclerSmileyAdapter(this, conversationSmilies);
            recyclerView.setAdapter(recyclerSmileyAdapter);
            recyclerSmileyAdapter.setOnItemClickListener(new RecyclerSmileyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    ConversationSmiley conversationSmiley;
                    if (showRecentlyUsed) {
                        if (mCurrentPage == 0 || mCurrentPage == 1) {
                            conversationSmiley = emojis.get(0).get(position);
                        } else {
                            conversationSmiley = emojis.get(mCurrentPage - 1).get(position);
                        }
                    } else {
                        conversationSmiley = emojis.get(mCurrentPage).get(position);
                    }
                    ToastUtil.showToast(DemoUI.this, conversationSmiley.getText() + "Hello");

                    boolean isContains = false;
                    int index = 0;
                    for (int i = 0; i < commonlyUseSmilyRun.size(); i++) {
                        if (TextUtils.equals(conversationSmiley.getKey(), commonlyUseSmilyRun.get(i).getKey())) {
                            index = i;
                            isContains = true;
                            break;
                        }
                    }
                    if (isContains) {
                        if (!TextUtils.isEmpty(conversationSmiley.getKey())) {
                            commonlyUseSmilyRun.remove(index);
                            commonlyUseSmilyRun.add(0, conversationSmiley);
                        }
                    } else {
                        if (!TextUtils.isEmpty(conversationSmiley.getKey())) {
                            commonlyUseSmilyRun.add(0, conversationSmiley);
                        }
                    }
                    if (commonlyUseSmilyRun.size() > 8) {
                        commonlyUseSmilyRun = commonlyUseSmilyRun.subList(0, 9);
                    }
                    saveCommonUseSmiley(DemoUI.this, commonlyUseSmilyRun);
                }
            });
            smileyAdapters.add(recyclerSmileyAdapter);
            pageViews.add(inflate);
        }

        for (int i = 0; i < pageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.mx_d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ScreenUtil.dp2px(this, 6), ScreenUtil.dp2px(this, 6));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;

            smiley_panel_dot.addView(imageView, layoutParams);
            if (i == 1) {
                if (showRecentlyUsed) {
                    imageView.setBackgroundResource(R.drawable.recently_used_icon_black);
                } else {
                    imageView.setBackgroundResource(R.drawable.mx_d2);
                }
            }
            pointViews.add(imageView);
        }

        // 设置Viewpager
        SmileyAdapter smileyAdapter = new SmileyAdapter(pageViews, true);
        viewPager.setAdapter(smileyAdapter);
        viewPager.addOnPageChangeListener(new SmileyOnPageChangeChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("111111111111", "onPageScrolled+==position:"
                        + position + "===positionOffset:" + positionOffset + "===viewPager.getCurrentItem():" + viewPager.getCurrentItem());
                boolean isCurrentPage = position == viewPager.getCurrentItem();
                if (showRecentlyUsed) {
                    if (positionOffset <= 1 && positionOffset >= 0 && supportChange) {
                        if (viewPager.getCurrentItem() == 0 && isCurrentPage) {
//                            smileyViewHolder.imageView.setAlpha(alpha);
//                            smileyViewHolder.delView.setAlpha(1 - alpha);
                            updateSmileyList(positionOffset);
                        } else if (viewPager.getCurrentItem() == 1 && !isCurrentPage) {
                            updateSmileyList(positionOffset);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("111111111111", "onPageSelected======position:" + position);
//                Log.i("1111111111", "444444444onPageSelected=============selectposition" + position);
                mCurrentPage = position;

                List<ConversationSmiley> conversationSmilies = emojis.get(0);
                ConversationSmiley conversationSmiley = new ConversationSmiley();
                if (mCurrentPage == 0) {
                    conversationSmiley.setImageID(R.drawable.mx_del_btn);
                    conversationSmiley.setKey("del_btn");
                    conversationSmiley.setText("黄河");
                    conversationSmilies.set(16, conversationSmiley);
                    RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
                    recyclerSmileyAdapter.notifyItemChanged(16);
                } else {
                    conversationSmiley.setKey("/:em_17:/");
                    conversationSmiley.setText("/:em_17:/");
                    conversationSmiley.setResName("smiley_17");
                    conversationSmiley.setReplaceKey("/:em_17:/");
                    conversationSmiley.setImageID(R.drawable.em_17);
                    conversationSmilies.set(16, conversationSmiley);
                    RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
                    recyclerSmileyAdapter.notifyItemChanged(16);
                }

                draw_Point(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    // 空闲状态
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.i("111111111111", "onPageScrollStateChanged  空闲状态");

//                        List<ConversationSmiley> conversationSmilies = emojis.get(0);
//                        ConversationSmiley conversationSmiley = new ConversationSmiley();
//                        if (mCurrentPage == 0) {
//                            conversationSmiley.setImageID(R.drawable.mx_del_btn);
//                            conversationSmiley.setKey("del_btn");
//                            conversationSmiley.setText("黄河");
//                            conversationSmilies.set(16, conversationSmiley);
//                            RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
//                            recyclerSmileyAdapter.notifyItemChanged(16, 0.0f);
//                        } else {
//                            conversationSmiley.setKey("/:em_17:/");
//                            conversationSmiley.setText("/:em_17:/");
//                            conversationSmiley.setResName("smiley_17");
//                            conversationSmiley.setReplaceKey("/:em_17:/");
//                            conversationSmiley.setImageID(R.drawable.em_17);
//                            conversationSmilies.set(16, conversationSmiley);
//                            RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
//                            recyclerSmileyAdapter.notifyItemChanged(16, 1.0f);
//                        }
                        break;
                    // 滑动后自然沉降的状态
                    case ViewPager.SCROLL_STATE_SETTLING:
                        supportChange = false;
                        Log.i("111111111111", "onPageScrollStateChanged  滑动后自然沉降的状态");
                        break;
                    // 滑动状态
                    case ViewPager.SCROLL_STATE_DRAGGING:

                        supportChange = true;
                        Log.i("111111111111", "onPageScrollStateChanged  滑动状态");
                        break;
                }
            }
        });
    }


    private boolean supportChange = true;

    private void updateSmileyList(float positionOffset) {
        List<ConversationSmiley> conversationSmilies = emojis.get(0);
        ConversationSmiley conversationSmiley = new ConversationSmiley();
        if (viewPager.getCurrentItem() == 0) {
            conversationSmiley.setImageID(R.drawable.mx_del_btn);
            conversationSmiley.setKey("del_btn");
            conversationSmiley.setText("黄河");
            conversationSmilies.set(16, conversationSmiley);
            RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
            recyclerSmileyAdapter.notifyItemChanged(16, 1 - positionOffset);
        } else if (viewPager.getCurrentItem() == 1) {
            conversationSmiley.setKey("/:em_17:/");
            conversationSmiley.setText("/:em_17:/");
            conversationSmiley.setResName("smiley_17");
            conversationSmiley.setReplaceKey("/:em_17:/");
            conversationSmiley.setImageID(R.drawable.em_17);
            conversationSmilies.set(16, conversationSmiley);
            RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
            recyclerSmileyAdapter.notifyItemChanged(16, positionOffset);
        }
    }


    private void draw_Point(int index) {
        for (int i = 0; i < pointViews.size(); i++) {
            if (index == i) {
                if (showRecentlyUsed && i == 0) {
                    pointViews.get(i).setBackgroundResource(R.drawable.recently_used_icon_black);
                } else {
                    pointViews.get(i).setBackgroundResource(R.drawable.mx_d2);
                }
            } else {
                pointViews.get(i).setBackgroundResource(R.drawable.mx_d1);
            }
        }
    }

    private synchronized void saveCommonUseSmiley(Context context, List<ConversationSmiley> smileyList) {
        final StringBuilder sb = new StringBuilder();
        for (final ConversationSmiley item : smileyList) {
            if (!TextUtils.isEmpty(item.getResName()) && !"null".equals(item.getResName())) {
                sb.append(item.getResName()).append(",");
            }
        }

        SharedPreferenceUtil.setParam(this, COMMON_USE_SMILEY_KEY, sb.toString());
    }

    private void initView() {
        EditText textContent = findViewById(R.id.edittext_smiley);
        ImageButton smileButton = findViewById(R.id.button_smiley);
        smileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = NetWorkManagementUtils.getNetworkState(DemoUI.this);
                Log.i("111111111111","============"+i);


//                List<ConversationSmiley> conversationSmilies = emojis.get(0);
//
//                ConversationSmiley conversationSmiley = new ConversationSmiley();
//                conversationSmiley.setImageID(R.drawable.mx_del_btn);
//                conversationSmiley.setText("黄河");
//                conversationSmilies.set(16, conversationSmiley);
//                if (mCurrentPage == 0) {
//                    RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
//                    recyclerSmileyAdapter.notifyItemChanged(16);
//                } else if (mCurrentPage == 1) {
//                    RecyclerSmileyAdapter recyclerSmileyAdapter = smileyAdapters.get(1);
//                    recyclerSmileyAdapter.notifyItemChanged(16);
//                }
            }
        });

        viewPager = findViewById(R.id.viewpager);

        smiley_panel_dot = findViewById(R.id.smiley_panel_dot);
    }

    private void initDemo() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        Log.i("1111111111111111", "=============-----widthPixels："
                + widthPixels + "--------················heightPixels：" + heightPixels);
    }

    /*
     * 功能：把inputStream转化为字符串
     */
    public static String readInputStream(InputStream is){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len=0;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer)) != -1){
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] result = baos.toByteArray();
            return new String(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
