package com.example.imusicplayer;

import java.util.ArrayList;

import android.app.Application;
import android.content.res.Configuration;

import com.example.imusicplayer.entity.AlbumDataBean;
import com.example.imusicplayer.entity.GridItemInfo;
import com.example.imusicplayer.entity.MusicDataBean;
/**
 * 
 * @author Song Zhiyong/宋志勇
 * @version 1.0
 * @see 新浪微博&Twitter:@Chilly Song
 * @QQ 780834790
 * @date 2012-8-10
 */
public class IApplication extends Application{
	//应用程序共享的上下文对象
	public static IApplication GLOBAL_CONTEXT;
	//本地音乐列表 ？？未优化扫描模块 MARK
	public static ArrayList<MusicDataBean> listLocalMusics = new ArrayList<MusicDataBean>();
	//最近播放列表，重新启动则清空
	public static ArrayList<MusicDataBean> listRecentPlayMusics = new ArrayList<MusicDataBean>();
	//正在播放的歌曲在本地歌曲列表中的索引，采用set get 获取
	private static int currentIndex = 0;
	//最喜欢的歌曲数目
	public static int favMusicNumber;
	public static ArrayList<GridItemInfo> gridItems;
	public static ArrayList<AlbumDataBean> albumCacheBeans;
	/**
	 * @return 返回正在播放的歌曲索引
	 */
	public static int getCurrentIndex() {
		return currentIndex;
	}
	/**
	 * @return 设置正在播放的歌曲索引
	 */
	public static void setCurrentIndex(int curIndex) {
		currentIndex = curIndex;
	}

	@Override
	public void onCreate() {
		GLOBAL_CONTEXT = this;
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}
}
