package com.testone.demo.activity.smiley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.testone.demo.R;

import java.util.List;

public class ConversationSmileyAdapter extends BaseAdapter {

	private List<ConversationSmiley> data;
	private LayoutInflater inflater;

	public ConversationSmileyAdapter(Context context,
                                     List<ConversationSmiley> list) {
		this.inflater = LayoutInflater.from(context);
		this.data = list;
	}

	public void changeData(List<ConversationSmiley> list){
		this.data = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ConversationSmiley smiley = data.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.mx_smiley_grid_item_s, null);
			holder.art_emoji_icon_iv = (ImageView) convertView.findViewById(R.id.art_emoji_icon_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(smiley.getKey() != null && !"".equals(smiley.getKey())){
			holder.art_emoji_icon_iv.setTag(smiley);
			holder.art_emoji_icon_iv.setImageResource(smiley.getImageID());
		}
		return convertView;
	}

	class ViewHolder {
		public ImageView art_emoji_icon_iv;
	}
}