package com.example.imusicplayer.entity;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.online.OnlineMusicActivity;
import com.example.imusicplayer.utils.XMLUtils;

public class LoadAlbumTask extends
		AsyncTask<String, String, ArrayList<AlbumDataBean>> {

	private OnlineMusicActivity activity;

	public LoadAlbumTask(OnlineMusicActivity activity) {
		this.activity = activity;
	}

	private boolean isNetEnable;

	@Override
	protected ArrayList<AlbumDataBean> doInBackground(String... params) {
		ArrayList<AlbumDataBean> beans = null;
		if (checkInternetConnection()) {
			isNetEnable = true;
			XMLUtils.createOnlineAlbumsXMLFile();
			try {
				beans = XMLUtils.parserOnlineAlbumCache();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			isNetEnable = false;
			beans = IApplication.albumCacheBeans;
		}
		return beans;
	}

	public boolean checkInternetConnection() {
		ConnectivityManager con=(ConnectivityManager)IApplication.GLOBAL_CONTEXT.getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		return  wifi||internet;
	}

	@Override
	protected void onPostExecute(ArrayList<AlbumDataBean> result) {
		// TODO Auto-generated method stub
		activity.updateAlbumLv(result);
		if (!isNetEnable) {
			Toast.makeText(activity, "无法联网，请检查网络", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
}
