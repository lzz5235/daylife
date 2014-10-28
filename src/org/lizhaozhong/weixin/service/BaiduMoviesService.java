package org.lizhaozhong.weixin.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class BaiduMoviesService {

	public static String searchMovie(String City) {

		String requestUrl = "http://api.map.baidu.com/telematics/v3/movie?qt=hot_movie&location=北京&ak=ric9eLENBDb88Y5CXO5ZMcGV";

		//requestUrl = requestUrl.replace("{CITY}", urlEncodeUTF8(City));
		
		// 查询并获取返回结果
		InputStream inputStream = httpRequest(requestUrl);
		
		String movies = parsemovies(inputStream);

		return movies;
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
	 * @return Music
	 */
	@SuppressWarnings("unchecked")
	private static String parsemovies(InputStream inputStream) {
		String str = null;
		
		String movie_name = null;
		String movie_type = null;
		String movie_release = null;
		String movie_nation = null;
		String movie_length = null;
		String movie_pic = null;
		String movie_tags = null;
		String movie_score = null;
		
		try {
			// 使用dom4j解析xml字符串
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			
			String status = root.element("status").getText();			
			
			if ("Success".equals(status)) {
				
				Iterator iter = root.elementIterator("result");
				
				while(iter.hasNext())
				{
					Element child = (Element)iter.next();
					Iterator iters = child.element("movie").elementIterator("item");
					
					StringBuffer contentMsg = new StringBuffer();
					contentMsg.append("\ue131 大陆最新热映影片：").append("\n\n");
					while(iters.hasNext())
					{
						
						Element childs = (Element)iters.next();
						
						movie_name = childs.element("movie_name").getText();
						movie_type = childs.element("movie_type").getText();
						movie_release = childs.element("movie_release_date").getText();
						movie_nation = childs.element("movie_nation").getText();
						movie_length = childs.element("movie_length").getText();
						movie_pic = childs.element("movie_picture").getText();
						movie_tags = childs.element("movie_tags").getText();
						movie_score = childs.element("movie_score").getText();
						
						
						
						contentMsg.append(movie_name + " " + movie_type).append("\n");
						contentMsg.append(movie_tags).append("\n");
						contentMsg.append("评分： "+ movie_score).append("\n");
						contentMsg.append(movie_release).append("\n");
						contentMsg.append(movie_nation + " " + movie_length).append("\n\n");						
//						contentMsg.append("海报： "+ movie_pic).append("\n\n");	
					}
					str = contentMsg.toString();
				}				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
