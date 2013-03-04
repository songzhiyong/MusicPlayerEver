package com.example.imusicplayer;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

import com.example.imusicplayer.local.LocalMusicActivity;
import com.example.imusicplayer.online.OnlineMusicActivity;
import com.example.imusicplayer.utils.XMLUtils;
/**
 * @author Song Zhiyong/宋志勇
 * @version 1.0
 * @see 新浪微博&Twitter:@Chilly Song
 * @QQ 780834790
 * @date 2012-8-10
 * @describe 欢迎界面结束后的主界面
 */
public class MainActivity extends TabActivity {
	public static void launch(Context c){
		Intent intent = new Intent(c, MainActivity.class);
		c.startActivity(intent);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initView();
	}
	/**
	 * 初始化界面，tabhost
	 */
	private void initView() {
		TabHost host = getTabHost();
		host.setBackgroundResource(R.drawable.background);
		getTabWidget().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.handlelayout_bg));
		host.addTab(host.newTabSpec("LocalMusic").setIndicator("本地音乐")
				.setContent(new Intent(this, LocalMusicActivity.class)));
		host.addTab(host.newTabSpec("OnlineMusic").setIndicator("在线音乐")
				.setContent(new Intent(this, OnlineMusicActivity.class)));
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		try {
			/**主界面销毁时，即退出应用程序，将最后的列表写入本地文件*/
			XMLUtils.saveToXML(IApplication.listLocalMusics);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
