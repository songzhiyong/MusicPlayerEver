package com.example.imusicplayer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.R;
import com.example.imusicplayer.entity.MusicDataBean;
import com.example.imusicplayer.utils.LocalMusicUtils;
import com.example.imusicplayer.utils.StringUtils;

public class MusicListAdapter extends BaseAdapter {
	private ArrayList<MusicDataBean> musics = null;
	private LayoutInflater inflater;
	private int listType;
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MusicDataBean bean = (MusicDataBean) v.getTag();
			int index = IApplication.listLocalMusics.indexOf(bean);
			IApplication.setCurrentIndex(index);
			if (bean.favOrNotResId == R.drawable.btn_love) {
				IApplication.listLocalMusics.get(index).favOrNotResId = R.drawable.btn_not_love;
				IApplication.favMusicNumber--;
			} else {
				IApplication.listLocalMusics.get(index).favOrNotResId = R.drawable.btn_love;
				IApplication.favMusicNumber++;
			}
			switch (listType) {
			case 1:
				MusicListAdapter.this.setMusics(IApplication.listLocalMusics);
				break;
			case 2:
				ArrayList<MusicDataBean> lMusics = new ArrayList<MusicDataBean>();
				for (MusicDataBean musicDataBean : IApplication.listLocalMusics) {
					if (musicDataBean.favOrNotResId == R.drawable.btn_love) {
						lMusics.add(musicDataBean);
					}
				}
				MusicListAdapter.this.setMusics(lMusics);
				break;
			case 3:
				MusicListAdapter.this
						.setMusics(IApplication.listRecentPlayMusics);
				break;
			default:
				break;
			}
			MusicListAdapter.this.notifyDataSetChanged();
		}
	};

	public MusicListAdapter(Context context, ArrayList<MusicDataBean> musics,
			int listType) {
		this.inflater = LayoutInflater.from(context);
		setMusics(musics);
		this.listType = listType;
	}

	public void setMusics(ArrayList<MusicDataBean> musics) {
		if (musics != null) {
			this.musics = musics;
		} else {
			this.musics = new ArrayList<MusicDataBean>();
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musics.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return musics.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return musics.get(position).musicId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.music_item, null);
			holder.ivAlbum = (ImageView) convertView.findViewById(R.id.ivAlbum);
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tvMusicName);
			holder.tvArtist = (TextView) convertView
					.findViewById(R.id.tvSinger);
			holder.tvDuration = (TextView) convertView
					.findViewById(R.id.tvDuration);
			holder.tvAlbum = (TextView) convertView
					.findViewById(R.id.tvAlbumName);
			holder.ivListItemFavOrAdd = (ImageView) convertView
					.findViewById(R.id.list_item_fav_or_download);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MusicDataBean music = musics.get(position);
		String pathName = music.musicAlbumPicPath;
		if (music.musicType.equals(LocalMusicUtils.LOCAL_MUSIC)) {
			// 本地音乐
			if (pathName == null || "".equals(pathName)
					|| "null".equals(pathName)) {
				holder.ivAlbum.setImageResource(R.drawable.icon);
			} else {
				holder.ivAlbum.setImageDrawable(BitmapDrawable
						.createFromPath(pathName));
			}
			holder.ivListItemFavOrAdd.setTag(music);
			holder.ivListItemFavOrAdd
					.setBackgroundResource(music.favOrNotResId);
		} else {
			// 在线音乐

		}
		holder.tvTitle.setText(StringUtils.toGBK(music.musicTitle));
		holder.tvAlbum.setText(StringUtils.toGBK(music.musicAlbumName));
		holder.tvArtist.setText(StringUtils.toGBK(music.musicArtist));
		holder.tvDuration.setText(StringUtils.timeFormat(music.musicDuration));
		holder.ivListItemFavOrAdd.setTag(music);
		holder.ivListItemFavOrAdd.setOnClickListener(onClickListener);
		return convertView;
	}

	private class ViewHolder {
		ImageView ivListItemFavOrAdd;
		ImageView ivAlbum;
		TextView tvTitle;
		TextView tvArtist;
		TextView tvAlbum;
		TextView tvDuration;
	}

}
