package com.example.imusicplayer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.imusicplayer.R;
import com.example.imusicplayer.entity.AlbumDataBean;

public class AlbumAdatper extends BaseAdapter{

	private ArrayList<AlbumDataBean> albums;
	private LayoutInflater inflater;
	
	public void setAlbums(ArrayList<AlbumDataBean> albums) {
		if (albums!=null) {
			this.albums = albums;
		}else {
			this.albums = new ArrayList<AlbumDataBean>();
		}
	}

	public AlbumAdatper(Context mContext, ArrayList<AlbumDataBean> albums) {
		this.setAlbums(albums);
		this.inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return albums.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return albums.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return albums.get(position).albumId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView==null) {
			convertView = inflater.inflate(R.layout.online_album_item,null);
			holder = new ViewHolder();
			holder.ivAlbumPic = (ImageView) convertView.findViewById(R.id.ivAlbumPic);
			holder.tvAlbumName = (TextView) convertView.findViewById(R.id.tvAlbumName);
			holder.tvAlbumArtist = (TextView)convertView.findViewById(R.id.tvAlbumArtist);
			holder.rbRating = (RatingBar)convertView.findViewById(R.id.rtRate);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		AlbumDataBean album = albums.get(position);
		if (album.albumPicLocalCache!=null||"".equals(album.albumPicLocalCache)) {
			holder.ivAlbumPic.setImageBitmap(BitmapFactory.decodeFile(album.albumPicLocalCache));
		}
		holder.tvAlbumName.setText(album.albumName);
		holder.tvAlbumArtist.setText(album.albumArtist);
		holder.rbRating.setRating((album.albumRate)*5);
		return convertView;
	}
	private class ViewHolder{
		ImageView ivAlbumPic;
		TextView tvAlbumName;
		TextView tvAlbumArtist;
		RatingBar rbRating;
	}

}
