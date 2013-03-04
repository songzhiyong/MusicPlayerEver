package com.example.imusicplayer.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.entity.AlbumDataBean;
import com.example.imusicplayer.entity.MusicDataBean;

public class XMLUtils {
	public static void saveToXML(ArrayList<MusicDataBean> localMusics)
			throws Exception {
		createLocalMusciXMLFile(createDocument(localMusics));
	}

	private static Context context = IApplication.GLOBAL_CONTEXT;

	private static void createLocalMusciXMLFile(Document doc) throws Exception {
		@SuppressWarnings("static-access")
		OutputStream out = context.openFileOutput("localMusicInfos.xml",
				context.MODE_PRIVATE);
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(doc);
	}
	public static String createOnlineAlbumPic(String uri) throws IOException{
		byte[] data = HttpUtils.getBytes(uri, null, HttpUtils.METHOD_GET);
		@SuppressWarnings("static-access")
		OutputStream out = context.openFileOutput(uri.hashCode()+uri.substring(uri.lastIndexOf(".")), context.MODE_PRIVATE);
		out.write(data);
		out.close();
		return "/data/data/com.example.imusicplayer/files/"+uri.hashCode()+uri.substring(uri.lastIndexOf("."));
	}

	private static Document createDocument(ArrayList<MusicDataBean> localMusics) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("musics");
		for (MusicDataBean musicDataBean : localMusics) {
			Element music = root.addElement("music").addAttribute("id",
					musicDataBean.musicId + "");
			music.addElement("title").addText(musicDataBean.musicTitle + "");
			music.addElement("singer").addText(musicDataBean.musicArtist + "");
			music.addElement("musicpath").addText(musicDataBean.musicPath + "");
			music.addElement("type").addText(musicDataBean.musicType + "");
			music.addElement("duration").addText(
					musicDataBean.musicDuration + "");
			music.addElement("albumname").addText(
					musicDataBean.musicAlbumName + "");
			music.addElement("albumPicPath").addText(
					musicDataBean.musicAlbumPicPath + "");
			music.addElement("favorite").addText(
					musicDataBean.favOrNotResId + "");
		}
		return doc;
	}

	public static ArrayList<MusicDataBean> parserLocalList()
			throws XmlPullParserException, IOException {
		InputStream in = IApplication.GLOBAL_CONTEXT
				.openFileInput("localMusicInfos.xml");
		ArrayList<MusicDataBean> musics = new ArrayList<MusicDataBean>();
		if (in!=null) {
		MusicDataBean music = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(in, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				if ("music".equals(tagName)) {
					music = new MusicDataBean();
					music.musicId = (Integer.parseInt(parser.getAttributeValue(
							null, "id")));
				} else if ("title".equals(tagName)) {
					music.musicTitle = (parser.nextText());
				} else if ("singer".equals(tagName)) {
					music.musicArtist = (parser.nextText());
				} else if ("musicpath".equals(tagName)) {
					music.musicPath = (parser.nextText());
				} else if ("type".equals(tagName)) {
					music.musicType = (parser.nextText());
				} else if ("duration".equals(tagName)) {
					music.musicDuration = Integer.parseInt(parser.nextText());
				} else if ("albumname".equals(tagName)) {
					music.musicAlbumName = (parser.nextText());
				} else if ("albumPicPath".equals(tagName)) {
					music.musicAlbumPicPath = (parser.nextText());
				} else if ("favorite".equals(tagName)) {
					music.favOrNotResId = (Integer.parseInt(parser.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				if ("music".equals(parser.getName())) {
					musics.add(music);
				}
				break;
			}
			type = parser.next();
			}
		}
		return musics;
	}

	public static ArrayList<AlbumDataBean> parserOnlineAlbumCache()
			throws IOException, XmlPullParserException {
		ArrayList<AlbumDataBean> albums = new ArrayList<AlbumDataBean>();
		InputStream in = context.openFileInput("onlineAlbumsInfo.xml");
		AlbumDataBean album = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(in, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				if ("album".equals(tagName)) {
					album = new AlbumDataBean();
				} else if ("id".equals(tagName)) {
					album.albumId = Integer.parseInt(parser.nextText());
				} else if ("name".equals(tagName)) {
					album.albumName = (parser.nextText());
				} else if ("image".equals(tagName)) {
					album.albumPicPath = (parser.nextText());
				} else if ("rating".equals(tagName)) {
					album.albumRate = Float.parseFloat(parser.nextText());
				} else if ("artist_name".equals(tagName)) {
					album.albumArtist = (parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if ("album".equals(parser.getName())) {
					albums.add(album);
				}
				break;
			}
			type = parser.next();
		}
		return albums;
	}
	public static void createOnlineAlbumsXMLFile() {
		try {
//自己搭建的简单服务器测试
//			byte[] data = HttpUtils.getBytes(HttpUtils.BASE_URL
//					+ "ratingweek_desc.xml", null, HttpUtils.METHOD_POST);
			byte[] data = HttpUtils.getBytes(HttpUtils.BASE_URL
					, null, HttpUtils.METHOD_POST);
			@SuppressWarnings("static-access")
			OutputStream out = context.openFileOutput("onlineAlbumsInfo.xml",
					context.MODE_PRIVATE);
			out.write(data);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
