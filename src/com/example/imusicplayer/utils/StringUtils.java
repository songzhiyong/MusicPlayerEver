package com.example.imusicplayer.utils;


public class StringUtils {
	public static  String timeFormat(long duration){
		int min = (int)duration/1000/60;
		int sec = (int)duration/1000%60;
		String mm = min>10?min+"":"0"+min;
		String ss = sec>=10?sec+"":"0"+sec;
		return mm+":"+ss;
	}
	public static String toGBK( String string){
//		try {
//			if (string!=null) {
//				return new String(string.getBytes("iso-8859-1"),"GBK");
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return string;
	}
}
