package com.example.imusicplayer.local;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.R;
import com.example.imusicplayer.adapter.MusicListAdapter;
import com.example.imusicplayer.entity.MusicDataBean;
import com.example.imusicplayer.utils.GlobalUtils;
import com.example.imusicplayer.utils.MenuUtils;

public class MusicsListActivity extends LocalMusicActivity implements
		OnItemClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void initGridView() {
		gvLocalMusics = (GridView) findViewById(R.id.gvLocalMusics);
		gvLocalMusics.setVisibility(View.GONE);
	}

	@Override
	protected void updateGridItem() {
	}

	protected TextView tvTitle;
	protected void initTitle() {
		tvTitle = (TextView)findViewById(R.id.tvListTypeTitile);
		tvTitle.setVisibility(View.VISIBLE);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		lp.setMargins(0, 70, 0, 0);
//		drawer.setLayoutParams(lp);	
	}
	private static int listType;
	@Override
	protected ArrayList<MusicDataBean> loadMusics() {
		ArrayList<MusicDataBean> lMusics = new ArrayList<MusicDataBean>();
		int listType = getIntent().getIntExtra(
				GlobalUtils.EXTRA_MUSIC_LIST_TYPE, -1);
		switch (listType) {
		case GlobalUtils.LIST_TYPE_ALL:
			lMusics = IApplication.listLocalMusics;
			tvTitle.setText("全部歌曲");
			listType = 1;
			break;
		case GlobalUtils.LIST_TYPE_FAV:
			listType = 2;
			for (MusicDataBean musicDataBean : IApplication.listLocalMusics) {
				if (musicDataBean.favOrNotResId == R.drawable.btn_love) {
					lMusics.add(musicDataBean);
				}
			}
			tvTitle.setText("我的最爱");
			break;
		case GlobalUtils.LIST_TYPE_RECENT_PLAY:
			listType = 3;
			lMusics = IApplication.listRecentPlayMusics;
			tvTitle.setText("最近播放");
			break;
		default:
			break;
		}
		return lMusics;
	}

	protected void initLv() {
		lvMusics = (ListView) findViewById(R.id.lvMusics);
		lvMusics.setVisibility(View.VISIBLE);
		musics = loadMusics();
		adapter = new MusicListAdapter(this, musics,listType);
		lvMusics.setAdapter(adapter);
		lvMusics.setOnCreateContextMenuListener(this);
		lvMusics.setOnItemClickListener(this);
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.setHeaderTitle("操作");
		menu.add(2, MenuUtils.MENU_CONTEXT_DETAILS, 1, "查看详情");
		menu.add(2, MenuUtils.MENU_CONTEXT_DOWNLOAD, 2, "下载");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		MusicDataBean curBean = (MusicDataBean) adapter.getItem(position);
		IApplication.setCurrentIndex(IApplication.listLocalMusics.indexOf(curBean));
		if (isPlaying) {
			intent.setAction(GlobalUtils.ACTION_PAUSE);
		} else {
			intent.setAction(GlobalUtils.ACTION_PLAY);
		}
		sendBroadcast(intent);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
	}
	@Override
	public void onDrawerOpened() {
		lvMusics.setVisibility(View.GONE);
		tvTitle.setVisibility(View.GONE);
	}
	@Override
	public void onDrawerClosed() {
		tvTitle.setVisibility(View.VISIBLE);
		lvMusics.setVisibility(View.VISIBLE);
	}
}
