package com.example.imusicplayer.local;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.R;
import com.example.imusicplayer.utils.LocalMusicUtils;
import com.example.imusicplayer.utils.XMLUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class RefreshLibActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refresh_lib);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://"
						+ Environment.getExternalStorageDirectory()
								.getAbsolutePath())));
		new Thread(){
			@Override
		public void run() {
			try {
				XMLUtils.saveToXML(LocalMusicUtils.getMusics());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IApplication.listLocalMusics = LocalMusicUtils.getMusics();
			try {
				XMLUtils.saveToXML(IApplication.listLocalMusics);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
		}}.start();
	}
}
