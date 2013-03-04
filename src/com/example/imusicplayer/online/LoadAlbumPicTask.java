package com.example.imusicplayer.online;

import java.io.IOException;

import com.example.imusicplayer.entity.AlbumDataBean;
import com.example.imusicplayer.utils.XMLUtils;

import android.os.AsyncTask;

public class LoadAlbumPicTask extends AsyncTask<AlbumDataBean, String, AlbumDataBean> {
	private OnlineMusicActivity activity;
	private AlbumDataBean bean;
	public LoadAlbumPicTask(OnlineMusicActivity activity){
		this.activity = activity;
	}
	@Override
	protected AlbumDataBean doInBackground(AlbumDataBean... params) {
		bean = params[0];
		try {
			if (bean.albumPicLocalCache==null||"".equals(bean.albumPicLocalCache)) {
				bean.albumPicLocalCache = XMLUtils.createOnlineAlbumPic(bean.albumPicPath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	@Override
	protected void onPostExecute(AlbumDataBean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		activity.updateAlbumLv(result);
	}
	
	
//	@Override
//	protected String doInBackground(String... params) {
//		// TODO Auto-generated method stub
//		String localPath =null;
//		try {
//			localPath = XMLUtils.createOnlineAlbumPic(params[0]);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return localPath;
//	}

}
