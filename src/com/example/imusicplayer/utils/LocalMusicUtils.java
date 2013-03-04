package com.example.imusicplayer.utils;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.entity.MusicDataBean;

public class LocalMusicUtils {
	private static Context mContext = IApplication.GLOBAL_CONTEXT;
	public static final ContentResolver cr = mContext.getContentResolver(); 
	public static final String LOCAL_MUSIC = "local_music";
	public static ArrayList<MusicDataBean> getMusics() {
		ArrayList<MusicDataBean> musics = null;
		Uri audioUri = Media.EXTERNAL_CONTENT_URI;
		Uri albumUri = Albums.EXTERNAL_CONTENT_URI;

		String[] projection = new String[] { "_id", "_data", "title",
				"duration", "artist", "album", "album_key" };
		Cursor c1 = cr.query(audioUri, projection, "duration > ?",
				new String[] { "1000" }, null);
		if (c1 != null && c1.getCount() > 0) {
			musics = new ArrayList<MusicDataBean>();
			while (c1.moveToNext()) {
				MusicDataBean music = new MusicDataBean();
				music.musicId = c1.getInt(c1.getColumnIndex("_id"));
				music.musicTitle = c1.getString(c1.getColumnIndex("title"));
				music.musicArtist = c1.getString(c1.getColumnIndex("artist"));
				music.musicAlbumName = (c1.getString(c1.getColumnIndex("album")));
				music.musicDuration = (c1.getLong(c1.getColumnIndex("duration")));
				music.musicPath = (c1.getString(c1.getColumnIndex("_data")));
				music.musicType = LOCAL_MUSIC;
				String album_key = c1.getString(c1.getColumnIndex("album_key"));
				projection = new String[] { "album_art" };
				Cursor c2 = cr.query(albumUri, projection, "album_key = ?",
						new String[] { album_key }, null);
				if (c2.moveToFirst()) {
					music.musicAlbumPicPath = (c2.getString(0));
					Log.i("info", "musicAlbum=="+music.musicAlbumPicPath);
					Log.i("info", "musicpath = "+music.musicPath);
				}
				c2.close();
				musics.add(music);
			}
		}
		c1.close();
		return musics;
	}
	public static void delete() {
		MusicDataBean bean = IApplication.listLocalMusics.get(IApplication.getCurrentIndex());
		int id = bean.musicId;
		Uri audioUri = Media.EXTERNAL_CONTENT_URI;
		cr.delete(audioUri, "_id = "+id, null);
		IApplication.listLocalMusics.remove(IApplication.getCurrentIndex());
		File file = new File(bean.musicPath);
		if (file.exists()) {
			file.delete();
		}
	}
}
