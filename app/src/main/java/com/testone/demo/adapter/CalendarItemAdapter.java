package com.testone.demo.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testone.calendar.CaledarAdapter;
import com.testone.calendar.CalendarBean;
import com.testone.demo.R;

public class CalendarItemAdapter implements CaledarAdapter {
    private TextView view;
    private TextView chinaText;
    @Override
    public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
            convertView.setLayoutParams(params);
        }

        view = convertView.findViewById(R.id.text_day);
        chinaText = convertView.findViewById(R.id.chinaText);

        view.setText("" + bean.day);
        if (bean.mothFlag != 0) {
            view.setTextColor(0xff9299a1);
        } else {
            view.setTextColor(0xffffffff);
        }
        chinaText.setText(bean.chinaDay);

        return convertView;
    }

    public static int px(float dipValue) {
        Resources r = Resources.getSystem();
        final float scale = r.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
