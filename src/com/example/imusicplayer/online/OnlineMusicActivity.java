package com.example.imusicplayer.online;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.R;
import com.example.imusicplayer.adapter.AlbumAdatper;
import com.example.imusicplayer.entity.AlbumDataBean;
import com.example.imusicplayer.entity.LoadAlbumTask;
import com.example.imusicplayer.local.LocalMusicActivity;
import com.example.imusicplayer.utils.HttpUtils;

public class OnlineMusicActivity extends LocalMusicActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
			LoadAlbumTask albumTask = new LoadAlbumTask(this);
			albumTask.execute(HttpUtils.BASE_URL);
		super.onResume();
	}


	private AlbumAdatper albumAdapter;

	private ArrayList<AlbumDataBean> albums;

	@Override
	protected void initLv() {
		lvMusics = (ListView) findViewById(R.id.lvMusics);
		lvMusics.setPadding(0, 0, 0, 0);
		lvMusics.setVisibility(View.VISIBLE);
		albums = IApplication.albumCacheBeans;
		albumAdapter = new AlbumAdatper(this, albums);
		lvMusics.setAdapter(albumAdapter);
	}

	@Override
	protected void initGridView() {
		gvLocalMusics = (GridView) findViewById(R.id.gvLocalMusics);
		gvLocalMusics.setVisibility(View.GONE);
	}

	protected void initTitle() {
	}

	@Override
	protected void updateGridItem() {
	}

	public void updateAlbumLv(ArrayList<AlbumDataBean> albums) {
		this.albums = albums;
		albumAdapter.setAlbums(albums);
		lvMusics.setAdapter(albumAdapter);
		if (albums != null) {
			for (AlbumDataBean albumDataBean : albums) {
				LoadAlbumPicTask task = new LoadAlbumPicTask(
						OnlineMusicActivity.this);
				task.execute(albumDataBean);
			}
		}
	}

	@Override
	public void onDrawerOpened() {
		lvMusics.setVisibility(View.GONE);
	}

	@Override
	public void onDrawerClosed() {
		lvMusics.setVisibility(View.VISIBLE);
	}

	public void updateAlbumLv(AlbumDataBean result) {
		albums.remove(result);
		albums.add(result);
		Log.i("info", albums + "");
		albumAdapter.setAlbums(albums);
		lvMusics.setAdapter(albumAdapter);
	}
}
