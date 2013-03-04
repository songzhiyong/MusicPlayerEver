package com.example.imusicplayer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imusicplayer.R;
import com.example.imusicplayer.entity.GridItemInfo;
import com.example.imusicplayer.utils.GlobalUtils;

public class GridViewAdapter extends BaseAdapter {
	private ArrayList<GridItemInfo> infos;
	private LayoutInflater inflater;
	private Context mContext;

	public void setInfos(ArrayList<GridItemInfo> infos) {
		if (infos != null)
			this.infos = infos;
		else
			this.infos = new ArrayList<GridItemInfo>();
	}

	public GridViewAdapter(Context context, ArrayList<GridItemInfo> infos) {
		this.setInfos(infos);
		this.inflater = LayoutInflater.from(context);
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.grid_item, null);
		ImageView ivIcon = (ImageView) convertView
				.findViewById(R.id.ivGridViewIcon);
		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.tvGridViewTitle);
		TextView tvDesc = (TextView) convertView
				.findViewById(R.id.tvGridViewDesc);
		GridItemInfo info = infos.get(position);
		ivIcon.setImageResource(info.resId);
		tvTitle.setText(info.title);
		tvDesc.setText(info.desc);
		convertView.setTag(info.resId);
		final Intent intent = new Intent();
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int resId = (Integer) v.getTag();
				switch (resId) {
				case R.drawable.list_icon_media:
					intent.setAction(GlobalUtils.ACTION_ALL_MUSICS);
					break;
				case R.drawable.list_icon_favourite:
					intent.setAction(GlobalUtils.ACTION_FAV_MUSICS);
					break;
				case R.drawable.list_icon_recent:
					intent.setAction(GlobalUtils.ACTION_RENCENTPLAY_MUSICS);
					break;
				case R.drawable.list_icon_artist:
					Toast.makeText(mContext, "歌手", Toast.LENGTH_SHORT).show();
					break;
				case R.drawable.list_icon_album:
					Toast.makeText(mContext, "专辑", Toast.LENGTH_SHORT).show();
					
					break;
				case R.drawable.list_icon_folder:
					Toast.makeText(mContext, "文件夹", Toast.LENGTH_SHORT).show();
					
					break;
				
				case R.drawable.list_icon_new_list:
					Toast.makeText(mContext, "新建列表", Toast.LENGTH_SHORT).show();
					
					break;
				default:
					break;
			}
				mContext.sendBroadcast(intent);
			}
		});
		return convertView;
}
}
