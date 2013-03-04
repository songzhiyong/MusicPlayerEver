package com.example.imusicplayer.entity;

public class AlbumDataBean {
	public int albumId;
	public String albumName;
	public String albumPicPath;
	public float albumRate;
	public String albumArtist;
	public String albumPicLocalCache;
	public AlbumDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + albumId;
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
		AlbumDataBean other = (AlbumDataBean) obj;
		if (albumId != other.albumId)
			return false;
		return true;
	}

	public AlbumDataBean(int id, String albumName, String albumPicPath,
			float albumRate, String albumArtist) {
		super();
		this.albumId = id;
		this.albumName = albumName;
		this.albumPicPath = albumPicPath;
		this.albumRate = albumRate;
		this.albumArtist = albumArtist;
	}

	@Override
	public String toString() {
		return "AlbumDataBean [albumId=" + albumId + "\n albumName=" + albumName
				+ "\n albumPicPath=" + albumPicPath + "\n albumRate=" + albumRate
				+ "\n albumArtist=" + albumArtist + "\n albumPicLocalCache="
				+ albumPicLocalCache + "]";
	}
	
	
}
