package com.example.imusicplayer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;
//	public static final String BASE_URL = "http://172.60.100.142:8080/musicserver/";
	/**借鉴jamendo音乐播放器开放平台Api接口*/
	public static final String BASE_URL = "http://api.jamendo.com/get2/id+name+url+image+rating+artist_name/album/xml/?n=20&order=ratingweek_desc";
	
	public InputStream getStream(String uri,ArrayList<BasicNameValuePair> params,int method) throws IOException{
		HttpEntity entity = getEntity(uri, params, method);
		if(entity!=null)
			return entity.getContent();
		return null;
	}
	
	/**
	 * 根据响应实体对象，获得输入流对象
	 * @param entity
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public InputStream getSTStream(HttpEntity entity) throws IllegalStateException, IOException{
		if(entity!=null)
			return entity.getContent();
		return null;
	}
	
	/**
	 * 根据响应实体对象，获得响应实体的长度
	 * @param entity
	 * @return
	 */
	public long getLength(HttpEntity entity){
		if(entity!=null)
			return entity.getContentLength();
		
		return -1;
	}
	
	public static byte[] getBytes(String uri,ArrayList<BasicNameValuePair> params,int method) throws IOException{
		HttpEntity entity = getEntity(uri, params, method);
		if(entity!=null)
			return EntityUtils.toByteArray(entity);
		return null;
	}
	
	/**
	 * 根据指定的uri获得响应实体
	 * @param uri
	 * @param params
	 * @param method
	 * @return
	 * @throws IOException
	 */
	public static HttpEntity getEntity(String uri,ArrayList<BasicNameValuePair> params,int method) throws IOException{
		HttpEntity entity = null;
		//创建客户端对象
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		//创建请求对象
		HttpUriRequest request = null;
		switch (method) {
		case METHOD_GET:
			StringBuilder sb = new StringBuilder(uri);
			if(params!=null && !params.isEmpty()){
				sb.append('?');
				for(BasicNameValuePair pair : params){
					sb.append(pair.getName())
					.append('=')
					.append(pair.getValue())
					.append('&');
				}
				sb.deleteCharAt(sb.length()-1);
			}
			request = new HttpGet(sb.toString());
			break;

		case METHOD_POST:
			request = new HttpPost(uri);
			if(params!=null && !params.isEmpty()){
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params);
				((HttpPost)request).setEntity(reqEntity);
			}
			break;
		}
		//执行请求获得响应对象
		HttpResponse response  = client.execute(request);
		//判断响应码是否为200，如果是 获取响应实体
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			entity = response.getEntity();
		}
		return entity;
	}
}
