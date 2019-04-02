package com.testone.demo.activity.calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.testone.calendar.CalendarBean;
import com.testone.calendar.CalendarDateView;
import com.testone.calendar.utils.CalendarUtil;
import com.testone.calendar.CalendarView;
import com.testone.calendar.utils.SolarTermsUtil;
import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.adapter.CalendarAdapter;
import com.testone.demo.adapter.CalendarItemAdapter;
import com.testone.demo.bean.SolarTermsBean;
import com.testone.demo.utils.CreateSolarTermsMsgUtil;
import com.testone.demo.utils.SharedPreferenceUtil;
import com.testone.demo.utils.ToastUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日历页面
 */
public class CalendarActivity extends BaseActivity {

    private String jieqiSong ="打春阳气转 雨水沿河边 惊蛰乌鸦叫 春分地皮干 清明忙种麦 谷雨种大田" +
            "哎唻哎嗨哎嗨哟 春呀吗春天" +
            "立夏鹅毛住 小满雀来全 芒种开了铲 夏至不拿棉 小暑不算热 大暑三伏天" +
            "哎唻哎嗨哎嗨哟 夏呀吗夏天" +
            "立秋忙打靛 处暑动刀镰 白露割蜜薯 秋分不生田 寒露不算冷 霜降变了天" +
            "哎唻哎嗨哎嗨哟 秋呀吗秋天" +
            "立冬交十月 小雪地封严 大雪河汊牢 冬至不行船 小寒大寒冰如铁 迎来又一年";
    /**
     * 日历页的listview
     */
    private RecyclerView recyclerView;
    /**
     * 日历页title
     **/
    private TextView mTitle;
    private TextView showChinaText;
    private TextView chinaText;
    private List<String> messageList = new ArrayList<>();
    private CalendarItemAdapter calendarItemAdapter;
    private CalendarAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        SQLiteDatabase database = LitePal.getDatabase();

        boolean solartermsbean = tabIsExist(database, "solartermsbean");
        ToastUtil.longToast(this, "表格已存在" +solartermsbean);

        CreateSolarTermsMsgUtil solarTermsMsgUtil = new CreateSolarTermsMsgUtil();
        if (!solartermsbean) {
            solarTermsMsgUtil.createSolarTermsMsg();
        }

        CalendarDateView mCalendarDateView = findViewById(R.id.calendarDateView);

        mTitle = findViewById(R.id.title);

        recyclerView = findViewById(R.id.calendar_recyclerview);

        calendarItemAdapter = new CalendarItemAdapter();

        mCalendarDateView.setAdapter(calendarItemAdapter);

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                messageList.clear();
                if (!TextUtils.isEmpty(bean.chinaDay)) {
                    List<SolarTermsBean> solarTermsBeans = LitePal.where("solarTerms = ?", bean.chinaDay).find(SolarTermsBean.class);
                    if (solarTermsBeans != null && solarTermsBeans.size() > 0) {
                        SolarTermsBean solarTermsBean = solarTermsBeans.get(0);
                        messageList.add(solarTermsBean.getSolarTermsDes());
                    }
                    adapter.notifyDataSetChanged();
                }
                mTitle.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());

        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);
        adapter = new CalendarAdapter(this, messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        SolarTermsUtil solarTermsUtil = new SolarTermsUtil(Calendar.getInstance());
        String solarTerms = solarTermsUtil.getSolartermsName();
        if (!TextUtils.isEmpty(solarTerms)) {
            List<SolarTermsBean> solarTermsBeans = LitePal.where("solarTerms = ?", solarTerms).find(SolarTermsBean.class);
            if (solarTermsBeans != null && solarTermsBeans.size() > 0) {
                SolarTermsBean solarTermsBean = solarTermsBeans.get(0);
                messageList.add(solarTermsBean.getSolarTermsDes());
            }
            adapter.notifyDataSetChanged();
        }
    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    public void back(View view) {
        finish();
    }

    /**
     *
     * @param tabName
     * @return
     */
    public boolean tabIsExist(SQLiteDatabase database, String tabName){
        boolean result = false;
        if(tabName == null){
            return false;
        }
        Cursor cursor = null;
        try {

            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";
            cursor = database.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

}
