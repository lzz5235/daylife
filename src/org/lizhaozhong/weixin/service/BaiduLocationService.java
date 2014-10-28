package org.lizhaozhong.weixin.service;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class BaiduLocationService {

	public static String Location(String Latitude,String Longitude) {

		String requestUrl = "http://api.map.baidu.com/telematics/v3/reverseGeocoding?location={LATITUDE},{LONGITUDE}&coord_type=gcj02&ak=ric9eLENBDb88Y5CXO5ZMcGV";

		requestUrl = requestUrl.replace("{LATITUDE}", urlEncodeUTF8(Latitude));
		requestUrl = requestUrl.replace("{LONGITUDE}", urlEncodeUTF8(Longitude));
		// 查询并获取返回结果
		InputStream inputStream = httpRequest(requestUrl);
		
		String locate = parselocation(inputStream);

		return locate;
	}

	/**
	 * UTF-8编码
	 * 
	 * @param source
	 * @return
	 */
	private static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送http请求取得返回的输入流
	 * 
	 * @param requestUrl 请求地址
	 * @return InputStream
	 */
	private static InputStream httpRequest(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
         httpUrlConn.setDoOutput(false);  
         httpUrlConn.setDoInput(true);  
         httpUrlConn.setUseCaches(false);  
  
         httpUrlConn.setRequestMethod("GET");  
         httpUrlConn.connect(); 
			// 获得返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 解析movies参数
	 * 
	 * @param inputStream 
	 * @return string
	 */
	@SuppressWarnings("unchecked")
	private static String parselocation(InputStream inputStream) {
		String str = null;
		
		try {
			// 使用dom4j解析xml字符串
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			
			String status = root.element("status").getText();			
			
			if ("Success".equals(status)) {
				str = root.element("city").getText();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
