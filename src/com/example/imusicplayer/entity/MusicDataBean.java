package com.example.imusicplayer.entity;

import java.io.Serializable;

import com.example.imusicplayer.R;

public class MusicDataBean implements Serializable{
	private static final long serialVersionUID = -563999818602774452L;
	public  int musicId;
	public String musicTitle;
	public String musicArtist;
	public long musicDuration;
	public String musicAlbumName;
	public String  musicAlbumPicPath;
	public String musicPath;
	public String musicType;//本地还是远程
	public int favOrNotResId = R.drawable.btn_not_love;
	@Override
	public String toString() {
		return "MusicDataBean [musicId=" + musicId + ", musicTitle="
				+ musicTitle + ", musicArtist=" + musicArtist
				+ ", musicDuration=" + musicDuration + ", albumName="
				+ musicAlbumName + ", albumPicPath=" + musicAlbumPicPath + ", musicPath="
				+ musicPath + ", musicType=" + musicType + ", favOrNotResId="
				+ favOrNotResId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + musicId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicDataBean other = (MusicDataBean) obj;
		if (musicId != other.musicId)
			return false;
		return true;
	}
	
}
