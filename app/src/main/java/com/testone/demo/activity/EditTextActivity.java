package com.testone.demo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.testone.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 */
public class EditTextActivity extends Activity {

    private EditText editText;
    private String testString;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);

        textView = findViewById(R.id.spannable);
        testString = "测试部门1/测试部门1sub1/测试部门1sub1sub1";

        TextView viewById = findViewById(R.id.test_tv);
        viewById.setText(testString);

        TextView searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                textView.setText(makeKeywordHighlightForContact(testString, s));
            }
        });

        editText = findViewById(R.id.edittext_01);

//        String s = "测试部门门测";
//        textView.setText(makeKeywordHighlight(this, s, "测试"));
//        textView.setText(matcherSearchContent( s, "测门"));


//        String str = "你好，安卓-》Hello,Android!";
//        final ForegroundColorSpan fSpan = new ForegroundColorSpan(Color.parseColor("#ff5656"));
//        SpannableStringBuilder builder = new SpannableStringBuilder(str);
//        ForegroundColorSpan[] colorSpan = {new ForegroundColorSpan(Color.RED),
//                new ForegroundColorSpan(Color.GREEN), new ForegroundColorSpan(Color.BLUE),
//                new ForegroundColorSpan(Color.WHITE), new ForegroundColorSpan(Color.BLACK)};
//        int length = "你好，安卓-》Hello,World".length();
//        int rand;
//        for (int i = 1; i < length; i++) {
//            rand = (int) (Math.random() * 5);
//            while (rand == 5) {
//                rand = (int) (Math.random() * 5);
//            }
//            builder.setSpan(CharacterStyle.wrap(fSpan), i - 1, i, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
////            builder.setSpan(colorSpan[rand], i - 1, i, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        }
//        textView.setText(builder);
    }


//    public CharSequence makeKeywordHighlight(CharSequence src, String kw) {
//        String[] split = kw.split("");
//        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(kw) || !src.toString().contains(kw)) {
//            return src;
//        }
//        final SpannableStringBuilder ssb;
//        if (src instanceof SpannableStringBuilder) {
//            ssb = (SpannableStringBuilder) src;
//        } else {
//            ssb = (SpannableStringBuilder) Html.fromHtml(src.toString());
//            if (ssb == null) {
//                return src;
//            }
//        }
//        final Matcher matcher = Pattern.compile(kw).matcher(ssb);
////        final int color = ThemeUtils.getThemeGroupColorInteger(context, ThemeGroup.THEME_GROUP);
//        final int color = R.color.red;
//        while (matcher.find()) {
//            String key = matcher.group();
//            final int start = matcher.start();
//            final int end = start + key.length();
//            final ForegroundColorSpan fSpan = new ForegroundColorSpan(color);
//            ssb.setSpan(fSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        return ssb;
//    }


    public static SpannableStringBuilder matcherSearchContent(String text, String keyword) {
        String[] split = keyword.split("");
        int index = 0;
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span;
        for (String aSplit : split) {
//            String wordReg = "(?i)" + aSplit;   //忽略字母大小写
            Pattern pattern = Pattern.compile(aSplit);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                if (matcher.start() != matcher.end()) {
                    int i = text.indexOf(aSplit, index);
                    index = i;
                    Log.i("1111111111111", "位置i=" + i);
                    span = new ForegroundColorSpan(Color.parseColor("#ff5656"));
                    spannable.setSpan(CharacterStyle.wrap(span), i, i + 1, Spannable.SPAN_MARK_MARK);
                }
            }
        }
        return spannable;
    }

    /**
     * 通讯录搜索部门专用高亮函数
     *
     * @param src
     * @param keyword
     * @return
     */
    public static CharSequence makeKeywordHighlightForContact(CharSequence src, String keyword) {
        ForegroundColorSpan fSpan = new ForegroundColorSpan(Color.parseColor("#ff5656"));
        char[] splitArray = keyword.toCharArray();
        String[] deptArray = src.toString().split("/");
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(keyword)) {
            return src;
        }
        final SpannableStringBuilder ssb;
        if (src instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) src;
        } else {
            ssb = (SpannableStringBuilder) Html.fromHtml(src.toString());
            if (ssb == null) {
                return src;
            }
        }
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < deptArray.length; i++) {
            String deptString = deptArray[i];
            int baseLocation = src.toString().indexOf(deptString);
            int matchIndex = 0;
            for (int j = 0; j < splitArray.length; j++) {
                if (deptArray[i].contains(String.valueOf(splitArray[j]))) {
                    int currentLocation = deptArray[i].indexOf(splitArray[j], matchIndex);
                    if (currentLocation < 0) {
                        indexList.clear();
                        continue;
                    }
                    matchIndex = currentLocation;
                    indexList.add(currentLocation + baseLocation);
                } else {
                    indexList.clear();
                    continue;
                }
            }
            if (indexList.size() >= splitArray.length) {
                break;
            } else {
                indexList.clear();
            }

        }
        for (Integer integer : indexList) {
            ssb.setSpan(CharacterStyle.wrap(fSpan), integer, integer + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ssb;
    }


//    public static SpannableStringBuilder matcherSearchContent(String text, String keyword) {
//
//        String[] split = keyword.split("");
//
//        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
//        String originString = text;
//        StringBuffer stringBuffer = new StringBuffer();
//        SparseArray sparseArray = new SparseArray();
//        for (String aSplit : split) {
//            Pattern pattern = Pattern.compile(aSplit);
//            Matcher matcher = pattern.matcher(text);
//            while (matcher.find()) {
//                String substring = originString.substring(matcher.start(), matcher.end());
//                if (matcher.start() != matcher.end()) {
//                    sparseArray.append(matcher.start(), substring);
//                }
//            }
//        }
//        for (int i = 0; i < sparseArray.size(); i++) {
//            String sss = (String) sparseArray.valueAt(i);
//            stringBuffer.append(sss);
//        }
//        Pattern pattern = Pattern.compile(keyword);
//        Matcher matcher = pattern.matcher(stringBuffer);
//        final ForegroundColorSpan fSpan = new ForegroundColorSpan(Color.parseColor("#ff5656"));
//        while (matcher.find()) {
//            final int start = matcher.start();
//            final int end = matcher.end();
//            for (int i = 0; i < end - start; i++) {
//                int i1 = sparseArray.keyAt(start + i);
//                Log.i("111111111","i1==========="+i1+"=========start + i="+(start + i));
////                spannable.setSpan(fSpan, i1, i1+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannable.setSpan(CharacterStyle.wrap(fSpan), i1, i1+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        Log.i("111111111111111","eeeeeee New String is "+stringBuffer+"===array"+sparseArray);
//        return spannable;
//    }


    public static SpannableString getSpannableString(String text) {
        SpannableString msp;
        if (TextUtils.isEmpty(text)) {
            return null;
        }

        /*
         //创建一个 SpannableString对象
        msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合");
        //设置字体(default,default-bold,monospace,serif,sans-serif)
        msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小（绝对值,单位：像素）
        msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(20,true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //2.0f表示默认字体大小的两倍
        //设置字体前景色
        msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
        //设置字体背景色
        msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色
        //设置字体样式正常，粗体，斜体，粗斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //正常
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体
        //设置下划线
        msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置删除线
        msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置上下标
        msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //下标
        msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标
        //超级链接（需要添加setMovementMethod方法附加响应）
        msp.setSpan(new URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //电话
        msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //邮件
        msp.setSpan(new URLSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //网络
        msp.setSpan(new URLSpan("sms:4155551212"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //短信   使用sms:或者smsto:
        msp.setSpan(new URLSpan("mms:4155551212"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //彩信   使用mms:或者mmsto:
        msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //地图
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
        msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变  */


        String tempText = new String(text);
        StringBuilder sb = new StringBuilder("");

        List<Integer> indexList = new ArrayList<>();  //保存去掉*之后的需要加粗的字符串索引index
        int prePosition = 0;
        while (true) {
            if (!TextUtils.isEmpty(tempText)) {
                int index = tempText.indexOf('*');
                if (index >= 0) {
                    String appendedStr = tempText.substring(0, index);
                    if (appendedStr.length() > 0) {
                        if (indexList.size() > 0) {
                            prePosition = indexList.get(indexList.size() - 1);
                        }
                        indexList.add(index + prePosition);
                        sb.append(appendedStr);
                    } else {
                        if (indexList.size() == 0) {
                            indexList.add(0);
                        }
                    }
                    if (index + 1 > tempText.length()) {
                        break;
                    } else {
                        tempText = tempText.substring(index + 1);
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //设置加粗内容
        if (indexList.size() > 0) {
            msp = new SpannableString(sb);
            for (int i = 0; i < indexList.size() - 1; i += 2) {
                msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexList.get(i), indexList.get(i + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
            }
        } else {
            msp = new SpannableString(text);
            msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return msp;

    }
}
