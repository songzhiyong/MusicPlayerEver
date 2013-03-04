package com.example.imusicplayer.utils;


public class GlobalUtils {
	//播放模式
	public static final int PLAY_MODE_LOOP =1;
	public static final int PLAY_MODE_ORDER =2;
	public static final int PLAY_MODE_SINGLE_LOOP =3;
	public static final int PLAY_MODE_RANDOM =4;
	
	//列表类型
	public static final int LIST_TYPE_ALL =100;
	public static final int LIST_TYPE_FAV =101;
	public static final int LIST_TYPE_RECENT_PLAY =102;
	
	
	//Action	ServiceReceiver
	public static final String ACTION_PLAY = "com.justing.ACTION_PLAY";
	public static final String ACTION_PAUSE = "com.justing.ACTION_PAUSE";
	public static final String ACTION_STOP = "com.justing.ACTION_STOP";
	public static final String ACTION_PREVIOUS = "com.justing.ACTION_PREVIOUS";
	public static final String ACTION_NEXT = "com.justing.ACTION_NEXT";
	public static final String ACTION_SEEK_TO= "com.justing.ACTION_SEEK_TO";
	public static final String ACTION_PLAY_MODE_CHANGED = "com.justing.ACTION_PLAY_MODE_CHANGED";
	public static final String ACTION_UPDATE_STATE_CHANGED = "com.justing.ACTION_UPDATE_STATE_CHANGED";
	public static final String ACTION_SERVICE_EXIT = "com.justing.ACTION_SERVICE_EXIT";
	public static final String ACTION_REQUEST_PLAY_STATE = "com.justing.ACTION_REQUEST_PLAY_STATE";
	public static final String ACTION_DELETE = "com.justing.ACTION_DELETE";
	
	
	
	//ActivityReceiver
	public static final String ACTION_UPDATE_PROGRESS = "com.justing.ACTION_UPDATE_PROGRESS";
	public static final String ACTION_CUR_MUSIC_CHANGED = "com.justing.ACTION_CUR_MUSIC_CHANGED";
	public static final String ACTION_PLAY_STATE_CHANGED = "com.justing.ACTION_PLAY_STATE_CHANGED";
	public static final String ACTION_UPDATE_LIST = "com.justing.ACTION_UPDATE_LIST";
	public static final String ACTION_ALL_MUSICS ="com.justing.ACTION_ALL_MUSICS";
	public static final String ACTION_FAV_MUSICS = "com.justing.ACTION_FAV_MUSICS";
	public static final String ACTION_RENCENTPLAY_MUSICS = "com.justing.ACTION_RECENTPLAY_MUSICS";
	public static final String  ACTION_CUR_LRC = "com.justing.ACTION_CUR_LRC";
	public static final String ACTION_SYS_EXIT = "com.justing.Action_SYS_EXIT";
	
	
	//intent extra
	public static final String EXTRA_PLAY_MODE = "playMode"; 
	public static final String EXTRA_MUSIC = "music"; 
	public static final String EXTRA_NEED_UPDATE= "state"; 
	public static final String EXTRA_CUR_PROGRESS= "progress"; 
	public static final String EXTRA_DURATION= "duration"; 
	public static final String EXTRA_PLAY_STATE = "playState";
	public static final String EXTRA_MUSIC_LIST_TYPE = "musicListType";
	public static final String EXTRA_CUR_LRC = "currentLrcSentence";
	
	public static final String ABOUT = "版本更新，敬请关注：\n新浪微博：@Chilly_Song \nTwitter:@Chilly Song \nQQ:780834790";
	
	
	
	
	
	
	
}
