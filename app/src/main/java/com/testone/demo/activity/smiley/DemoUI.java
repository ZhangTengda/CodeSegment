package com.testone.demo.activity.smiley;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.testone.demo.adapter.SmileyAdapter;
import com.testone.demo.utils.ScreenUtil;
import com.testone.demo.utils.SharedPreferenceUtil;
import com.testone.demo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoUI extends BaseActivity implements View.OnClickListener {

    public static final String COMMON_USE_SMILEY_KEY = "common_use_smiley_key";
    private List<ConversationSmiley> commonlyUseSmily;
    private List<ConversationSmiley> commonlyUseSmilyRun;

    private List<ConversationSmileyAdapter> smileyAdapters = new ArrayList<>();

    private List<ImageView> pointViews = new ArrayList<>();

    private List<View> pageViews = new ArrayList<>();
    private boolean showRecentlyUsed;

    private int currentSmileyPage = 0;
    private EditText textContent;
    private ImageButton smileButton;
    private boolean smileButtonState;
    private int smileFlag;
    private ViewPager viewPager;
    private LinearLayout smiley_panel_dot;
    private LinearLayout linearLayout;
    private int mCurrentPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initView();

        initSmilely();

        initData();

        initDemo();
    }

    private void initSmilely() {
        commonlyUseSmilyRun = new ArrayList<>();
        commonlyUseSmily = new ArrayList<>();

        SharedPreferenceUtil spu = new SharedPreferenceUtil();


        final ConversationSmileyConversionUtil util = ConversationSmileyConversionUtil.getInstace(this);

        final String value = (String) SharedPreferenceUtil.getParam(this, COMMON_USE_SMILEY_KEY, "");
        if (!TextUtils.isEmpty(value)) {
            final Map<String, ConversationSmiley> emojiMap = util.emojiMap;
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
                commonlyUseSmily.add(temp.get(resName));
            }
        }
        commonlyUseSmilyRun.addAll(commonlyUseSmily);
    }

    private void initData() {

        List<List<ConversationSmiley>> emojis = ConversationSmileyConversionUtil.getInstace(this).emojiLists;
        if (commonlyUseSmily != null && commonlyUseSmily.size() > 0) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.nine_smiley_layout, null);
            GridView commonly_use = linearLayout.findViewById(R.id.commonly_use);
            ConversationSmileyAdapter adapterCommonly_use = new ConversationSmileyAdapter(this, commonlyUseSmily);
            commonly_use.setAdapter(adapterCommonly_use);
            commonly_use.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    smileClickUse(position, commonlyUseSmily);
                }
            });
            smileyAdapters.add(adapterCommonly_use);
            pageViews.add(linearLayout);
            showRecentlyUsed = true;
        }

        for (int i = 0; i < emojis.size(); i++) {
            linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.twenty_smiley_layout, null);
//            linearLayout.setTag(i);
            List<ConversationSmiley> conversationSmilies = emojis.get(i);
            for (int j = 1; j < conversationSmilies.size() + 1; j++) {
                ConversationSmiley conversationSmiley = conversationSmilies.get(j - 1);
                switch (j) {
                    case 1:
                        ImageView smiley1 = linearLayout.findViewById(R.id.art_emoji_icon_iv1);
                        smiley1.setImageResource(conversationSmiley.getImageID());
                        smiley1.setTag(j);
                        smiley1.setOnClickListener(this);
                        break;
                    case 2:
                        ImageView smiley2 = linearLayout.findViewById(R.id.art_emoji_icon_iv2);
                        smiley2.setImageResource(conversationSmiley.getImageID());
                        smiley2.setTag(j);
                        smiley2.setOnClickListener(this);
                        break;
                    case 3:
                        ImageView smiley3 = linearLayout.findViewById(R.id.art_emoji_icon_iv3);
                        smiley3.setImageResource(conversationSmiley.getImageID());
                        smiley3.setOnClickListener(this);
                        smiley3.setTag(j);
                        break;
                    case 4:
                        ImageView smiley4 = linearLayout.findViewById(R.id.art_emoji_icon_iv4);
                        smiley4.setImageResource(conversationSmiley.getImageID());
                        smiley4.setOnClickListener(this);
                        smiley4.setTag(j);
                        break;
                    case 5:
                        ImageView smiley5 = linearLayout.findViewById(R.id.art_emoji_icon_iv5);
                        smiley5.setImageResource(conversationSmiley.getImageID());
                        smiley5.setOnClickListener(this);
                        smiley5.setTag(j);
                        break;
                    case 6:
                        ImageView smiley6 = linearLayout.findViewById(R.id.art_emoji_icon_iv6);
                        smiley6.setImageResource(conversationSmiley.getImageID());
                        smiley6.setOnClickListener(this);
                        smiley6.setTag(j);
                        break;
                    case 7:
                        ImageView smiley7 = linearLayout.findViewById(R.id.art_emoji_icon_iv7);
                        smiley7.setImageResource(conversationSmiley.getImageID());
                        smiley7.setOnClickListener(this);
                        smiley7.setTag(j);
                        break;
                    case 8:
                        ImageView smiley8 = linearLayout.findViewById(R.id.art_emoji_icon_iv8);
                        smiley8.setImageResource(conversationSmiley.getImageID());
                        smiley8.setOnClickListener(this);
                        smiley8.setTag(j);
                        break;
                    case 9:
                        ImageView smiley9 = linearLayout.findViewById(R.id.art_emoji_icon_iv9);
                        smiley9.setImageResource(conversationSmiley.getImageID());
                        smiley9.setOnClickListener(this);
                        smiley9.setTag(j);
                        break;
                    case 10:
                        ImageView smiley10 = linearLayout.findViewById(R.id.art_emoji_icon_iv10);
                        smiley10.setImageResource(conversationSmiley.getImageID());
                        smiley10.setOnClickListener(this);
                        smiley10.setTag(j);
                        break;
                    case 11:
                        ImageView smiley11 = linearLayout.findViewById(R.id.art_emoji_icon_iv11);
                        smiley11.setImageResource(conversationSmiley.getImageID());
                        smiley11.setOnClickListener(this);
                        smiley11.setTag(j);
                        break;
                    case 12:
                        ImageView smiley12 = linearLayout.findViewById(R.id.art_emoji_icon_iv12);
                        smiley12.setImageResource(conversationSmiley.getImageID());
                        smiley12.setOnClickListener(this);
                        smiley12.setTag(j);
                        break;
                    case 13:
                        ImageView smiley13 = linearLayout.findViewById(R.id.art_emoji_icon_iv13);
                        smiley13.setImageResource(conversationSmiley.getImageID());
                        smiley13.setOnClickListener(this);
                        smiley13.setTag(j);
                        break;
                    case 14:
                        ImageView smiley14 = linearLayout.findViewById(R.id.art_emoji_icon_iv14);
                        smiley14.setImageResource(conversationSmiley.getImageID());
                        smiley14.setOnClickListener(this);
                        smiley14.setTag(j);
                        break;
                    case 15:
                        ImageView smiley15 = linearLayout.findViewById(R.id.art_emoji_icon_iv15);
                        smiley15.setImageResource(conversationSmiley.getImageID());
                        smiley15.setOnClickListener(this);
                        break;
                    case 16:
                        ImageView smiley16 = linearLayout.findViewById(R.id.art_emoji_icon_iv16);
                        smiley16.setImageResource(conversationSmiley.getImageID());
                        smiley16.setOnClickListener(this);
                        break;
                    case 17:
                        ImageView smiley17 = linearLayout.findViewById(R.id.art_emoji_icon_iv17);
                        smiley17.setImageResource(conversationSmiley.getImageID());
                        smiley17.setOnClickListener(this);
                        break;
                    case 18:
                        ImageView smiley18 = linearLayout.findViewById(R.id.art_emoji_icon_iv18);
                        smiley18.setImageResource(conversationSmiley.getImageID());
                        smiley18.setOnClickListener(this);
                        break;
                    case 19:
                        ImageView smiley19 = linearLayout.findViewById(R.id.art_emoji_icon_iv19);
                        smiley19.setImageResource(conversationSmiley.getImageID());
                        smiley19.setOnClickListener(this);
                        break;
                    case 20:
                        ImageView smiley20 = linearLayout.findViewById(R.id.art_emoji_icon_iv20);
                        smiley20.setImageResource(conversationSmiley.getImageID());
                        smiley20.setOnClickListener(this);
                        break;
                    case 21:
                        ImageView smiley21 = linearLayout.findViewById(R.id.art_emoji_icon_iv21);
                        smiley21.setImageResource(R.drawable.mx_del_btn);
                        smiley21.setOnClickListener(this);
                        break;
                }
            }
            pageViews.add(linearLayout);
        }


//        for (int i = 0; i < emojis.size(); i++) {
//            GridView view = new GridView(this);
//            ConversationSmileyAdapter adapter = new ConversationSmileyAdapter(this, emojis.get(i));
//            if (showRecentlyUsed && i == 0) {
//                List<ConversationSmiley> conversationSmilies = emojis.get(i);
//                ConversationSmiley conversationSmiley = conversationSmilies.get(16);
//                conversationSmiley.setImageID(R.drawable.mx_del_btn);
//                conversationSmiley.setKey("del_btn");
//                adapter = new ConversationSmileyAdapter(this, conversationSmilies);
//            }
//            view.setAdapter(adapter);
//            smileyAdapters.add(adapter);
//            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    smileClick(arg2);
//                }
//            });
//
//            view.setNumColumns(7);
//            view.setBackgroundColor(Color.TRANSPARENT);
//            view.setHorizontalSpacing(1);
//            view.setVerticalSpacing(1);
//            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//            view.setCacheColorHint(0);
//            view.setPadding(5, 50, 5, 0);
//            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
//            view.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
//            view.setGravity(Gravity.CENTER);
//            pageViews.add(view);
//        }

        for (int i = 0; i < pageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.mx_d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ScreenUtil.dp2px(this, 6), ScreenUtil.dp2px(this, 6));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;

            smiley_panel_dot.addView(imageView, layoutParams);
//            if (i == 0 || i == pageViews.size() - 1) {
//                imageView.setVisibility(View.GONE);
//            }
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (showRecentlyUsed) {
                    LinearLayout mLinearLayout = (LinearLayout) pageViews.get(1);
                    ImageView imageView = mLinearLayout.findViewById(R.id.art_emoji_icon_iv17);
                    ImageView delImageView = mLinearLayout.findViewById(R.id.art_emoji_icon_iv_del);
                    imageView.setImageAlpha((int)(255 * positionOffset));
                    delImageView.setImageAlpha(255 - (int)(255 * positionOffset));
                    Log.i("testtag",mCurrentPage+"page||==positionOffset=="+positionOffset+"||==tag=="+(int)(255 * positionOffset));
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                draw_Point(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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


    private void smileClick(int arg2) {
        ToastUtil.shortToast(this, "nihao a " + arg2);

        ConversationSmiley smiley;
        if (showRecentlyUsed) {
            if (currentSmileyPage == 0) {
                smiley = (ConversationSmiley) smileyAdapters.get(1).getItem(arg2);
            } else {
                smiley = (ConversationSmiley) smileyAdapters.get(currentSmileyPage).getItem(arg2);
            }
            // 当滑动到最近使用表情时，右下角表情是删除
            if (currentSmileyPage == 0 && arg2 == 16) {
                smiley = new ConversationSmiley();
                smiley.setImageID(R.drawable.mx_del_btn);
                smiley.setKey("del_btn");
            }
        } else {
            smiley = (ConversationSmiley) smileyAdapters.get(currentSmileyPage).getItem(arg2);
        }
        if (TextUtils.isEmpty(smiley.getKey()) || smiley.getImageID() == 0) {
            return;
        }
        int selection = textContent.getSelectionStart();
        if (smiley.getImageID() == R.drawable.mx_del_btn) {
            String text = textContent.getText().toString();
            if (selection > 0) {
                int start = ConversationSmileyConversionUtil.getInstace(this).deleteLastSmiley(text);
                if (start != -1) {
                    textContent.getText().delete(start, selection);
                    smileButtonState = true;
                    // 设置蓝色笑脸
                    smileButton.setImageResource(R.drawable.mx_input_smile_blue);
                    smileFlag = 0;
                    return;
                }
                textContent.getText().delete(selection - 1, selection);
            }
        } else {
            if (!TextUtils.isEmpty(smiley.getKey())) {
                SpannableString spannableString = ConversationSmileyConversionUtil
                        .getInstace(this)
                        .addFace(this, smiley.getImageID(), smiley.getKey());
                textContent.getText().insert(selection, spannableString);
                smileButtonState = true;
                // 设置蓝色笑脸
                smileButton.setImageResource(R.drawable.mx_input_smile_blue);
                smileFlag = 0;
            }
            boolean isContains = false;
            int index = 0;
            for (int i = 0; i < commonlyUseSmilyRun.size(); i++) {
                if (TextUtils.equals(smiley.getKey(), commonlyUseSmilyRun.get(i).getKey())) {
                    index = i;
                    isContains = true;
                    break;
                }
            }
            if (isContains) {
                if (!TextUtils.isEmpty(smiley.getKey())) {
                    commonlyUseSmilyRun.remove(index);
                    commonlyUseSmilyRun.add(0, smiley);
                }
            } else {
                if (!TextUtils.isEmpty(smiley.getKey())) {
                    commonlyUseSmilyRun.add(0, smiley);
                }
            }
            if (commonlyUseSmilyRun.size() > 8) {
                commonlyUseSmilyRun = commonlyUseSmilyRun.subList(0, 9);
            }
            saveCommonUseSmiley(this, commonlyUseSmilyRun);
        }
    }

    private synchronized void saveCommonUseSmiley(Context context, List<ConversationSmiley> smileyList) {
//        if (adapterCommonly_use != null) {
//            adapterCommonly_use.notifyDataSetChanged();
//        }
        final StringBuilder sb = new StringBuilder();
        for (final ConversationSmiley item : smileyList) {
            if (!TextUtils.isEmpty(item.getResName()) && !"null".equals(item.getResName())) {
                sb.append(item.getResName()).append(",");
            }
        }

        SharedPreferenceUtil.setParam(this, COMMON_USE_SMILEY_KEY, sb.toString());
    }

    private void smileClickUse(int position, List<ConversationSmiley> commonlyUseSmily) {

    }

    private void initView() {
        textContent = findViewById(R.id.edittext_smiley);
        smileButton = findViewById(R.id.button_smiley);

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        handleConfigChanged();
    }

    private void handleConfigChanged() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        Log.i("11111111111111112", "=============-----widthPixels："
                + widthPixels + "--------················heightPixels：" + heightPixels);
    }


    @Override
    public void onClick(View v) {

        int tag = (int) v.getTag();
        ToastUtil.shortToast(this, "nihaoa "+ tag);

        switch (v.getId()) {
            case R.id.art_emoji_icon_iv1:
                break;
            case R.id.art_emoji_icon_iv2:
                break;
            case R.id.art_emoji_icon_iv3:
                break;
            case R.id.art_emoji_icon_iv4:
                break;
            case R.id.art_emoji_icon_iv5:
                break;
            case R.id.art_emoji_icon_iv6:
                break;
            case R.id.art_emoji_icon_iv7:
                break;
            case R.id.art_emoji_icon_iv8:
                break;
            case R.id.art_emoji_icon_iv9:
                break;
            case R.id.art_emoji_icon_iv10:
                break;
            case R.id.art_emoji_icon_iv11:
                break;
            case R.id.art_emoji_icon_iv12:
                break;
            case R.id.art_emoji_icon_iv13:
                break;
            case R.id.art_emoji_icon_iv14:
                break;
            case R.id.art_emoji_icon_iv15:
                break;
            case R.id.art_emoji_icon_iv16:
                break;
            case R.id.art_emoji_icon_iv17:
                break;
            case R.id.art_emoji_icon_iv18:
                break;
            case R.id.art_emoji_icon_iv19:
                break;
            case R.id.art_emoji_icon_iv20:
                break;
            case R.id.art_emoji_icon_iv21:
                break;
        }
    }
}
