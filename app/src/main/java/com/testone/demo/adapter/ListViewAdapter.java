package com.testone.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.activity.listview.ListViewActivity;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<ListViewActivity.Person> list;
    private Context context;

    public ListViewAdapter(Context context, List<ListViewActivity.Person> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0: list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            viewHolder.name = convertView.findViewById(R.id.listview_name);
            viewHolder.age = convertView.findViewById(R.id.listview_age);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListViewActivity.Person person = list.get(position);
        viewHolder.name.setText(person.getName());
        viewHolder.age.setText(person.getAge()+"");
        return convertView;
    }

    public class ViewHolder {
        TextView name;
        TextView age;
    }
}
